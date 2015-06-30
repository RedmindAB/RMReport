(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('HomeCtrl', HomeCtrl);
	    	
	HomeCtrl.$inject = ['$http', 'CurrentSuite','RestLoader', 'ChartMaker', 'Charts'];
	
	function HomeCtrl($http, CurrentSuite, RestLoader, ChartMaker, Charts){
		
		var vm = this;
		
		vm.Charts = Charts;
		vm.CurrentSuite = CurrentSuite;
		
		vm.getSuiteSkeleton = getSuiteSkeleton;
		vm.homeChartLoaded 	= homeChartLoaded;
		vm.setZIndex 		= setZIndex;
		
		// Generates charts on each instantiation of this controller.
		loadAll();
		
		/*
		 * Sends a RESTful call via RestLoader to get
		 * all classes and methods run in the latest timestamp
		 * for given suite and store in CurrentSuite.currentSuite
		 * 
		 * @param {Object} Suite object to load from
		 */
	    function getSuiteSkeleton(suite){
	    	RestLoader.getSuiteSkeleton(suite);
	    }
		
	    /*
	     * Checks if the chart for suite in question is fully loaded
	     * and necessary parameters are set.
	     * 
	     * @param {Object} Suite to check
	     */
	    function homeChartLoaded(suite){
	    	return Charts.chartHomeConfig[suite.id] !== undefined && 
	    	Charts.chartHomeConfig[suite.id].loading === false && 
	    	suite.lastTimestamp !== undefined;
	    }
		
	    /*
	     * Sets a higher z-index the lower the index passed in
	     * in relation to the size of CurrentSuite.allSuites
	     * array. Used to make sure tool tip of charts
	     * renders on top of charts beneath it.
	     * 
	     * @param {Integer} index to set z-index for
	     * @return {Integer} z-index to set
	     */
	    function setZIndex(index){
	    	var length = CurrentSuite.allSuites.length;
	    	var reverse = index-length;
	    	var zIndex = Math.abs(reverse);
	    	
	    	return zIndex;
	    }
	    
	    /*
	     * Sets up the homeChartConfig that will be used by
	     * Highcharts when rendering the chart for given suite
	     * 
	     * @param {Object} suite to load chart for
	     */
	    function createHomeChartFromID(suite){
	    	ChartMaker.loadHomeChart(suite, Charts.chartHomeConfig);
	    }
	    
	    /*
	     * Assigns a blueprint chart set to loading for
	     * given suites.
	     * 
	     * @param {Array} Array of suites to generate charts for 
	     */
	    function setUpBlueprints(suites){
	    	for (var i = 0, x = suites.length; i < x; i++){
	    		Charts.chartHomeConfig[suites[i].id] = Charts.homeChartBlueprint;
	    	}
	   	}
	    
	    /*
	     * RESTful call to get all suites available to store
	     * in CurrentSuite.allSuites array. Sets up blueprints
	     * and finally generates proper chart data.
	     */
	    function loadAll(){
	    	$http.get('/api/suite/getsuites')
	    	.success(function(data, status, headers, config){ 
	    		if(data){
	    			CurrentSuite.allSuites = data;
	    			setUpBlueprints(CurrentSuite.allSuites);
	    			for (var i = 0; i < CurrentSuite.allSuites.length; i++) {
	    				createHomeChartFromID(CurrentSuite.allSuites[i]);
	    			}
	    		}
	    	}).error(function(data, status, headers, config){
	    		console.error(data);
	    	});
	    }
	    
	}
})();