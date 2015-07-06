(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavBarCtrl', NavBarCtrl);
		
	NavBarCtrl.$inject = ['$state', '$window', 'ChartMaker', 'RestLoader', 'Utilities', 'CurrentSuite', 'DashboardServices', 'SuiteInfoHandler', 'ScreenshotMaster', 'Charts'];
	
	function NavBarCtrl ($state,$window, ChartMaker, RestLoader, Utilities, CurrentSuite, DashboardServices, SuiteInfoHandler, ScreenshotMaster, Charts) {
	
		var vm = this;
		
		var count = 0;
		
		vm.CurrentSuite = CurrentSuite;
		
		
		vm.checkPosition					= checkPosition;
		vm.chooseProject					= chooseProject;
		vm.changeSuite						= changeSuite;
		vm.getSuiteSkeletonByTimestamp		= getSuiteSkeletonByTimestamp;
		vm.goToGraphView					= goToGraphView;
		vm.goToScreenshotView				= goToScreenshotView;
		vm.goToDashboardView				= goToDashboardView;
		vm.goToHomeView 					= goToHomeView;
		vm.goToGridView						= goToGridView;
		vm.highlightPoint					= highlightPoint;
		vm.isSmallWindowToggle 				= isSmallWindowToggle;
		vm.isSmallWindowTarget				= isSmallWindowTarget;
		vm.isNavActive						= isNavActive;
		vm.isActive							= isActive;
		vm.reloadOnSuiteChange				= reloadOnSuiteChange;
		
		
		/*
		 * used on suite change from dropdown.
		 * clears current data and sets new suiteId
		 * and new timestamp, reloads mainchart,
		 * checks if it needs to transition to
		 * a higher state. example if user is looking
		 * at methods and changing suite he will be
		 * transfered to classes again. If allready in
		 * classes it reloads that state. If in home state
		 * it transitions to dashboard.
		 * 
		 * @param {Integer} suite id to change to.
		 */
	    function changeSuite(suite){
	    	Utilities.clearData();
			CurrentSuite.currentSuiteInfo = suite;
			CurrentSuite.currentTimestamp = suite.lastTimestamp;
			getSuiteSkeletonByTimestamp(suite.lastTimestamp);
			if (!$state.includes('reports')) {
				ChartMaker.loadMainChart(suite.id, false);
			}
			reloadOnSuiteChange();
			checkPosition();
	    }
		
	    /*
	     * sets the string for the "choose project" dropdown.
	     * if no project is chosen it sets "choose project"
	     * else it sets current projects name.
	     * 
	     * @param {String} set string or current suite name.
	     */
		function chooseProject(){
	    	if(CurrentSuite.currentSuiteInfo.name === undefined){
	    		return 'Choose Project';
	    	}
	    	else{
	    		return CurrentSuite.currentSuiteInfo.name;
	    	}
	    }
	    
		/*
		 * checks if current state is home and if that
		 * is the case it transitions to dashboard.
		 */
	    function checkPosition(){
	    	if($state.$current.name === "home"){
	    		$state.transitionTo("dashboard");
	    	}
	    }
		
	    //reloads suite skeleton from the passed timestamp.
		function getSuiteSkeletonByTimestamp(timestamp){
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
		
		//transitions to grid
		function goToGridView(){
			$state.transitionTo("grid");
		}
		
		//transitions to home
		function goToHomeView(){
			$state.transitionTo("home");
		}
		
		/*
		 * transitions to graph. if looking at methods
		 * in screenshots it transitions to reports.methods
		 * otherwise it transitions to reports.classes.
		 */
		function goToGraphView(){
			if ($state.current.name === 'screenshots.methods'){
				$state.transitionTo('reports.methods');
			}
			else{
				$state.transitionTo('reports.classes');
			}
		}
		
		//transitions to dashboard
		function goToDashboardView(){
			$state.transitionTo("dashboard");
		}
		
		/*
		 * transitions to screenshots.
		 * if in reports.methods or reports.cases
		 * it transitions to screenshots.methods
		 * otherwise to screenthos.classes
		 */
		function goToScreenshotView(){
			
			if($state.$current.name === 'reports.methods' || $state.$current.name === 'reports.cases'){
				$state.transitionTo('screenshots.methods');
			} else {
				$state.transitionTo('screenshots.classes');
			}
		}
		
		
	    function highlightPoint(timestamp){
	    	Charts.highlightPoint(Utilities.getIndexByTimestamp(timestamp));
	    }
	    
		/*
		 * Checks if passed in state is the current state
		 * viewed.
		 * 
		 * @param {String} name of state to check
		 * @return {Boolean} true if passed in state i current.
		 */
		function isActive(state){
			if($state.includes(state)){
				return true;
			}
			else{	
				return false;
			}
		}
		
		/*
		 * checks if a suite is choosen. used to
		 * make it possible to click on reports,
		 * visualizer and dashboard
		 * 
		 * @param {Boolean} returns true if a suite is chosen
		 */
		function isNavActive(){
			if(CurrentSuite.currentSuiteInfo.length !== 0){
				return true;
			} else {	
				return false;
			}
		}
		
		/*
		 * methods used for the oh so amazing navbar sollution
		 * to check size and collapse
		 */
		function isSmallWindowToggle(){
			return $window.window.innerWidth < 900  ? 'collapse' : '';
		}
		
		/*
		 * no clue why this exists...
		 */
		function isSmallWindowTarget(){
			return $window.width < 900  ? '.navbar-collapse' : '';
		}
		
	    /*
	     * checks the current state and determines if it needs to 
	     * reload or not. if not in absolute parent state it till
	     * transition to absolute parent. If allready in parent
	     * it will reload its current state and wich will
	     * trigger its controllers init()
	     */
	    function reloadOnSuiteChange(){
	    	if ($state.includes('reports')) {
	    		if($state.$current.name === 'reports.classes'){
	    			$state.go('.',{notify:true}, {reload:true});
	    		} else {
	    			$state.go('reports.classes',{notify:true}, {reload:true});
	    		}
			} else if ($state.includes('screenshots')) {
	    		if($state.$current.name === 'screenshots.classes'){
	    			$state.go('.',{notify:true}, {reload:true});
	    		} else {
	    			$state.go('screenshots.classes',{notify:true}, {reload:true});
	    		}
			} else if ($state.includes('dashboard')){
				$state.go('.',{notify:true}, {reload:true});
			}
	    }
	}
})();