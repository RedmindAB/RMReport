(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('HomeCtrl', HomeCtrl);
	    	
	HomeCtrl.$inject = ['$http', '$state', 'CurrentSuite','RestLoader', 'ChartMaker', 'Charts'];
	
	function HomeCtrl($http, $state, CurrentSuite, RestLoader, ChartMaker, Charts){
		
		var vm = this;
		
		vm.Charts = Charts;
		vm.CurrentSuite = CurrentSuite;
		
		vm.getSuiteSkeleton = getSuiteSkeleton;
		vm.homeChartLoaded 	= homeChartLoaded;
		vm.setZIndex 		= setZIndex;
		
		loadAll();
		
	    function getSuiteSkeleton(suite){
	    	RestLoader.getSuiteSkeleton(suite);
	    }
		
	    function homeChartLoaded(suite){
	    	return Charts.chartHomeConfig[suite.id] !== undefined && 
	    	Charts.chartHomeConfig[suite.id].loading === false && 
	    	suite.lastTimeStamp !== undefined;
	    }
		
	    function setZIndex(index){
	    	var length = CurrentSuite.allSuites.length;
	    	var reverse = index-length;
	    	var zIndex = Math.abs(reverse);
	    	
	    	return zIndex;
	    }
	    
	    function createHomeChartFromID(suite){
	    	ChartMaker.loadHomeChart(suite, Charts.chartHomeConfig);
	    }
	    
	    function setUpBlueprints(suites){
	    	for (var i = 0, x = suites.length; i < x; i++){
	    		Charts.chartHomeConfig[suites[i].id] = Charts.homeChartBlueprint;
	    	}
	   	}
	    
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