angular.module('webLog')
    .controller('MainCtrl',['$scope', '$rootScope', '$http','$location', '$timeout','$state', 'CurrentSuite', 'Charts', 'Utilities','ScreenshotMaster', function($scope, $rootScope,$http, $location, $timeout, $state, CurrentSuite, Charts, Utilities,ScreenshotMaster){
    	
    //set value chosen true for graph data
    //colors reload button if changes are made to the query
//    $scope.newContent = function(){
//    	document.getElementById('button_reload').className = 'btn btn-primary';
//    }
    
//    //changes sorting in the class/method/case list
//    $scope.setSorting = function(sorting){
//    	switch (sorting) {
//		case 'pass/fail':
//			Utilities.sorting = ['result', 'name'];
//			Utilities.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
//			break;
//		case 'time':
//			Utilities.sorting = '-time';
//			Utilities.caseSorting = '-timetorun';
//
//		default:
//			break;
//		}
//    }
    
	
	//use by "clear" button, clears every checkbox
//	$scope.clearAllChosen = function(){
//		//remove classes checkbox
//		clearChosenClasses();
//		
//		//remove method checkbox
//		clearChosenMethods();
//		
//		//remove browser checkbox
//		clearChosenBrowsers();
//		
//		//remove device checkbox
//		clearChosenDevices();
//		
//		//remove os checkbox
//		clearChosenOs();
//		
//		//remove platform checkbox
//		clearChosenPlatforms();
//	}
	
//	function clearChosenOs() {
//		if (CurrentSuite.currentSpecObject.platforms) {
//			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
//				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].versions.length; j++) {
//					if (CurrentSuite.currentSpecObject.platforms[i].versions[j].chosen) {
//						delete CurrentSuite.currentSpecObject.platforms[i].versions[j].chosen;
//					}
//				}
//			}
//		}
//	}
//	
//	function clearChosenPlatforms(){
//		if (CurrentSuite.currentSpecObject.platforms) {
//			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
//				if (CurrentSuite.currentSpecObject.platforms[i].chosen) {
//					delete CurrentSuite.currentSpecObject.platforms[i].chosen;
//				}
//			}
//		}
//	}
//	function clearChosenDevices() {
//		if (CurrentSuite.currentSpecObject.platforms) {
//			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
//				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].devices.length; j++) {
//					if (CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen) {
//						delete CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen;
//					}
//				}
//			}
//		}
//	}
//	function clearChosenBrowsers() {
//		if (CurrentSuite.currentSpecObject.browsers) {
//			for (var i = 0; i < CurrentSuite.currentSpecObject.browsers.length; i++) {
//				if (CurrentSuite.currentSpecObject.browsers[i].chosen) {
//					delete CurrentSuite.currentSpecObject.browsers[i].chosen;
//				}
//			}
//		}
//	}
//	function clearChosenMethods() {
//		if (CurrentSuite.currentClass.testcases) {
//			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
//				if (CurrentSuite.currentClass.testcases[i].chosen) {
//					delete CurrentSuite.currentClass.testcases[i].chosen;
//				}
//			}
//		}
//	}
//	function clearChosenClasses() {
//		if (CurrentSuite.currentSuite) {
//			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
//				if (CurrentSuite.currentSuite[i].chosen) {
//					delete CurrentSuite.currentSuite[i].chosen
//				}
//			}
//		}
//	}
	
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

	$scope.setCurrentSuite = function(suite){
		CurrentSuite.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+CurrentSuite.currentSuite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
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
	    	console.error(data);
	    });
	};
	
	
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
    
//    function getAllDevicesByPlatform(chosen){
//    	var specs = CurrentSuite.currentSpecObject;
//    	var deviceIDs = [];
//    	for (var i = 0; i < specs.platforms.length; i++) {
//    		for (var k = 0; k < chosen.platforms.length; k++) {
//				if (chosen.platforms[k] === specs.platforms[i].osname) {
//					for (var j = 0; j < specs.platforms[i].devices.length; j++) {
//						deviceIDs.push(specs.platforms[i].devices[j].deviceid);
//					}
//				}
//			}
//		}
//    	return deviceIDs;
//    }
    
}]);