(function(){
	'use strict';
	
	angular
		.module('webLog')
		.service('RestLoader', restLoader);
	
	restLoader.$inject = ['$http', '$state', 'CurrentSuite', 'Utilities', 'Charts', 'ScreenshotMaster'];
	
	function restLoader ($http, $state, CurrentSuite, Utilities, Charts, ScreenshotMaster){
		
		var vm = this;
		
		
		vm.getConsolePrint 				= getConsolePrint;
		vm.getSuiteSkeletonByTimestamp 	= getSuiteSkeletonByTimestamp;
		vm.addCaseToGraph 				= addCaseToGraph;
		vm.createHomeChartFromID 		= createHomeChartFromID;
		vm.getPassFailByMethod 			= getPassFailByMethod;
		vm.getCases 					= getCases;
		vm.loadMainChart 				= loadMainChart;
		vm.loadScreenshotsFromClass 	= loadScreenshotsFromClass;
		vm.loadTimestamp 				= loadTimestamp;
		vm.getPassFailByClass 			= getPassFailByClass;
		vm.getPassFailTotByMethod 		= getPassFailTotByMethod;
		
		
		function getConsolePrint(){
		    $http.get('api/suite/syso?suiteid=' + CurrentSuite.currentSuiteInfo.id + '&timestamp=' + CurrentSuite.currentTimeStamp)
		    .success(function(data, status, headers, config){ 
		    	ScreenshotMaster.consolePrint = data;
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
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
		
		function getSuiteSkeletonByTimestamp(timestamp, firstLoad, suiteID){
			var id = suiteID !== undefined ? suiteID : CurrentSuite.currentSuiteInfo.id;
		    $http.get("/api/suite/bytimestamp?suiteid="+ id + "&timestamp="+timestamp)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentSuite = data;
		    		setClassResult(CurrentSuite.currentSuite);
		    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
		    		if (CurrentSuite.currentClass !== undefined) {
		    			CurrentSuite.currentMethods = CurrentSuite.getMethodsByClassId(CurrentSuite.currentClass.id);
		    			if (CurrentSuite.currentMethods !== undefined) {
		    				vm.getPassFailTotByMethod(timestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
						}
		    			if ($state.current.name === "reports.cases") {
		    				vm.getCases(CurrentSuite.currentMethod);
						}
					}
		    		if (firstLoad) {
		    			getSpecsInfo(id);
					}
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
	    function addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer, createMainChart){
	    	var dataRequest = {};
			dataRequest.suiteid = [CurrentSuite.currentSuiteInfo.id];
			dataRequest.reslimit = Utilities.getResLimit();
			dataRequest.os = [CurrentSuite.getOsIdByVersion(osName,osVersion)];
			dataRequest.devices = [CurrentSuite.getDeviceIdByName(deviceName)];
			dataRequest.browsers = [CurrentSuite.getBrowserIdByname(browserName, browserVer)];
			dataRequest.classes = [CurrentSuite.currentClass.id];
			dataRequest.testcases = [CurrentSuite.currentMethod.id];
			dataRequest.name = osName+"-"+osVersion+"-"+deviceName+"-"+browserName;
			
			var requestObj = [dataRequest];
	    	$http.post('/api/stats/graphdata', requestObj)
	    	.success(function(data, status, headers, config){
	    		createMainChart(data, true);
	    	}).error(function(data, status, headers, config){
	    		console.error(data);
	    	});
	    }
		
	    function createHomeChartFromID(suite, createHomeChart) {
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
		}
		
		function getPassFailByMethod(timestamp, classObj, method){
			$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id+'&testcaseid='+method.id)
			.success(function(data, status, headers, config){ 
				if(data){
					method.stats = data;
					method.stats.totFail = getTotFail(data);
				}
			}).error(function(data, status, headers, config){
				console.error(data);
			});
		}
	    
		function getCases(){
		    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimeStamp)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentCases = data;
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
	    function loadMainChart(suiteID, newLine, createMainChart,name) {
		   	Charts.mainChart.loading = 'Generating impressivly relevant statistics...';
		   	
		   	var activeQueries = [];
		   	
		   	var i = 0;
		   	
	 	   if (!newLine) {
			   CurrentSuite.activeQueries = [];
		   } else {
			   for (i = 0; i < CurrentSuite.activeQueries.length; i++) {
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
		   	for (i = 0; i < activeQueries.length; i++) {
				requestObject.push(activeQueries[i]);
		   	}
		   	CurrentSuite.lastRunSize = Utilities.getResLimit();
		   	$http.post('/api/stats/graphdata', requestObject)
		   	.success(function(data, status, headers, config){
		   		createMainChart(data,newLine);
		   	}).error(function(data, status, headers, config){
		   		console.error(data);
		   	});
	    }
	   
		function loadScreenshotsFromClass(classObj){
			
		    $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimeStamp+'&classid='+classObj.id)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		ScreenshotMaster.data = data;
		    		vm.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, classObj, ScreenshotMaster.data);
		    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id;
		    		ScreenshotMaster.currentTimestamp = CurrentSuite.currentTimeStamp;
		    		setCaseSizeByMethod();
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
		function loadTimestamp(timestamp, firstLoad){
			vm.getSuiteSkeletonByTimestamp(timestamp, firstLoad);
			CurrentSuite.currentTimeStamp = timestamp;
		}
		
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
				vm.getPassFailByClass(timestamp, classObj[i]);
			}
		}
		
		function getPassFailByClass(timestamp, classObj){
			var stats = {
					passed:		0,
					failure: 	0,
					skipped: 	0,
					total:		0,
					totFail:	0
			};
			
			$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id)
			.success(function(data, status, headers, config){ 
				if(data){
					stats.passed = parseInt(data.passed);
					stats.failure = parseInt(data.failure);
					stats.error = parseInt(data.error);
					stats.skipped = parseInt(data.skipped);
					stats.total = parseInt(data.total);
					stats.totFail = getTotFail(data);
					
					classObj.stats = stats;
				}
			}).error(function(data, status, headers, config){
				console.error(data);
			});
		}
		
		function getPassFailTotByMethod(timestamp, classObj, methodArray){
			var methods = methodArray;
			for (var i = 0; i < methods.length; i++) {
				vm.getPassFailByMethod(timestamp, classObj, methods[i]);
			}
		}
		
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