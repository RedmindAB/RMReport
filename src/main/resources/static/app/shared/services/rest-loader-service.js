(function(){
	'use strict';
	
	angular
		.module('webLog')
		.service('RestLoader', restLoader);
	
	restLoader.$inject = ['$http', '$state', 'CurrentSuite', 'Utilities', 'Charts', 'ScreenshotMaster'];
	
	function restLoader ($http, $state, CurrentSuite, Utilities, Charts, ScreenshotMaster){
		
		var rl = this;
		
		rl.getConsolePrint = function(){
		    $http.get('api/suite/syso?suiteid=' + CurrentSuite.currentSuiteInfo.id + '&timestamp=' + CurrentSuite.currentTimeStamp)
		    .success(function(data, status, headers, config){ 
		    	ScreenshotMaster.consolePrint = data;
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		};
		
		function setClassResult(classes){
			for (var i = 0; i < classes.length; i++) {
				if (classes[i].failure >= 1) {
					classes[i].result = "failure";
				} else if(classes[i].failure === 0 && classes[i].passed === 0) {
					classes[i].result = "skipped";
				} else {
					classes[i].result = "passed";
				}
			}
		}
		
		rl.getSuiteSkeleton = function(suite){
			CurrentSuite.currentSuiteInfo = suite;
		    $http.get('/api/suite/latestbyid?suiteid=' + suite.id)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		getSpecsInfo(suite.id);
		    		CurrentSuite.currentSuite = data;
		    		setClassResult(CurrentSuite.currentSuite);
		    		var timestamp;
		    		if (CurrentSuite.currentTimeStamp === '') {
						timestamp = suite.lastTimeStamp;
					} else {
						timestamp = CurrentSuite.currentTimeStamp;
						CurrentSuite.currentTimeStamp = CurrentSuite.timestampRaw[suite.id][CurrentSuite.timestampRaw[suite.id].length-1];
					}
		    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		};
		
		rl.getSuiteSkeletonByTimestamp = function(timestamp, firstLoad){
		    $http.get("/api/suite/bytimestamp?suiteid="+ CurrentSuite.currentSuiteInfo.id + "&timestamp="+timestamp)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentSuite = data;
		    		setClassResult(CurrentSuite.currentSuite);
		    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
		    		if (CurrentSuite.currentClass !== undefined) {
		    			CurrentSuite.currentMethods = CurrentSuite.getMethodsByClassId(CurrentSuite.currentClass.id);
		    			if (CurrentSuite.currentMethods !== undefined) {
		    				rl.getPassFailTotByMethod(timestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
						}
		    			if ($state.current.name === "reports.cases") {
		    				rl.getCases(CurrentSuite.currentMethod);
						}
					}
		    		if (firstLoad) {
		    			getSpecsInfo(CurrentSuite.currentSuiteInfo.id);
					}
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		};
		
	    rl.addCaseToGraph = function(osName, osVersion, deviceName, browserName, browserVer, createMainChart){
	    	var dataRequest = {};
			dataRequest.suiteid = [CurrentSuite.currentSuiteInfo.id];
			dataRequest.reslimit = Utilities.getResLimit();
			dataRequest.os = [CurrentSuite.getOsIdByVersion(osName,osVersion)];
			dataRequest.devices = [CurrentSuite.getDeviceIdByName(deviceName)];
			dataRequest.browsers = [CurrentSuite.getBrowserIdByname(browserName, browserVer)];
			dataRequest.classes = [CurrentSuite.currentClass.id];
			dataRequest.testcases = [CurrentSuite.currentMethod.id];
			dataRequest.name = osName+"-"+osVersion+"-"+deviceName+"-"+browserName+"-"+browserVer;
			
			var requestObj = [dataRequest];
	    	$http.post('/api/stats/graphdata', requestObj)
	    	.success(function(data, status, headers, config){
	    		createMainChart(data, true);
	    	}).error(function(data, status, headers, config){
	    		console.error(data);
	    	});
	    };
		
	    rl.createHomeChartFromID = function(suite, createHomeChart) {
	    	var requestObject= [];
	    	requestObject.push({
	    			name:"homeChart",
	    			suiteid: suite.id,
	    			reslimit:50,
	    			os: [],
	    			devices: [],
	    			browsers: [],
	    			classes:[],
	    			testcases:[]
	    	});
	    	$http.post('/api/stats/graphdata', requestObject)
	    	.success(function(data, status, headers, config){ 
	    		createHomeChart(data, suite);
	    	}).error(function(data, status, headers, config){
	    		console.error(data);
	    	});
		};
		
		rl.getPassFailByMethod = function(timestamp, classObj, method){
			$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id+'&testcaseid='+method.id)
			.success(function(data, status, headers, config){ 
				if(data){
					method.stats = data;
					method.stats.totFail = getTotFail(data);
				}
			}).error(function(data, status, headers, config){
				console.error(data);
			});
		};
	    
		rl.getCases = function(method){
			CurrentSuite.currentMethod = method;
		    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimeStamp)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentCases = data;
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		};
		
	    rl.loadMainChart = function(suiteID, newLine, createMainChart,name) {
		   	Charts.mainChart.loading = 'Generating impressivly relevant statistics...';
		   	var activeQueries = [];
	 	   if (!newLine) {
			   CurrentSuite.activeQueries = [];
		   } else {
			   for (var i = 0; i < CurrentSuite.activeQueries.length; i++) {
					   for (var j = 0; j < CurrentSuite.activeQueries[i].length; j++) {
						   if (CurrentSuite.activeQueries[i][j].reslimit > Utilities.getResLimit() || CurrentSuite.activeQueries[i][j].reslimit < Utilities.getResLimit()) {
							   CurrentSuite.activeQueries[i][j].reslimit = Utilities.getResLimit();
						   }
						   activeQueries.push(CurrentSuite.activeQueries[i][j]);
					   }
			   }
			   CurrentSuite.activeQueries = [];
		   }
	 	   
		   var requestObject = getGraphDataObject(suiteID,name);
		   	for (var i = 0; i < activeQueries.length; i++) {
				requestObject.push(activeQueries[i]);
			}
		   CurrentSuite.lastRunSize = Utilities.getResLimit();
		   $http.post('/api/stats/graphdata', requestObject)
		   .success(function(data, status, headers, config){
			   createMainChart(data,newLine);
		   }).error(function(data, status, headers, config){
			   console.error(data);
		   });
	   };
	   
		rl.loadScreenshotsFromClass = function(classObj){
			
		    $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimeStamp+'&classid='+classObj.id)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		ScreenshotMaster.data = data;
		    		rl.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, classObj, ScreenshotMaster.data);
		    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id;
		    		ScreenshotMaster.currentTimestamp = CurrentSuite.currentTimeStamp;
		    		setCaseSizeByMethod();
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		};
		
		rl.loadTimestamp = function(timestamp, firstLoad){
			rl.getSuiteSkeletonByTimestamp(timestamp, firstLoad);
			CurrentSuite.currentTimeStamp = timestamp;
		};
		
		function setCaseSizeByMethod(){
			var data = ScreenshotMaster.data;
			
			for (var i = 0; i < data.length; i++) {
				var screenshotLength = 0;
				for (var j = 0; j < data[i].testcases.length; j++) {
					if (data[i].testcases[j].screenshots.length > screenshotLength) {
						screenshotLength = data[i].testcases[j].screenshots.length;
					}
				}
				data[i].screenshotLength = screenshotLength;
				if (data[i].screenshotLength > 0) {
					data.screenshotsExists = true;
				}
			}
			if (data.screenshotsExists === undefined) {
				data.screenshotsExists = false;
			}
		}
		
	   function getGraphDataObject(suiteID, name){
		    	var graphDataObject = [];
		    	
		    	switch ($state.$current.name) {
				case 'reports.methods':
					CurrentSuite.setChosenForCurrent(CurrentSuite.currentClass.id);
					break;
				case 'reports.cases':
					CurrentSuite.setChosenForCurrent(CurrentSuite.currentClass.id, CurrentSuite.currentMethod.id);
					break;
				default:
					break;
				}
		    	
		    	switch (Utilities.breakPointChoice) {
				case "None":
					graphDataObject = getAllDataAsOne(suiteID, name);
					break;
	
				case "Platform":
					graphDataObject = splitDataOnPlatform(suiteID, name);
					break;
	
				case "Device":
					graphDataObject = splitDataOnDevice(suiteID, name);
					break;
	
				case "Browser":
					graphDataObject = splitDataOnBrowser(suiteID, name);
					break;
					
				case "Version":
					graphDataObject = splitDataOnVersion(suiteID, name);
					break;
	
				default:
					graphDataObject = getAllDataAsOne(suiteID, name);
					console.error("Something went wrong");
					break;
				}
		    	CurrentSuite.activeQueries.push(graphDataObject);
		    	return graphDataObject;
		    	
		    }
		    
		    function getAllDataAsOne(suiteID, name) {
		    	var graphArray = [];
		    	var chosen = CurrentSuite.getChosen();
		    	var dataRequest = {};
		    	if (name) {
		    		dataRequest.name = name;
				} else {
					dataRequest.name = Utilities.getCurrentPosName();
				}
		    	
				dataRequest.suiteid = suiteID;
				dataRequest.reslimit = Utilities.getResLimit();
				
				dataRequest.os = chosen.os;
				dataRequest.devices = chosen.devices;
				dataRequest.browsers = chosen.browsers;
				dataRequest.classes = chosen.classes;
				dataRequest.testcases = chosen.testcases;
				
				graphArray.push(dataRequest);
				
		    	return graphArray;
			}
	
		    function splitDataOnDevice(suiteID, name) {
		    	var graphArray = [];
		    	var chosen = CurrentSuite.getChosen();
		    	var addedUnknown = false;
		    	
		    	chosen.devices = CurrentSuite.getDataFromSpecs('platforms','devices', 'deviceid');
		    	
		    	for (var i = 0; i < chosen.devices.length; i++) {
		    		var dataRequest = {};
		    		if (name) {
		    			dataRequest.name = name;
		    		} else {
		    			dataRequest.name = CurrentSuite.getDeviceByID(chosen.devices[i]);
		    		}
		    		dataRequest.suiteid = suiteID;
		    		dataRequest.reslimit = Utilities.getResLimit();
		    		
		    		dataRequest.os = chosen.os;
		    		dataRequest.devices = [chosen.devices[i]];
		    		dataRequest.browsers = chosen.browsers;
		    		dataRequest.classes = chosen.classes;
		    		dataRequest.testcases = chosen.testcases;
					
		    		graphArray.push(dataRequest);
				}
		    	return graphArray;
		    }
		    
		    function splitDataOnBrowser(suiteID, name) {
		    	var graphArray = [];
		    	var chosen = CurrentSuite.getChosen();
		    	
		    	chosen.browsers = CurrentSuite.getDataFromSpecs('browsers', 'none', 'browserid');
		    	
		    	for (var i = 0; i < chosen.browsers.length; i++) {
		    		var dataRequest = {};
	
		    		dataRequest.suiteid = suiteID;
		    		dataRequest.reslimit = Utilities.getResLimit();
		    		
		    		dataRequest.os = chosen.os;
		    		dataRequest.browsers = [chosen.browsers[i]];
		    		dataRequest.devices = chosen.devices;
		    		dataRequest.classes = chosen.classes;
		    		dataRequest.testcases = chosen.testcases;
		    		
		    		if (name) {
		    			dataRequest.name = name;
		    		} else {
		    			dataRequest.name = CurrentSuite.getBrowserByID(dataRequest.browsers[0]).browsername + " v." + CurrentSuite.getBrowserByID(dataRequest.browsers[0]).browserver;
		    		}
		    		
		    		graphArray.push(dataRequest);
				}
		    	return graphArray;
			}
			
		    function splitDataOnVersion(suiteID, name) {
		    	var graphArray = [];
		    	var chosen = CurrentSuite.getChosen();
		    	
		    	chosen.os = CurrentSuite.getDataFromSpecs('platforms','versions', 'osid');
		    	
		    	for (var i = 0; i < chosen.os.length; i++) {
		    		var dataRequest = {};
		    		if (name) {
		    			dataRequest.name = name;
		    		} else {
		    			dataRequest.name = CurrentSuite.getVersionNameByID(chosen.os[i]);
		    		}
		    		dataRequest.suiteid = suiteID;
		    		dataRequest.reslimit = Utilities.getResLimit();
		    		
		    		dataRequest.os = [chosen.os[i]];
		    		dataRequest.devices = CurrentSuite.sortDevicesByOsId(chosen.devices, chosen.os[i]);
		    		dataRequest.browsers = chosen.browsers;
		    		dataRequest.classes = chosen.classes;
		    		dataRequest.testcases = chosen.testcases;
					
		    		graphArray.push(dataRequest);
				}
		    	return graphArray;
		    }
		    
		    function splitDataOnPlatform(suiteID, name) {
		    	var graphArray = [];
		    	var chosen = CurrentSuite.getChosen();
		    	
		    	chosen.platforms = CurrentSuite.getDataFromSpecs('platforms','none','osname');
		    	
		    	for (var i = 0; i < chosen.platforms.length; i++) {
		    		var dataRequest = {};
	
		    		dataRequest.suiteid = suiteID;
		    		dataRequest.reslimit = Utilities.getResLimit();
		    		
		    		dataRequest.os = CurrentSuite.sortVersionsByPlatform(chosen.platforms[i], chosen.os);
		    		dataRequest.devices = CurrentSuite.sortDevicesByPlatform(chosen.platforms[i],chosen.devices);
		    		
		    		dataRequest.browsers = chosen.browsers;
		    		dataRequest.classes = chosen.classes;
		    		dataRequest.testcases = chosen.testcases;
		    		
		    		if (name) {
		    			dataRequest.name = name;
		    		} else {
		    			dataRequest.name = chosen.platforms[i];
		    		}
		    		
		    		graphArray.push(dataRequest);
				}
		    	return graphArray;
			}
	   
		function getPassFailTotByClass(timestamp, classObj){
			for (var i = 0; i < classObj.length; i++) {
				rl.getPassFailByClass(timestamp, classObj[i]);
			}
		}
		
		rl.getPassFailByClass = function(timestamp, classObj){
			$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id)
			.success(function(data, status, headers, config){ 
				if(data){
					classObj.stats = data;
					classObj.stats.totFail = getTotFail(data);
				}
			}).error(function(data, status, headers, config){
				console.error(data);
			});
		};
		
		rl.getPassFailTotByMethod = function(timestamp, classObj, methodArray){
			var methods = methodArray;
			for (var i = 0; i < methods.length; i++) {
				rl.getPassFailByMethod(timestamp, classObj, methods[i]);
			}
		};
		
		function getSpecsInfo(suiteID){
			var reslimit = Utilities.getResLimit();
		    $http.get('/api/stats/options?suiteid='+suiteID+'&limit='+reslimit)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentSpecObject = data;
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
		function getTotFail(passFailData){
			var totFail = parseInt(passFailData.error) + parseInt(passFailData.failure);
			return totFail;
		}
		
	}
})();