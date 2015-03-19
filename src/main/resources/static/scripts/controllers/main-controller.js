angular.module('webLog')
    .controller('MainCtrl',['$scope', '$rootScope', '$http','$location', '$timeout','$state', 'CurrentSuite', 'Charts', 'Utilities','ScreenshotMaster', function($scope, $rootScope,$http, $location, $timeout, $state, CurrentSuite, Charts, Utilities,ScreenshotMaster){
    	
    $scope.Charts = Charts;
    $scope.CurrentSuite = CurrentSuite;
    $scope.Utilities = Utilities;
    $scope.ScreenshotMaster = ScreenshotMaster;
    $scope.$state = $state;
    $scope.chartHomeConfig = {};
    $scope.chartMainConfig = {};
    $scope.allSuites = [];
    $scope.chartVariants = ["Pass/Fail", "Total Pass", "Total Fail", "Run Time"];
    $scope.runValues = ["10", "20", "50", "100", "500"];
    var colors = ['#2ecc71', '#e74c3c', '#3498db', '#8e44ad', '#2c3e50', '#f1c40f', '#7f8c8d', '#e67e22', '#c0392b', '#1abc9c', '#9b59b6', '#34495e', '#16a085', '#f39c12', '#27ae60', '#d35400'];
    $scope.breakPoints = ["None", "Browser", "Version", "Device", "Platform"];
    $scope.breakPointChoice = "None";
    $scope.descTimestamps = [];
    $scope.setBreakPoint = function(choice){
    	$scope.breakPointChoice = choice;
    }
    
    $scope.mock = colors;
    
    function clearPlatformChosen(platform){
    	var platforms = CurrentSuite.currentSpecObject.platforms;
    	for (var i = 0; i < platforms.length; i++) {
			if (platforms[i].osname === platform.osname) {
				for (var j = 0; j < platforms[i].versions.length; j++) {
					if (platforms[i].versions[j].chosen) {
						delete platforms[i].versions[j].chosen;
					}
				}
				for (var j = 0; j < platforms[i].devices.length; j++) {
					if (platforms[i].devices[j].chosen) {
						delete platforms[i].devices[j].chosen;
					}
				}
			}
		}
    }
    
    $scope.togglePlatformChosen = function(platform){
    	if (platform.chosen != undefined){
    		if (platform.chosen === true) {
				clearPlatformChosen(platform);
			} else {
			}
    	}
    }
    
    //set value chosen true for graph data
    $scope.setChosen = function(value){
    	if(value.chosen){
    		delete value.chosen;
    	}
    	else{
    		value.chosen = true;
    	}
    }
    
    //checks if trashcan list contains more than one
    $scope.trashcanEmpty = function() {
    	if (Charts.mainChart.series.length < 2) {
    		return true;
    	}
    	else {
    		return false;
    	}
	};
    
	//remove object from data Array from trashcan
	$scope.remove = function(item) { 
		var index = Charts.mainChart.series.indexOf(item)
		Charts.mainChart.series.splice(index, 1);  
 		for (var i = 0; i < Charts.data.length; i++) {
			if (Charts.data[i].name === item.name) {
				Charts.data.splice(i, 1);
			}
		}
	}
    	
	//sets the amount of timestamps to load
    $scope.setResultAmount = function(value){
    	Utilities.resultAmount = value;
    }
    
    //colors reload button if changes are made to the query
    $scope.newContent = function(){
    	document.getElementById('button_reload').className = 'btn btn-primary';
    }
    
    //change view type of graph depending on dropdown
    $scope.changeChartVariant = function(input){
    	Utilities.graphView = input;
    	
    	switch (input) {
		case "Pass/Fail":
			passFailChart();
			break;
		case "Run Time":
			runTimeChart();
			break;
		case "Total Pass":
			totalPassChart();
			break;
		case "Total Fail":
			totalFailChart();
			break;
		default:
			Utilities.graphView = "Pass/Fail"
			passFailChart();
			break;
		}
    }
    
    //changes sorting in the class/method/case list
    $scope.setSorting = function(sorting){
    	switch (sorting) {
		case 'pass/fail':
			Utilities.sorting = ['result', 'name'];
			Utilities.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
			break;
		case 'time':
			Utilities.sorting = '-time';
			Utilities.caseSorting = '-timetorun';

		default:
			break;
		}
    }
    
    //stores chosen class info and clears chosen for other classes
    //when going into a class
	$scope.getMethods = function(testClass){
		$scope.clearOtherChosen(testClass);
		CurrentSuite.currentClass=testClass;
		CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
		getPassFailTotByMethod(CurrentSuite.currentTimeStamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
	}
	
	//used to clear other objects in the class/method/case view
	//when going into a deeper level
	$scope.clearOtherChosen = function(item){
		//remove classes checkbox
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen && CurrentSuite.currentSuite[i] != item) {
					delete CurrentSuite.currentSuite[i].chosen;
				}
			}
		}
		
		//remove method checkbox
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen && CurrentSuite.currentClass.testcases[i] != item) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
		
		//remove driver checkbox
		if (CurrentSuite.currentDrivers) {
			for (var i = 0; i < CurrentSuite.currentDrivers.length; i++) {
				if (CurrentSuite.currentDrivers[i].chosen && CurrentSuite.currentDrivers[i] != item) {
					delete CurrentSuite.currentDrivers[i].chosen;
				}
			}
		}
	}
	
	//use by "clear" button, clears every checkbox
	$scope.clearAllChosen = function(){
		//remove classes checkbox
		clearChosenClasses();
		
		//remove method checkbox
		clearChosenMethods();
		
		//remove browser checkbox
		clearChosenBrowsers();
		
		//remove device checkbox
		clearChosenDevices();
		
		//remove os checkbox
		clearChosenOs();
		
		//remove platform checkbox
		clearChosenPlatforms();
	}
	
	$scope.clearPlatformChosen = function(){
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].versions.length; j++) {
						delete CurrentSuite.currentSpecObject.platforms[i].versions[j].chosen;
				}
				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].devices.length; j++) {
					delete CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen;
				}
			}
	}
	
	function clearChosenOs() {
		if (CurrentSuite.currentSpecObject.platforms) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].versions.length; j++) {
					if (CurrentSuite.currentSpecObject.platforms[i].versions[j].chosen) {
						delete CurrentSuite.currentSpecObject.platforms[i].versions[j].chosen;
					}
				}
			}
		}
	}
	
	function clearChosenPlatforms(){
		if (CurrentSuite.currentSpecObject.platforms) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				if (CurrentSuite.currentSpecObject.platforms[i].chosen) {
					delete CurrentSuite.currentSpecObject.platforms[i].chosen;
				}
			}
		}
	}
	
	function clearChosenDevices() {
		if (CurrentSuite.currentSpecObject.platforms) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].devices.length; j++) {
					if (CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen) {
						delete CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen;
					}
				}
			}
		}
	}
	
	function clearChosenBrowsers() {
		if (CurrentSuite.currentSpecObject.browsers) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.browsers.length; i++) {
				if (CurrentSuite.currentSpecObject.browsers[i].chosen) {
					delete CurrentSuite.currentSpecObject.browsers[i].chosen;
				}
			}
		}
	}
	
	function clearChosenMethods() {
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
	}
	
	function clearChosenClasses() {
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					delete CurrentSuite.currentSuite[i].chosen
				}
			}
		}
	}
	
	//retrives all chosen in a object to
	//send in query
	$scope.getChosen = function(){
		var platforms = CurrentSuite.currentSpecObject.platforms;
		var browsers = CurrentSuite.currentSpecObject.browsers;
		var chosen = {
				os: [],
				devices: [],
				browsers: [],
				classes: [],
				testcases: [],
				platforms:[]
		};
		if (platforms) {
			//add version to send
				for (var i = 0; i < platforms.length; i++) {
					var versions = platforms[i].versions;
					for (var j = 0; j < versions.length; j++) {
						if (versions[j].chosen) {
							chosen.os.push(versions[j].osid);
						}
					}
				}
			
			//add devices to send
				for (var i = 0; i < platforms.length; i++) {
					var devices= platforms[i].devices;
					for (var j = 0; j < devices.length; j++) {
						if (devices[j].chosen) {
							chosen.devices.push(devices[j].deviceid);
						}
					}
				}
			
			//add platform to send
				for (var i = 0; i < platforms.length; i++) {
					if (platforms[i].chosen) {
						var osids = [];
						if (containsChosen(platforms[i].versions)) {
							for (var j = 0; j < platforms[i].versions.length; j++) {
								if (chosen.os.indexOf(platforms[i].versions[j].osid) == -1) {
									if (platforms[i].versions[j].chosen) {
										chosen.os.push(platforms[i].versions[j].osid);
									}
								}
							}
						} else {
							for (var j = 0; j < platforms[i].versions.length; j++) {
								chosen.os.push(platforms[i].versions[j].osid);
							}
						}
						chosen.platforms.push(platforms[i].osname);
					}
				}
			
			//add browsers to send
				for (var i = 0; i < browsers.length; i++) {
					if (browsers[i].chosen) {
						chosen.browsers.push(browsers[i].browserid);
					}
				}
			}
		
		//add classes to send
		if (CurrentSuite.currentSuite) {
			var classes = CurrentSuite.currentSuite;
			for (var i = 0; i < classes.length; i++) {
				if (classes[i].chosen) {
					chosen.classes.push(classes[i].id);
				}
			}
		}
		
		//add methods to send
		if (CurrentSuite.currentClass.testcases) {
			var testcases = CurrentSuite.currentClass.testcases
			for (var i = 0; i < testcases.length; i++) {
				if (testcases[i].chosen) {
					chosen.testcases.push(testcases[i].id);
				}
			}
		}
		return chosen;
	}
	
	function getMethodsByClassId(id) {
		for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
			if (CurrentSuite.currentSuite[i].id === id) {
				return CurrentSuite.currentSuite[i].testcases;
			}
		}
	}
	
	function getCasesByMethodId(id) {
		for (var i = 0; i < CurrentSuite.currentMethods.length; i++) {
			if (CurrentSuite.currentMethods[i].id === id) {
				return CurrentSuite.currentSuite[i].testcases;
			}
		}
	}
	
	function getCurrentPosName(){
		switch ($state.current.name) {
		case 'reports.classes':
			return CurrentSuite.currentSuiteInfo.name;
			break;
		case 'reports.methods':
			var className = CurrentSuite.currentClass.name.substring(CurrentSuite.currentClass.name.lastIndexOf('.') +1);
			return className;
			break;
		case 'reports.cases':
			return CurrentSuite.currentMethod.name;
			break;
		case 'home':
			return 'Aggregation';
			break;
		default:
			return 'Aggregation';
			break;
		}
	}
	
	function getDeviceIdByName(deviceName){
		var specs = CurrentSuite.currentSpecObject;
		for (var i = 0; i < specs.platforms.length; i++) {
			for (var j = 0; j < specs.platforms[i].devices.length; j++) {
				if (specs.platforms[i].devices[j].devicename === deviceName) {
					return specs.platforms[i].devices[j].deviceid;
				}
			}
		}
	}
	
	function getOsIdByVersion(osName,osVersion){
		var specs = CurrentSuite.currentSpecObject;
		for (var i = 0; i < specs.platforms.length; i++) {
			if (specs.platforms[i].osname === osName) {
				for (var j = 0; j < specs.platforms[i].versions.length; j++) {
					if (specs.platforms[i].versions[j].osver === osVersion) {
						return specs.platforms[i].versions[j].osid;
					}
				}
			}
		}
	}
	
	function getBrowserIdByname(browserName, browserVer){
		var specs = CurrentSuite.currentSpecObject;
		for (var i = 0; i < specs.browsers.length; i++) {
			if (specs.browsers[i].browsername === browserName && specs.browsers[i].browserver === browserVer) {
				return specs.browsers[i].browserid;
				
			}
		}
	}
    // HTTP -----------------------------------------------------------------------------------------------------------
	
    $http.get('/api/suite/getsuites')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.allSuites = data;
    		for (var i = 0; i < $scope.allSuites.length; i++) {
    			$scope.createHomeChartFromID($scope.allSuites[i]);
			}
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
	
	$scope.getSpecsInfo = function(suiteID){
		var reslimit = getResLimit();
	    $http.get('/api/stats/options?suiteid='+suiteID+'&limit='+reslimit)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSpecObject = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getSuiteSkeleton = function(suite){
		CurrentSuite.currentSuiteInfo = suite;
	    $http.get('/api/suite/latestbyid?suiteid=' + suite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		$scope.getSpecsInfo(suite.id);
	    		CurrentSuite.currentSuite = data;
	    		
	    		var timestamp
	    		if (CurrentSuite.currentTimeStamp === '') {
					timestamp = suite.lastTimeStamp;
				} else {
					timestamp = CurrentSuite.currentTimeStamp;
				}
	    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getSuiteSkeletonByTimeStamp = function(timestamp){
	    $http.get("/api/suite/bytimestamp?suiteid="+ CurrentSuite.currentSuiteInfo.id + "&timestamp="+timestamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSuite = data;
	    		getPassFailTotByClass(timestamp, CurrentSuite.currentSuite);
	    		if (CurrentSuite.currentClass != undefined) {
	    			CurrentSuite.currentMethods = getMethodsByClassId(CurrentSuite.currentClass.id);
	    			getPassFailTotByMethod(timestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
	    			if ($state.current.name === "reports.cases") {
	    				$scope.getCases(CurrentSuite.currentMethod);
					}
				}
	    		$scope.getSpecsInfo(CurrentSuite.currentSuiteInfo.id);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getCases = function(method){
		CurrentSuite.currentMethod = method;
	    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimeStamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentCases = data;
	    		console.log(data);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.setCurrentSuite = function(suite){
		CurrentSuite.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+CurrentSuite.currentSuite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	};
	
	$scope.setCurrentSuiteByTimestamp = function(suite, timestamp){
		CurrentSuite.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+CurrentSuite.currentSuite.id+"&timestamp"+timestamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	};
	
	function getPassFailTotByClass(timestamp, classObj){
		for (var i = 0; i < classObj.length; i++) {
			getPassFailByClass(timestamp, classObj[i]);
		}
	}
	
	function getPassFailTotByMethod(timestamp, classObj, methods){
		for (var i = 0; i < methods.length; i++) {
			getPassFailByMethod(timestamp, classObj, methods[i]);
		}
	}
	
	function getPassFailByClass(timestamp, classObj){
		$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id)
		.success(function(data, status, headers, config){ 
			if(data){
				classObj.stats = data 
				classObj.stats.totFail = getTotFail(data);
			};
		}).error(function(data, status, headers, config){
			console.log(data);
		});
	}
	
	function getPassFailByMethod(timestamp, classObj, method){
		$http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classObj.id+'&testcaseid='+method.id)
		.success(function(data, status, headers, config){ 
			if(data){
				method.stats = data 
				method.stats.totFail = getTotFail(data);
			};
		}).error(function(data, status, headers, config){
			console.log(data);
		});
	}
	
	function getTotFail(passFailData){
		var totFail = parseInt(passFailData.error) + parseInt(passFailData.failure);
		return totFail;
	}
	
   $scope.loadMainChart = function(suiteID, newLine) {
	   	Charts.mainChart.loading = 'Generating impressivly relevant statistics...';
	   	var activeQueries = [];
 	   if (!newLine) {
		   CurrentSuite.activeQueries = [];
	   } else {
		   for (var i = 0; i < CurrentSuite.activeQueries.length; i++) {
				   for (var j = 0; j < CurrentSuite.activeQueries[i].length; j++) {
					   if (CurrentSuite.activeQueries[i][j].reslimit > getResLimit() || CurrentSuite.activeQueries[i][j].reslimit < getResLimit()) {
						   CurrentSuite.activeQueries[i][j].reslimit = getResLimit();
					   }
					   activeQueries.push(CurrentSuite.activeQueries[i][j]);
				   }
		   }
		   CurrentSuite.activeQueries = [];
	   }
 	   
	   var requestObject = $scope.getGraphDataObject(suiteID);
	   	for (var i = 0; i < activeQueries.length; i++) {
			requestObject.push(activeQueries[i]);
		}
	   CurrentSuite.lastRunSize = getResLimit();
	   $http.post('/api/stats/graphdata', requestObject)
	   .success(function(data, status, headers, config){
		   $scope.createMainChart(data, newLine);
	   }).error(function(data, status, headers, config){
		   console.log(data);
	   });
   };
   
   function highlightPoint(timestamp){
	   for (var i = 0; i < Charts.mainChart.series.length; i++) {
		   for (var j = 0; j < Charts.mainChart.series[i].data.length; j++) {
			   if (Charts.mainChart.xAxis.categories[j] === timestamp){
				   Charts.mainChart.xAxis.plotLines[0].value = j;
				   return;
			   }
		   }
	   }
	   delete Charts.mainChart.xAxis.plotLines[0].value;
   }
   
   $scope.loadNewTimeStamp = function(timestamp){
	   $scope.getSuiteSkeletonByTimeStamp(timestamp);
	   CurrentSuite.currentTimeStamp = timestamp;
	   highlightPoint(timestamp);
   }
    
    $scope.createHomeChartFromID = function(suite) {
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
    		$scope.createHomeChart(data, suite);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
	};
		
    $scope.getMainGraphData = function(suiteID){
    	$scope.requestObject =  $scope.getGraphDataObject(suiteID);
    	var requestArray = [];
    	requestArray.push($scope.getGraphDataObject(suiteID));
    	$http.post('/api/stats/graphdata', requestArray)
    	.success(function(data, status, headers, config){ 
    		$scope.currentGraphData = data;
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
    }
    
    $scope.addCaseToGraph = function(osName, osVersion, deviceName, browserName, browserVer){
    	var dataRequest = {};
		dataRequest.suiteid = [CurrentSuite.currentSuiteInfo.id];
		dataRequest.reslimit = getResLimit();
		dataRequest.os = [getOsIdByVersion(osName,osVersion)];
		dataRequest.devices = [getDeviceIdByName(deviceName)];
		dataRequest.browsers = [getBrowserIdByname(browserName, browserVer)];
		dataRequest.classes = [CurrentSuite.currentClass.id];
		dataRequest.testcases = [CurrentSuite.currentMethod.id];
		dataRequest.name = osName+"-"+osVersion+"-"+deviceName+"-"+browserName+"-"+browserVer;
		
		var requestObj = [dataRequest];
    	$http.post('/api/stats/graphdata', requestObj)
    	.success(function(data, status, headers, config){
    		$scope.createMainChart(data, true);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
    }
    
    function setChosenForCurrent(classId, methodId){
    	for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
			if (CurrentSuite.currentSuite[i].id === classId) {
				if (methodId) {
					for (var j = 0; j < CurrentSuite.currentSuite[i].testcases.length; j++) {
						if (CurrentSuite.currentSuite[i].testcases[j].id === methodId) {
							CurrentSuite.currentSuite[i].testcases[j].chosen = true;
							break;
						}
					}
				}
					CurrentSuite.currentSuite[i].chosen = true;
					break;
				}
			}
		}
    
    $scope.getGraphDataObject = function(suiteID, name){
    	var graphDataObject = [];
    	
    	switch ($state.$current.name) {
		case 'reports.methods':
			setChosenForCurrent(CurrentSuite.currentClass.id);
			break;
		case 'reports.cases':
			setChosenForCurrent(CurrentSuite.currentClass.id, CurrentSuite.currentMethod.id);
			break;
		default:
			break;
		}
    	
    	switch ($scope.breakPointChoice) {
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
			console.log("Something went wrong");
			break;
		}
    	CurrentSuite.activeQueries.push(graphDataObject);
    	return graphDataObject;
    	
    };
    
    function splitDataOnDevice(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	var addedUnknown = false;
    	
    	chosen.devices = getDataFromSpecs('platforms','devices', 'deviceid');
    	
    	for (var i = 0; i < chosen.devices.length; i++) {
    		var dataRequest = {};
    		if (name) {
    			dataRequest.name = name;
    		} else {
    			dataRequest.name = getDeviceByID(chosen.devices[i]);
    		}
    		dataRequest.suiteid = suiteID;
    		dataRequest.reslimit = getResLimit();
    		
    		dataRequest.os = chosen.os;
    		dataRequest.devices = [chosen.devices[i]];
    		dataRequest.browsers = chosen.browsers;
    		dataRequest.classes = chosen.classes;
    		dataRequest.testcases = chosen.testcases;
			
    		graphArray.push(dataRequest);
		}
    	return graphArray;
    };
    
    function containsChosen(array){
    	for (var i = 0; i < array.length; i++) {
			if (array[i].chosen) {
				return true;
			}
		}
    	return false;
    };
    
    function getDataFromSpecs(base, child, key){
    	var specs = CurrentSuite.currentSpecObject[base];
    	var dataArray = [];
    	var chosenBaseArray = [];
    	
    	//browsers
    	if (base == 'browsers') {
			dataArray = getBrowsers(specs, key);
		} else {
		
		//the rest
			//if chosen patforms are found, only add the chosen
			if (containsChosen(specs)) {
				for (var i = 0; i < specs.length; i++) {
					if (specs[i].chosen) {
			    		if (child != 'none') {
			    			//if chosen childs are found only add the chosen
			    			if (containsChosen(specs[i][child])) {
					    		for (var j = 0; j < specs[i][child].length; j++) {
					    			if (specs[i][child][j].chosen) {
					    				dataArray.push(specs[i][child][j][key]);
									}
								}
			    			} else {
			    				//if NO chosen childs found adding all
		    					for (var j = 0; j < specs[i][child].length; j++) {
		    						dataArray.push(specs[i][child][j][key]);
		    					}
			    			}
			    			//if no child is specified adding osName
			    		} else {
			    			dataArray.push(specs[i][key]);
			    		}
					}
				}
				//if NO chosen platforms are found adding all
			} else {
		    	for (var i = 0; i < specs.length; i++) {
		    		if (child != 'none') {
		    			//if chosen child found adding chosen
		    			if (containsChosen(specs[i][child])) {
				    		for (var j = 0; j < specs[i][child].length; j++) {
				    			if (specs[i][child][j].chosen) {
				    				dataArray.push(specs[i][child][j][key]);
								}
							}
		    			} else {
		    				//if NO chosen child found adding all
	    					for (var j = 0; j < specs[i][child].length; j++) {
	    						dataArray.push(specs[i][child][j][key]);
	    					}
		    			}
		    			//if no child is specified adding osName
		    		} else {
		    			dataArray.push(specs[i][key]);
		    		}
				}
			}
		}
	    return dataArray;
    };
    
    function getBrowsers(specs, key){
    	browserIDs = [];
    	if (containsChosen(specs)) {
	    	for (var i = 0; i < specs.length; i++) {
	    		if (specs[i].chosen) {
	    			browserIDs.push(specs[i][key]);
				}
			}
    	} else {
    		for (var i = 0; i < specs.length; i++) {
				browserIDs.push(specs[i][key]);
			}
    	}
    	return browserIDs;
    }
    
    function getDevices(){
    	var specs = CurrentSuite.currentSpecObject;
    	var deviceIDs = [];
    	
    	for (var i = 0; i < specs.platforms.length; i++) {
			for (var j = 0; j < specs.platforms[i].devices.length; j++) {
				 
			}
		}
    	
    	for (var i = 0; i < specs.platforms.length; i++) {
    		for (var j = 0; j < specs.platforms[i].devices.length; j++) {
    			if (deviceIDs.indexOf(specs.platforms[i].devices[j].deviceid) == -1) {
    				deviceIDs.push(specs.platforms[i].devices[j].deviceid);
				}
			}
		}
    	return deviceIDs;
    }
    
    function splitDataOnVersion(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	
    	chosen.os = getDataFromSpecs('platforms','versions', 'osid');
    	
    	for (var i = 0; i < chosen.os.length; i++) {
    		var dataRequest = {};
    		if (name) {
    			dataRequest.name = name;
    		} else {
    			dataRequest.name = getVersionNameByID(chosen.os[i]);
    		}
    		dataRequest.suiteid = suiteID;
    		dataRequest.reslimit = getResLimit();
    		
    		dataRequest.os = [chosen.os[i]];
    		dataRequest.devices = sortDevicesByOsId(chosen.devices, chosen.os[i]);
    		dataRequest.browsers = chosen.browsers;
    		dataRequest.classes = chosen.classes;
    		dataRequest.testcases = chosen.testcases;
			
    		graphArray.push(dataRequest);
		}
    	return graphArray;
    };
    
    function sortDevicesByOsId(devices, osid){
    	var sortedDevices = [];
    	var platformName = '';
    	var platforms = CurrentSuite.currentSpecObject.platforms

    	for (var i = 0; i < platforms.length; i++) {
    		if (platforms[i].chosen) {
    			if (containsOsId(osid, platforms[i].versions)) {
					for (var j = 0; j < platforms[i].devices.length; j++) {
						for (var k = 0; k < devices.length; k++) {
							if (devices[k] === platforms[i].devices[j].deviceid) {
								sortedDevices.push(platforms[i].devices[j].deviceid);
							}
						}
					}
					if (sortedDevices.length === 0) {
						for (var j = 0; j < platforms[i].devices.length; j++) {
							sortedDevices.push(platforms[i].devices[j].deviceid);
						}
					}
    			}
    		}
		}
    	return sortedDevices;
    }
    
    function containsOsId(osid, array){
    	for (var i = 0; i < array.length; i++) {
			if (array[i].osid === osid) {
				return true;
			}
		}
    	return false;
    }
    
    function splitDataOnBrowser(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	
    	chosen.browsers = getDataFromSpecs('browsers', 'none', 'browserid');
    	
    	for (var i = 0; i < chosen.browsers.length; i++) {
    		var dataRequest = {};

    		dataRequest.suiteid = suiteID;
    		dataRequest.reslimit = getResLimit();
    		
    		dataRequest.os = chosen.os;
    		dataRequest.browsers = [chosen.browsers[i]];
    		dataRequest.devices = chosen.devices;
    		dataRequest.classes = chosen.classes;
    		dataRequest.testcases = chosen.testcases;
    		
    		if (name) {
    			dataRequest.name = name;
    		} else {
    			dataRequest.name = getBrowserByID(dataRequest.browsers[0]).browsername+" v."+getBrowserByID(dataRequest.browsers[0]).browserver;
    		}
    		
    		graphArray.push(dataRequest);
		}
    	return graphArray;
	};
    
    function splitDataOnPlatform(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	
    	chosen.platforms = getDataFromSpecs('platforms','none','osname');
    	
    	for (var i = 0; i < chosen.platforms.length; i++) {
    		var dataRequest = {};

    		dataRequest.suiteid = suiteID;
    		dataRequest.reslimit = getResLimit();
    		
    		dataRequest.os = sortVersionsByPlatform(chosen.platforms[i], chosen.os);
    		dataRequest.devices = sortDevicesByPlatform(chosen.platforms[i],chosen.devices);
    		
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
	
    function getAllDataAsOne(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	var dataRequest = {};
    	
    	if (name) {
    		dataRequest.name = name;
		} else {
			dataRequest.name = getCurrentPosName();
		}
    	
		dataRequest.suiteid = suiteID;
		dataRequest.reslimit = getResLimit();
		
		dataRequest.os = chosen.os;
		dataRequest.devices = chosen.devices;
		dataRequest.browsers = chosen.browsers;
		dataRequest.classes = chosen.classes;
		dataRequest.testcases = chosen.testcases;
		
		graphArray.push(dataRequest);
		
    	return graphArray;
	};
    
    function getBrowserByID(id){
    	for (var i = 0; i < CurrentSuite.currentSpecObject.browsers.length; i++) {
			if (CurrentSuite.currentSpecObject.browsers[i].browserid === id) {
				return CurrentSuite.currentSpecObject.browsers[i];
			}
		}
    };
    
    function getAllBrowsers(){
    	var specs = CurrentSuite.currentSpecObject;
    	var browserIDs = [];
    	for (var i = 0; i < specs.browsers.length; i++) {
    		browserIDs.push(specs.browsers[i].browserid);
		}
    	return browserIDs;
    }
    
    function getVersionNameByID(id){
    	for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
    		var platform = CurrentSuite.currentSpecObject.platforms[i];
			for (var j = 0; j < platform.versions.length; j++) {
				if (platform.versions[j].osid === id) {
					return platform.osname + " " + platform.versions[j].osver;
				}
			}
		}
    };
    
    function getVersionsByDevice(chosen){
    	var specs = CurrentSuite.currentSpecObject;
    	var versions = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
    		for (var j = 0; j < specs.platforms[i].devices.length; j++) {
	    		for (var k = 0; k < chosen.devices.length; k++) {
	    			if (chosen.devices[k] === specs.platforms[i].devices[j].deviceid) {
	    				versions.push(specs.platforms[i].devices[j].osver);
					}
				}
    		}
		}
    	var versionsToReturn = [];
    	for (var i = 0; i < versions.length; i++) {
    		for (var k = 0; k < specs.platforms.length; k++) {
				for (var j = 0; j < specs.platforms[k].versions.length; j++) {
					if (specs.platforms[k].versions[j].osver === versions[i]) {
						versionsToReturn.push(specs.platforms[k].versions[j].osid);
					}
				}
    		}
		}
    	return versionsToReturn;
    };
    
    function getAllVersions(){
    	var specs = CurrentSuite.currentSpecObject;
    	var versions = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
			var platform = specs.platforms[i];
			for (var j = 0; j < platform.versions.length; j++) {
				versions.push(platform.versions[j].osid);
			}
		}
    	return versions;
    }
    
    function sortVersionsByPlatform(platformName, chosenOS){
    	var specs = CurrentSuite.currentSpecObject;
    	var versions = [];
    	if (chosenOS.length === 0) {
			versions = getVersionsByPlatform(platformName, chosenOS);
			return versions;
		} else {
			var allVersions = [];
			var chosenVersions=[];
			for (var i = 0; i < specs.platforms.length; i++) {
				if (specs.platforms[i].osname === platformName) {
					for (var j = 0; j < specs.platforms[i].versions.length; j++) {
						allVersions.push(specs.platforms[i].versions[j].osid);
						if (specs.platforms[i].versions[j].chosen) {
							chosenVersions.push(specs.platforms[i].versions[j].osid);
						}
					}
				}
			}
		}
    	if (chosenVersions.length === 0) {
			return allVersions;
		} else {
			return chosenVersions;
		}
    }
    
    function sortDevicesByPlatform(platformName, chosenDevices){
    	var specs = CurrentSuite.currentSpecObject;
    	var devices = [];
    	if (chosenDevices.length === 0) {
			devices = getDevicesByPlatform(platformName, chosenDevices);
			return devices;
		} else {
			var allDevices = [];
			var chosenDevices=[];
			for (var i = 0; i < specs.platforms.length; i++) {
				if (specs.platforms[i].osname === platformName) {
					for (var j = 0; j < specs.platforms[i].devices.length; j++) {
						allDevices.push(specs.platforms[i].devices[j].deviceid);
						if (specs.platforms[i].devices[j].chosen) {
							chosenDevices.push(specs.platforms[i].devices[j].deviceid);
						}
					}
				}
			}
		}
    	if (chosenDevices.length === 0) {
			return allDevices;
		} else {
			return chosenDevices;
		}
    }
    
    function getDevicesByPlatform(platformName, chosenDevices){
    	var specs = CurrentSuite.currentSpecObject;
    	var platformDevices = [];
    		for (var i = 0; i < specs.platforms.length; i++) {
    			if (specs.platforms[i].osname === platformName) {
    				for (var j = 0; j < specs.platforms[i].devices.length; j++) {
    					platformDevices.push(specs.platforms[i].devices[j].deviceid);
    				}
    			}
    		}
    	return platformDevices;
    }
    
    function getVersionsByPlatform(platformName, chosenOS){
    	var specs = CurrentSuite.currentSpecObject;
    	var platformVersions = [];
    		for (var i = 0; i < specs.platforms.length; i++) {
    			if (specs.platforms[i].osname === platformName) {
    				for (var j = 0; j < specs.platforms[i].versions.length; j++) {
    					platformVersions.push(specs.platforms[i].versions[j].osid);
    				}
    			}
    		}
    	return platformVersions;
    }
    
    function getAllPlatforms(){
    	var specs = CurrentSuite.currentSpecObject;
    	var platforms = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
			platforms.push(specs.platforms[i].osname);
		}
    	return platforms;
    }
    
    function getPlatformsByOs(chosen){
    	var specs = CurrentSuite.currentSpecObject.platforms;
    	
    	for (var i = 0; i < specs.length; i++) {
			
		}
    }
    
    function getDeviceByID(id){
    	for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
    		var platform = CurrentSuite.currentSpecObject.platforms[i];
			for (var j = 0; j < platform.devices.length; j++) {
				if (platform.devices[j].deviceid === id) {
					if (platform.devices[j].devicename === "UNKNOWN") {
						return "Others";
					} else {
						return platform.devices[j].devicename;
					}
				}
			}
		}
    };
    
    function getAllDevicesByPlatform(chosen){
    	var specs = CurrentSuite.currentSpecObject;
    	var deviceIDs = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
    		for (var k = 0; k < chosen.platforms.length; k++) {
				if (chosen.platforms[k] === specs.platforms[i].osname) {
					for (var j = 0; j < specs.platforms[i].devices.length; j++) {
						deviceIDs.push(specs.platforms[i].devices[j].deviceid);
					}
				}
			}
		}
    	return deviceIDs;
    }
    
    function getAllDevicesByVersion(chosen){
    	var specs = CurrentSuite.currentSpecObject;
    	var versions = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
			for (var j = 0; j < specs.platforms[i].versions.length; j++) {
				if (specs.platforms[i].versions[j].chosen) {
					versions.push(specs.platforms[i].versions[j].osver);
				}
			}
		}
    	
    	var devices = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
			for (var j = 0; j < specs.platforms[i].devices.length; j++) {
				for (var k = 0; k < versions.length; k++) {
					if (versions[k] === specs.platforms[i].devices[j].osver) {
						devices.push(specs.platforms[i].devices[j].deviceid);
					}
				}
			}
		}
    	return devices;
    }
    
    function getResLimit() {
		var reslimit = Utilities.resultAmount;
		if (!(isNaN(reslimit)) && !(reslimit === "")) {
			reslimit = parseInt(reslimit);
		} else {
			reslimit = 50;
		}
		return reslimit;
	};
	
	function reverseArray(array){
		var length = array.length;
		var reverseArray = [];
		for (var i = length-1; i >= 0; i--) {
			reverseArray.push(array[i]);
		}
		return reverseArray;
	}
	
	function getSerieColor(i){
		if (i > colors.length -1) {
			i = i - (colors.length - 1);
		}
		return colors[i];
	}
	
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
	function isSkipped(dataObj){
		var passed = dataObj.pass> 0;
		var failed = dataObj.fail > 0;
		var error = dataObj.error > 0;
		var skipped = dataObj.skipped;
		if (!passed && !failed && !error && skipped > 0) {
			return true;
		} else {
			return false;
		}
	}
	
    $scope.createMainChart = function(data, newLine){
    	if (data.length === 0) {
    		alert("There is no data for that combination");
    		Charts.mainChart.loading = false;
    		return;
		}
    	CurrentSuite.currentTimeStampArray = [];
    	for (var index = 0; index < data[0].data.length; index++) {
			CurrentSuite.currentTimeStampArray.push(data[0].data[index].timestamp);
		}
    	
    	if (CurrentSuite.currentTimeStamp === '') {
    		CurrentSuite.currentTimeStamp = data[0].data[data[0].data.length-1].timestamp;
		}
    	$scope.descTimestamps = reverseArray(CurrentSuite.currentTimeStampArray);
    	var graphDataArray = [];
    	for (var i = 0; i < data.length; i++) {
    		var graphDataObj = {
    				runTime: [],
    				totalPass: [],
    				totalFail: [],
    				passPercentage: []
    		};
    		var graphName = data[i].name;
    		
			for (var j = 0; j < data[i].data.length; j++) {
				if (isSkipped(data[i].data[j])) {
					graphDataObj.runTime.push(null);
					graphDataObj.totalPass.push(null);
					graphDataObj.totalFail.push(null);
					graphDataObj.passPercentage.push(null);
				} else {
					graphDataObj.runTime.push(data[i].data[j].time);
					graphDataObj.totalPass.push(data[i].data[j].pass);
					graphDataObj.totalFail.push(data[i].data[j].fail + data[i].data[j].error);
					graphDataObj.passPercentage.push(Math.round(getPassPercentage(data[i].data[j].pass, data[i].data[j].fail, data[i].data[j].error)));
				}
			}
			graphDataObj.name = graphName;
			graphDataArray.push(graphDataObj);
		}
    	if (newLine) {
    		Charts.data = [];
    		for (var i = 0; i < graphDataArray.length; i++) {
				Charts.data.push(graphDataArray[i]);
			}
		} else {
			Charts.data = graphDataArray;
		}
    	
    	Charts.mainChart.xAxis.categories = CurrentSuite.currentTimeStampArray;
    	Charts.mainChart.options.plotOptions.series.point.events.click = function (e) {
    		$scope.loadNewTimeStamp(this.category);
    	};
    	Charts.mainChart.subtitle.text = "Showing " + CurrentSuite.currentTimeStampArray.length + " results";
		$scope.chartMainConfig = Charts.mainChart;
			
			for (var i = 0; i < Charts.data.length; i++) {
		    	Charts.mainChart.series.push({
					data : Charts.data[i].passPercentage,
					name : Charts.data[i].name,
				});
			}
			if(CurrentSuite.currentTimeStampArray.length <= 50){
				Charts.mainChart.xAxis.tickInterval = 0.4;
			} else if(CurrentSuite.currentTimeStampArray.length > 50 && CurrentSuite.currentTimeStampArray.length <= 100){
	        	Charts.mainChart.xAxis.tickInterval = 2;
	    	} else{
	    		Charts.mainChart.xAxis.tickInterval = 5;
	    	}
			$scope.changeChartVariant(Utilities.graphView);
			Charts.mainChart.loading = false;
			highlightPoint(CurrentSuite.currentTimeStamp);
    };
    
    function getPassPercentage(pass, fail, error){
    	var totalFail = fail + error;
    	var total = pass + totalFail;
    	var percentage = (pass/total)*100;
    	return percentage;
    }
    
    $scope.createHomeChart = function(data, suite) {
    	var timeStamps = [];
    	for (var index = 0; index < data[0].data.length; index++) {
			timeStamps.push(data[0].data[index].timestamp);
		}
    	
    	suite.lastTimeStamp = timeStamps[timeStamps.length-1];
    	
        var chartHomeConfigObject = {
				options : {
					chart : {
						type : "areaspline",
						backgroundColor : '#ecf0f1'
					},
					plotOptions : {
						series : {
							cursor : 'pointer',
							stacking : "normal",
							point: {
								events: {
									click: function(e){
										CurrentSuite.currentSuiteInfo = suite;
										$scope.loadNewTimeStamp(this.category);
										$scope.getSuiteSkeletonByTimeStamp(this.category);
										$scope.loadMainChart(suite.id);
										$state.transitionTo('reports.classes');
								}
							}
							}
						}
					}
				},
				title:{
					text: suite.name
					},
				subtitle:{
					text: "Last 50 test runs"
				},
				series : [ {
					data : [],
					id : "pass",
					name : "Pass",
					type : "column",
					color : "green",
					dashStyle : "Solid",
					connectNulls : false
				}, {
					data : [],
					id : "fail",
					name : "Fail",
					type : "column",
					color : '#FF0000',
					dashStyle : "Solid",
					connectNulls : false
				} ],
				xAxis : {
					tickInterval: 2,
					labels : {
						rotation : 45
					}
				},	
				yAxis : {
					title : {
						text : 'Number of tests'
					},
				},
				useHighStocks : false,
				size : {
					height : 400
				},
				// function (optional)
				func : function(chart) {
					// setup some logic for the chart
				}
			}
    	
        chartHomeConfigObject.series[0].data = [];
        chartHomeConfigObject.series[1].data = [];
        
		for (var j = 0; j < data[0].data.length; j++) {
			chartHomeConfigObject.series[0].data.push(data[0].data[j].pass);
			chartHomeConfigObject.series[1].data.push(data[0].data[j].fail + data[0].data[j].error);
		}
		chartHomeConfigObject.xAxis.categories = timeStamps;
		$scope.chartHomeConfig[suite.id] = chartHomeConfigObject;
    };
    
    function runTimeChart() {
    	
    	var chart = Charts.mainChart;
    	chart.options.chart.type = "line";
    	chart.series = [];
    	chart.yAxis.max = undefined;
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
    					data : Charts.data[i].runTime,
    					name : Charts.data[i].name,
    					color: getSerieColor(i),
    		});
		}
    	
    	chart.yAxis.title.text = 'Seconds';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Time to run in seconds";
    	Charts.mainChart.options.tooltip.valueDecimals = 2;
	}
    
    function totalPassChart() {
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "";
    	chart.series = [];
    	chart.yAxis.max = undefined;
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
				data : Charts.data[i].totalPass,
				name : Charts.data[i].name,
				color: getSerieColor(i),
				type : "column",
				dashStyle : "Solid",
				connectNulls : false
    		});
		}
    	chart.yAxis.title.text = 'Passed tests';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Passed tests";
    	delete Charts.mainChart.options.tooltip.valueDecimals;
	}
    
    function totalFailChart() {
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "";
    	chart.series = [];
    	chart.yAxis.max = undefined;
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
				data : Charts.data[i].totalFail,
				name : Charts.data[i].name,
				color: getSerieColor(i),
				type : "column",
				dashStyle : "Solid",
				connectNulls : false
    		});
		}
    	chart.yAxis.title.text = 'Failed tests';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Failed tests";
    	delete Charts.mainChart.options.tooltip.valueDecimals;
	}
    
    function passFailChart() {
    	var chart = Charts.mainChart;
    	
    	chart.series = [];
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
    			data : Charts.data[i].passPercentage,
    			name : Charts.data[i].name,
    			color: getSerieColor(i),
    			type: "line"
    		});
		}
    	chart.yAxis.title.text = 'Pass percentage';
    	chart.title.text = "Percentage of passed tests";
    	delete Charts.mainChart.options.tooltip.valueDecimals;
	}
}])
.animation('.slide-animation', function () {
        return {
            beforeAddClass: function (element, className, done) {
                var scope = element.scope();

                if (className == 'ng-hide') {
                    var finishPoint = element.parent().width();
                    if(scope.direction !== 'right') {
                        finishPoint = -finishPoint;
                    }
                    TweenMax.to(element, 0.5, {left: finishPoint, onComplete: done });
                }
                else {
                    done();
                }
            },
            removeClass: function (element, className, done) {
                var scope = element.scope();

                if (className == 'ng-hide') {
                    element.removeClass('ng-hide');

                    var startPoint = element.parent().width();
                    if(scope.direction === 'right') {
                        startPoint = -startPoint;
                    }

                    TweenMax.fromTo(element, 0.5, { left: startPoint }, {left: 0, onComplete: done });
                }
                else {
                    done();
                }
            }
        };
    });
