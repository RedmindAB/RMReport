(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavBarCtrl', NavBarCtrl);
		
	NavBarCtrl.$inject = ['$state', '$window', 'ChartMaker', 'RestLoader', 'Utilities', 'CurrentSuite', 'DashboardServices', 'SuiteInfoHandler', 'ScreenshotMaster'];
	
	function NavBarCtrl ($state,$window, ChartMaker, RestLoader, Utilities, CurrentSuite, DashboardServices, SuiteInfoHandler, ScreenshotMaster) {
	
		var vm = this;
		
		var count = 0;
		
		vm.CurrentSuite = CurrentSuite;
		
		vm.checkPosition					= checkPosition;
		vm.chooseProject					= chooseProject;
		vm.getScreenshotsFromTimestamp		= getScreenshotsFromTimestamp;
		vm.getSuiteSkeletonByTimestamp		= getSuiteSkeletonByTimestamp;
		vm.goToGraphView					= goToGraphView;
		vm.highlightPoint					= highlightPoint;
		vm.isSmallWindowToggle 				= isSmallWindowToggle;
		vm.isSmallWindowTarget				= isSmallWindowTarget;
		vm.loadDashboardPlatformsAndDevices = loadDashboardPlatformsAndDevices;
		vm.loadMainChart					= loadMainChart;
		vm.reloadOnSuiteChange				= reloadOnSuiteChange;
		vm.changeSuite						= changeSuite;
		vm.goToScreenshotView				= goToScreenshotView;
		vm.isNavActive						= isNavActive;
		vm.isActive							= isActive;
		vm.goToDashboardView				= goToDashboardView;
		vm.goToHomeView 					= goToHomeView;
		vm.goToGridView						= goToGridView;
		
		function isActive(state){
			if($state.includes(state)){
				return true;
			}
			else{	
				return false;
			}
		}
		
		function isNavActive(){
			if(CurrentSuite.currentSuiteInfo.length !== 0){
				return true;
			} else {	
				return false;
			}
		}
		
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
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
		
		function goToGridView(){
			$state.transitionTo("grid");
		}
		
		function goToHomeView(){
			$state.transitionTo("home");
		}
		
		function goToGraphView(){
			if ($state.current.name === 'screenshots.classes') {
				$state.transitionTo('reports.classes');
			} else if ($state.current.name === 'screenshots.methods'){
				$state.transitionTo('reports.methods');
			}
			else{
				$state.transitionTo('reports.classes');
			}
		}
		
		function goToDashboardView(){
			$state.transitionTo("dashboard");
		};
		
		function goToScreenshotView(){
			ScreenshotMaster.previousView = $state.$current.name;
			
			if($state.$current.name === 'reports.methods' || $state.$current.name === 'reports.cases'){
				$state.transitionTo('screenshots.methods');
			} else {
				$state.transitionTo('screenshots.classes');
			}
		}
		
		function getScreenshotsFromTimestamp(){
			if ($state.$current.name === 'screenshots.methods') {
				RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
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
			DashboardServices.getPlatforms(CurrentSuite.currentSuiteInfo.id, "android")
			.then(function(){
				DashboardServices.getClasses(CurrentSuite.currentSuiteInfo.id);	
				DashboardServices.getDevices(CurrentSuite.currentSuiteInfo.id);	
			});
		}
		
	    function loadMainChart(suiteID, name){
	    	ChartMaker.loadMainChart(suiteID, true, name);
	    }
	    
	    function changeSuite(suite){
	    	Utilities.clearData();
			CurrentSuite.currentSuiteInfo = suite;
			CurrentSuite.currentTimestamp = suite.lastTimestamp;
			loadMainChart(CurrentSuite.currentSuiteInfo.id, 'Aggregation');
			reloadOnSuiteChange();
			loadDashboardPlatformsAndDevices();
			checkPosition();
	    }
	    
	    function reloadOnSuiteChange(){
	    	if ($state.includes('reports')) {
	    		if($state.$current.name === 'reports.classes'){
	    			$state.go('.',{notify:true}, {reload:true});
	    		} else {
	    			$state.transitionTo('reports.classes');
	    		}
			} else if ($state.includes('screenshots')) {
	    		if($state.$current.name === 'screenshots.classes'){
	    			$state.go('.',{notify:true}, {reload:true});
	    		} else {
	    			$state.transitionTo('screenshots.classes');
	    		}
			} 
	    }
	}
})();