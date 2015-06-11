(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavBarCtrl', NavBarCtrl);
		
	NavBarCtrl.$inject = ['$state', '$window', 'ChartMaker', 'RestLoader', 'Utilities', 'CurrentSuite', 'DashboardServices'];
	
	function NavBarCtrl ($state,$window, ChartMaker, RestLoader, Utilities, CurrentSuite, DashboardServices) {
	
		var vm = this;
		var count = 0;
		vm.CurrentSuite = CurrentSuite;
		
		
		vm.loadDashboardPlatformsAndDevices = function(){
			DashboardServices.getPlatforms(CurrentSuite.currentSuiteInfo.id, "android").then(function(){
				DashboardServices.getDevices(CurrentSuite.currentSuiteInfo.id).then(function(){
					DashboardServices.getClasses(CurrentSuite.currentSuiteInfo.id);	
				});
			});
			vm.loadDashboardDeviceRange();
		};
		
		vm.loadDashboardDeviceRange = function(){
			DashboardServices.getDeviceRange(CurrentSuite.currentSuiteInfo.id, CurrentSuite.currentSuiteInfo.lastTimeStamp);
		};
		
		vm.isSmallWindowToggle = function(){
			return $window.window.innerWidth < 900  ? 'collapse' : '';
		};
		
		vm.isSmallWindowTarget = function(){
			return $window.width < 900  ? '.navbar-collapse' : '';
		};
		
	    vm.highlightPoint = function(timestamp){
	    	ChartMaker.highlightPoint(Utilities.getIndexByTimestamp(timestamp));
	    };
	    
		vm.getSuiteSkeletonByTimestamp = function(timestamp){
			RestLoader.loadTimestamp(timestamp);
		};
		
		vm.getScreenshotsFromTimestamp = function(){
			if ($state.$current.name === 'screenshots.methods') {
				RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
			}
		};
		
		vm.getSuiteSkeleton= function(suite){
			RestLoader.getSuiteSkeleton(suite);
			if ($state.includes("reports")) {
				$state.transitionTo("reports.classes");
			} else if($state.includes("screenshots")){
				$state.transitionTo("screenshots.classes");
			} else {
			}
		};
		
	    vm.loadMainChart = function(suiteID, newLine, name){
	    	ChartMaker.loadMainChart(suiteID,newLine, name);
	    };
		
	    vm.chooseProject = function(){
	    	if(CurrentSuite.currentSuiteInfo.name === undefined){
	    		return 'Choose Project';
	    	}
	    	else{
	    		return CurrentSuite.currentSuiteInfo.name;
	    	}
	    };
	    
	    vm.checkPosition = function(){
	    	if($state.$current.name === "home"){
	    		$state.transitionTo("dashboard");
	    	}
	    }
	}
})();