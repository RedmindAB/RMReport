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
		
		vm.checkPosition					= checkPosition;
		vm.chooseProject					= chooseProject;
		vm.getScreenshotsFromTimestamp		= getScreenshotsFromTimestamp;
		vm.getSuiteSkeletonByTimestamp		= getSuiteSkeletonByTimestamp;
		vm.getSuiteSkeleton					= getSuiteSkeleton;
		vm.highlightPoint					= highlightPoint;
		vm.isSmallWindowToggle 				= isSmallWindowToggle;
		vm.isSmallWindowTarget				= isSmallWindowTarget;
		vm.loadDashboardPlatformsAndDevices = loadDashboardPlatformsAndDevices;
		vm.loadMainChart					= loadMainChart;
		
	    function chooseProject(){
	    	if(CurrentSuite.currentSuiteInfo.name === undefined){
	    		return 'Choose Project';
	    	}
	    	else{
	    		return CurrentSuite.currentSuiteInfo.name;
	    	}
	    }
	    
	    function checkPosition(){
	    	if($state.$current.name === "home"){
	    		$state.transitionTo("dashboard");
	    	}
	    }
		
		function getSuiteSkeletonByTimestamp(timestamp){
			RestLoader.loadTimestamp(timestamp);
		}
		
		function getScreenshotsFromTimestamp(){
			if ($state.$current.name === 'screenshots.methods') {
				RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
			}
		}
		
		function getSuiteSkeleton(suite){
			RestLoader.getSuiteSkeleton(suite);
			if ($state.includes("reports")) {
				$state.transitionTo("reports.classes");
			} else if($state.includes("screenshots")){
				$state.transitionTo("screenshots.classes");
			} else {
			}
		}
	    
	    function highlightPoint(timestamp){
	    	ChartMaker.highlightPoint(Utilities.getIndexByTimestamp(timestamp));
	    }
		
		function isSmallWindowToggle(){
			return $window.window.innerWidth < 900  ? 'collapse' : '';
		}
		
		function isSmallWindowTarget(){
			return $window.width < 900  ? '.navbar-collapse' : '';
		}
	    
		function loadDashboardPlatformsAndDevices(){
			DashboardServices.getPlatforms(CurrentSuite.currentSuiteInfo.id, "android").then(function(){
				DashboardServices.getDevices(CurrentSuite.currentSuiteInfo.id).then(function(){
					DashboardServices.getClasses(CurrentSuite.currentSuiteInfo.id);	
				});
			});
		}
		
	    function loadMainChart(suiteID, newLine, name){
	    	ChartMaker.loadMainChart(suiteID,newLine, name);
	    }
	}
})();