(function(){
	'use strict';
	
	angular
		.module('webLog')
		.service('RestLoader', restLoader);
	
	restLoader.$inject = ['$http', '$state', 'CurrentSuite', 'Utilities', 'Charts', 'ScreenshotMaster','SuiteHandler'];
	
	function restLoader ($http, $state, CurrentSuite, Utilities, Charts, ScreenshotMaster, SuiteHandler){
		
		var vm = this;
		
		
		vm.addCaseToGraph 				= addCaseToGraph;
		vm.createHomeChartFromID 		= createHomeChartFromID;
		vm.getCases 					= getCases;
		vm.loadMainChart 				= loadMainChart;
		
		
	    function addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer, createMainChart){
	    	var dataRequest = {};
			dataRequest.suiteid = [CurrentSuite.currentSuiteInfo.id];
			dataRequest.reslimit = Utilities.getResLimit();
			dataRequest.os = [SuiteHandler.getOsIdByVersion(osName,osVersion)];
			dataRequest.devices = [SuiteHandler.getDeviceIdByName(deviceName)];
			dataRequest.browsers = [SuiteHandler.getBrowserIdByName(browserName, browserVer)];
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

		function getCases(){
		    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimestamp)
		    .success(function(data, status, headers, config){ 
		    	if(data){
		    		CurrentSuite.currentCases = data;
		    	}
		    }).error(function(data, status, headers, config){
		    	console.error(data);
		    });
		}
		
	    function loadMainChart(suiteID, newLine, createMainChart,name) {
	    	
	    	var i, activeQueries, requestObject;
	    	
		   	Charts.mainChartConfig.loading = 'Generating impressivly relevant statistics...';
		   	activeQueries = [];
		   	i = 0;
		   	
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
	 	   
	 	   requestObject = getGraphDataObject(suiteID,name);
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
	   
	    function getGraphDataObject(suiteID, name){
	    	var graphDataObject = [];
		    	
			switch ($state.$current.name) {
			case 'reports.methods':
				SuiteHandler.setChosenForCurrent(CurrentSuite.currentClass.id);
				break;
			case 'reports.cases':
				SuiteHandler.setChosenForCurrent(CurrentSuite.currentClass.id, CurrentSuite.currentMethod.id);
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
			var chosen = SuiteHandler.getChosen();
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
			var chosen = SuiteHandler.getChosen();
			var addedUnknown = false;
			
			chosen.devices = SuiteHandler.getDataFromSpecs('platforms','devices', 'deviceid');
			
			for (var i = 0; i < chosen.devices.length; i++) {
				var dataRequest = {};
				if (name) {
					dataRequest.name = name;
				} else {
					dataRequest.name = SuiteHandler.getDeviceByID(chosen.devices[i]);
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
			var chosen = SuiteHandler.getChosen();
			
			chosen.browsers = SuiteHandler.getDataFromSpecs('browsers', 'none', 'browserid');
			
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
					dataRequest.name = SuiteHandler.getBrowserByID(dataRequest.browsers[0]).browsername + " v." + SuiteHandler.getBrowserByID(dataRequest.browsers[0]).browserver;
				}
				
				graphArray.push(dataRequest);
			}
			return graphArray;
		}
			
		function splitDataOnVersion(suiteID, name) {
			var graphArray = [];
			var chosen = SuiteHandler.getChosen();
			
			chosen.os = SuiteHandler.getDataFromSpecs('platforms','versions', 'osid');
			
			for (var i = 0; i < chosen.os.length; i++) {
				var dataRequest = {};
				if (name) {
					dataRequest.name = name;
				} else {
					dataRequest.name = SuiteHandler.getVersionNameByID(chosen.os[i]);
				}
				dataRequest.suiteid = suiteID;
				dataRequest.reslimit = Utilities.getResLimit();
				
				dataRequest.os = [chosen.os[i]];
				dataRequest.devices = SuiteHandler.sortDevicesByOsId(chosen.devices, chosen.os[i]);
				dataRequest.browsers = chosen.browsers;
				dataRequest.classes = chosen.classes;
				dataRequest.testcases = chosen.testcases;
				
				graphArray.push(dataRequest);
			}
			return graphArray;
		}
		
		function splitDataOnPlatform(suiteID, name) {
			var graphArray = [];
			var chosen = SuiteHandler.getChosen();
			
			chosen.platforms = SuiteHandler.getDataFromSpecs('platforms','none','osname');
			
			for (var i = 0; i < chosen.platforms.length; i++) {
				var dataRequest = {};
		
				dataRequest.suiteid = suiteID;
				dataRequest.reslimit = Utilities.getResLimit();
				
				dataRequest.os = SuiteHandler.sortVersionsByPlatform(chosen.platforms[i], chosen.os);
				dataRequest.devices = SuiteHandler.sortDevicesByPlatform(chosen.platforms[i],chosen.devices);
				
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
	}
})();