angular.module('webLog')
.service('RestLoader', ['$http', '$state', 'CurrentSuite', 'Utilities', 'Charts', 'ScreenshotMaster', function($http, $state, CurrentSuite, Utilities, Charts, ScreenshotMaster){
	
	var restLoader = this;
	
	restLoader.loadTimestamp = function(timestamp, firstLoad){
		restLoader.getSuiteSkeletonByTimestamp(timestamp, firstLoad);
		CurrentSuite.currentTimeStamp = timestamp;
	};
	
	restLoader.getSuiteSkeleton = function(suite){
		CurrentSuite.currentSuiteInfo = suite;
	    $http.get('/api/suite/latestbyid?suiteid=' + suite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		getSpecsInfo(suite.id);
	    		CurrentSuite.currentSuite = data;
	    		
	    		var timestamp
	    		if (CurrentSuite.currentTimeStamp === '') {
					timestamp = suite.lastTimeStamp;
				} else {
					timestamp = CurrentSuite.currentTimeStamp;
					CurrentSuite.currentTimeStamp = suite.lastTimeStamp;
				}
	    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	};
	
	restLoader.getSuiteSkeletonByTimestamp = function(timestamp, firstLoad){
	    $http.get("/api/suite/bytimestamp?suiteid="+ CurrentSuite.currentSuiteInfo.id + "&timestamp="+timestamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSuite = data;
	    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
	    		if (CurrentSuite.currentClass != undefined) {
	    			CurrentSuite.currentMethods = CurrentSuite.getMethodsByClassId(CurrentSuite.currentClass.id);
	    			if (CurrentSuite.currentMethods !== undefined) {
	    				restLoader.getPassFailTotByMethod(timestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
					}
	    			if ($state.current.name === "reports.cases") {
	    				restLoader.getCases(CurrentSuite.currentMethod);
					}
				}
	    		if (firstLoad) {
	    			getSpecsInfo(CurrentSuite.currentSuiteInfo.id);
				}
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	};
	
    restLoader.addCaseToGraph = function(osName, osVersion, deviceName, browserName, browserVer, createMainChart){
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
    }
	
    restLoader.getMainGraphData = function(suiteID){
    	$scope.requestObject =  $scope.getGraphDataObject(suiteID);
    	var requestArray = [];
    	requestArray.push($scope.getGraphDataObject(suiteID));
    	$http.post('/api/stats/graphdata', requestArray)
    	.success(function(data, status, headers, config){ 
    		$scope.currentGraphData = data;
    	}).error(function(data, status, headers, config){
    		console.error(data);
    	});
    }
    
    restLoader.createHomeChartFromID = function(suite, createHomeChart) {
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
	
	restLoader.getPassFailByMethod = function(timestamp, classObj, method){
		$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id+'&testcaseid='+method.id)
		.success(function(data, status, headers, config){ 
			if(data){
				method.stats = data 
				method.stats.totFail = getTotFail(data);
			};
		}).error(function(data, status, headers, config){
			console.error(data);
		});
	}
    
	restLoader.getCases = function(method){
		CurrentSuite.currentMethod = method;
	    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimeStamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentCases = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	}
	
    restLoader.loadMainChart = function(suiteID, newLine, createMainChart) {
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
 	   
	   var requestObject = getGraphDataObject(suiteID);
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
   
	restLoader.loadScreenshotsFromClass = function(classObj){
		
	    $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimeStamp+'&classid='+classObj.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		ScreenshotMaster.data = data
	    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id
	    		ScreenshotMaster.currentTimestamp = CurrentSuite.currentTimeStamp;
	    		setCaseSizeByMethod();
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
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
	    	
	    };
	    
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
		};

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
	    };
	    
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
		};
		
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
	    };
	    
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
		};
   
	function getPassFailTotByClass(timestamp, classObj){
		for (var i = 0; i < classObj.length; i++) {
			restLoader.getPassFailByClass(timestamp, classObj[i]);
		}
	}
	
	restLoader.getPassFailByClass = function(timestamp, classObj){
		$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id)
		.success(function(data, status, headers, config){ 
			if(data){
				classObj.stats = data 
				classObj.stats.totFail = getTotFail(data);
			};
		}).error(function(data, status, headers, config){
			console.error(data);
		});
	}
	
	restLoader.getPassFailTotByMethod = function(timestamp, classObj){
		var methods = CurrentSuite.currentMethods;
		for (var i = 0; i < methods.length; i++) {
			restLoader.getPassFailByMethod(timestamp, classObj, methods[i]);
		}
	}
	
	function getSpecsInfo(suiteID){
		var reslimit = Utilities.getResLimit();
	    $http.get('/api/stats/options?suiteid='+suiteID+'&limit='+reslimit)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSpecObject = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	}
	
	function getTotFail(passFailData){
		var totFail = parseInt(passFailData.error) + parseInt(passFailData.failure);
		return totFail;
	}
	
}]);