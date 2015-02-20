angular.module('webLog')
    .controller('MainCtrl',['$scope', '$rootScope', '$http','$location', '$timeout','$state', 'CurrentSuite', 'Charts', 'Utilities', function($scope, $rootScope,$http, $location, $timeout, $state, CurrentSuite, Charts, Utilities){
    	
    $scope.Charts = Charts;
    $scope.CurrentSuite = CurrentSuite;
    $scope.Utilities = Utilities;
    $scope.suiteSkeleton = [];	
    $scope.$state = $state;
    $scope.methods = {};
    $scope.errorReport={};
    $scope.chartHomeConfig = {};
    $scope.chartMainConfig = {};
    $scope.allSuites = [];
    $scope.chartVariants = ["Pass/Fail", "Total Pass", "Total Fail", "Run Time"];
    $scope.currentChartVariant = "Pass/Fail";
    
    $scope.newGraphLine = false;
    $scope.breakPoints = ["None", "Browser", "Version", "Device", "Platform"];
    $scope.breakPointChoice = "None";
    $scope.setBreakPoint = function(choice){
    	$scope.breakPointChoice = choice;
    }
    	
    
    $scope.changeChartVariant = function(input){
    	$scope.currentChartVariant = input;
    	
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
			$scope.currentChartVarint = "Pass/Fail"
			passFailChart();
			break;
		}
    }
    
    $scope.mockDriverArray = ["Andriod", "iOS", "OSX", "Windows"];
    $scope.mockOsObject = [];
    $scope.mockOsObject.push({
    	id: 1,
    	os: "Andriod",
    	devices: ["Samsung", "HTC"],
    	versions: ["4.4", "4.5.4"],
    	browsers: ["Chrome", "Fire fox"]
    });
    
    $scope.mockOsObject.push({
    	id: 2,
    	os: "OSX",
    	devices: ["Mac"],
    	versions: ["5", "6"],
    	browsers: ["Chrome", "Fire fox", "Safari"]
    });
    
    $scope.resetFilterField = function(){
    	Utilities.searchField = "";
    }
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
	$scope.getMethods = function(testClass){
		$scope.clearOtherChosen(testClass);
		CurrentSuite.currentClass=testClass;
		CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
	}
	
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
		
		//remove platform checkboc
		clearChosenPlatforms();
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
	
	function clearChosenMethods(chosenMethod) {
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
	}
	
	function clearChosenClasses(chosenClass) {
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					delete CurrentSuite.currentSuite[i].chosen
				}
			}
		}
	}
	
	$scope.getChosen = function(){
		var chosen = {
				os: [],
				devices: [],
				browsers: [],
				classes: [],
				testcases: [],
				platforms:[]
		};
		
		//add version to send
		if (CurrentSuite.currentSpecObject.platforms) {
			var platforms = CurrentSuite.currentSpecObject.platforms;
			for (var i = 0; i < platforms.length; i++) {
				var versions = platforms[i].versions;
				for (var j = 0; j < versions.length; j++) {
					if (versions[j].chosen) {
						chosen.os.push(versions[j].osid);
					}
				}
			}
		}
		
		//add devices to send
		if (CurrentSuite.currentSpecObject.platforms) {
			var platforms = CurrentSuite.currentSpecObject.platforms;
			for (var i = 0; i < platforms.length; i++) {
				var devices= platforms[i].devices;
				for (var j = 0; j < devices.length; j++) {
					if (devices[j].chosen) {
						chosen.devices.push(devices[j].deviceid);
					}
				}
			}
		}
		
		//add platform to send
		if (CurrentSuite.currentSpecObject.platforms) {
			var platforms = CurrentSuite.currentSpecObject.platforms;
			for (var i = 0; i < platforms.length; i++) {
				if (platforms[i].chosen) {
					for (var j = 0; j < platforms[i].versions.length; j++) {
						if (chosen.os.indexOf(platforms[i].versions[j].osid) == -1) {
							chosen.os.push(platforms[i].versions[j].osid);
						}
					}
					chosen.platforms.push(platforms[i].osname);
				}
			}
		}
		
		//add browsers to send
		if (CurrentSuite.currentSpecObject.browsers) {
			var browsers = CurrentSuite.currentSpecObject.browsers;
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
	
	
    // HTTP -----------------------------------------------------------------------------------------------------------
	
    $http.get('/api/suite/getsuites')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.allSuites = data;
    		for (var i = 0; i < $scope.allSuites.length; i++) {
    			$scope.createHomeChartFromID($scope.allSuites[i].id);
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
		console.log(suite.id);
	    $http.get('/api/suite/latestbyid?suiteid=' + suite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		$scope.getSpecsInfo(suite.id);
	    		CurrentSuite.currentSuite = data;
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
	    		if (CurrentSuite.currentClass != undefined) {
	    			CurrentSuite.currentMethods = getMethodsByClassId(CurrentSuite.currentClass.id);
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
	
   $scope.loadMainChart = function(suiteID, newLine) {
    	var requestObject = $scope.getGraphDataObject(suiteID);
    	CurrentSuite.lastRunSize = getResLimit();
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){
    		$scope.createMainChart(data, newLine);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
   };
   
   $scope.loadNewTimeStamp = function(timestamp){
	   $scope.getSuiteSkeletonByTimeStamp(timestamp);
	   CurrentSuite.currentTimeStamp = timestamp;
   }
    
    $scope.createHomeChartFromID = function(id) {
    	var requestObject= [];
    	requestObject.push({
    			name:"hej",
    			suiteid:id,
    			reslimit:50,
    			os: [],
    			devices: [],
    			browsers: [],
    			classes:[],
    			testcases:[]
    });
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){ 
    		$scope.createHomeChart(data, id);
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
    
    $scope.getGraphDataObject = function(suiteID, name){
    	switch ($scope.breakPointChoice) {
		case "None":
			return getAllDataAsOne(suiteID, name);
			break;

		case "Platform":
			return splitDataOnPlatform(suiteID, name);
			break;

		case "Device":
			return splitDataOnDevice(suiteID, name);
			break;

		case "Browser":
			return splitDataOnBrowser(suiteID, name);
			break;
			
		case "Version":
			return splitDataOnVersion(suiteID, name);
			break;

		default:
			break;
		}

    };
    
    function splitDataOnDevice(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	
    	var addedUnknown = false;
    	
    	if (chosen.devices.length === 0) {
    		if (chosen.os.length === 0) {
    			chosen.devices = getAllDevices();
			} else {
				chosen.devices = getAllDevicesByPlatform(chosen);
			}
		}
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
    
    function splitDataOnVersion(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	console.log(chosen);
    	if (chosen.os.length === 0) {
    		if (chosen.devices.length === 0) {
    			console.log("getting all");
    			chosen.os = getAllVersions();
			} else {
				console.log("getting specific");
				chosen.os = getVersionsByDevice(chosen);
			}
		}
    	
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
    		dataRequest.devices = chosen.devices;
    		dataRequest.browsers = chosen.browsers;
    		dataRequest.classes = chosen.classes;
    		dataRequest.testcases = chosen.testcases;
			
    		graphArray.push(dataRequest);
		}
    	return graphArray;
    };
    
    function splitDataOnBrowser(suiteID, name) {
    	var graphArray = [];
    	var chosen = $scope.getChosen();
    	if (chosen.browsers.length === 0) {
			chosen.browsers = getAllBrowsers();
		}
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
    	
    	if (chosen.platforms.length === 0) {
			chosen.platforms = getAllPlatforms();
		}
    	
    	for (var i = 0; i < chosen.platforms.length; i++) {
    		var dataRequest = {};

    		dataRequest.suiteid = suiteID;
    		dataRequest.reslimit = getResLimit();
    		
    		dataRequest.os = getVersionsByPlatform(chosen.platforms[i]);
    		dataRequest.browsers = chosen.browsers;
    		dataRequest.devices = chosen.devices;
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
			dataRequest.name = "";
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
    	console.log(specs);
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
    	console.log(versions);
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
    	console.log(versionsToReturn);
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
    
    function getVersionsByPlatform(platformName){
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
    
    function getAllDevices(){
    	var specs = CurrentSuite.currentSpecObject;
    	var deviceIDs = [];
    	for (var i = 0; i < specs.platforms.length; i++) {
    		for (var j = 0; j < specs.platforms[i].devices.length; j++) {
    			if (deviceIDs.indexOf(specs.platforms[i].devices[j].deviceid) == -1) {
    				deviceIDs.push(specs.platforms[i].devices[j].deviceid);
				}
			}
		}
    	return deviceIDs;
    }
    
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
    
    function getResLimit() {
		var reslimit = Utilities.amountField;
		if (!(isNaN(reslimit)) && !(reslimit === "")) {
			reslimit = parseInt(reslimit);
		} else {
			reslimit = 50;
		}
		return reslimit;
	};
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
    $scope.createMainChart = function(data, newLine){
    	CurrentSuite.currentTimeStampArray = [];
    	for (var index = 0; index < data[0].data.length; index++) {
			CurrentSuite.currentTimeStampArray.push(data[0].data[index].timestamp);
		}
    	
    	if (CurrentSuite.currentTimeStamp === '') {
    		CurrentSuite.currentTimeStamp = data[0].data[data[0].data.length-1].timestamp;
		}
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
				graphDataObj.runTime.push(data[i].data[j].time);
				graphDataObj.totalPass.push(data[i].data[j].pass);
				graphDataObj.totalFail.push(data[i].data[j].fail + data[i].data[j].error);
				graphDataObj.passPercentage.push(Math.round(getPassPercentage(data[i].data[j].pass, data[i].data[j].fail, data[i].data[j].error)));
			}
			graphDataObj.name = graphName;
			graphDataArray.push(graphDataObj);
		}
    	if (newLine) {
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
		$scope.chartMainConfig = Charts.mainChart;
			
			for (var i = 0; i < Charts.data.length; i++) {
		    	Charts.mainChart.series.push({
					data : Charts.data[i].passPercentage,
					name : Charts.data[i].name,
				});
			}
			$scope.changeChartVariant();
    };
    
    function getPassPercentage(pass, fail, error){
    	var totalFail = fail + error;
    	var total = pass + totalFail;
    	var percentage = (pass/total)*100;
    	return percentage;
    }
    
    $scope.createHomeChart = function(data, id) {
    	var timeStamps = [];
    	for (var index = 0; index < data[0].data.length; index++) {
			timeStamps.push(data[0].data[index].timestamp);
		}
    	
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
										CurrentSuite.currentSuiteInfo.id = id;
										$scope.loadNewTimeStamp(this.category);
										$scope.loadMainChart(id);
										$state.transitionTo('reports.classes');
								}
							}
							}
						}
					}
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
					minTickInterval : 5,
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
		$scope.chartHomeConfig[id] = chartHomeConfigObject;
    };
    
    function runTimeChart() {
    	
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "line";
    	chart.series = [];
    	chart.yAxis.max = undefined;
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
    					data : Charts.data[i].runTime,
    					name : Charts.data[i].name
    		});
		}
    	
    	chart.yAxis.title.text = 'Time to run';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Time to run in seconds for the last " + CurrentSuite.lastRunSize + " runs";
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
				type : "column",
				dashStyle : "Solid",
				connectNulls : false
    		});
		}
    	chart.yAxis.title.text = 'Passed test';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Amount of passed tests for the last " + CurrentSuite.lastRunSize+ " runs";
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
				type : "column",
				dashStyle : "Solid",
				connectNulls : false
    		});
		}
    	chart.yAxis.title.text = 'Failed test';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Amount of failed tests for the last " + CurrentSuite.lastRunSize + " runs";
	}
    
    function passFailChart() {
    	var chart = Charts.mainChart;
    	
    	chart.series = [];
    	chart.yAxis.max = 100;
    	for (var i = 0; i < Charts.data.length; i++) {
    		chart.series.push({
    			data : Charts.data[i].passPercentage,
    			name : Charts.data[i].name,
    			type: "line"
    		});
		}
    	chart.yAxis.title.text = 'Percentage';
    	chart.title.text = "Pass/Fail ratio for the last " + CurrentSuite.lastRunSize + " runs";
	}
}]);
