(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl)
	    	
	DashboardCtrl.$inject = ['$scope', '$http', '$state', 'CurrentSuite','RestLoader', 'ChartMaker', 'Charts'];
	
	function DashboardCtrl($scope, $http, $state, CurrentSuite, RestLoader, ChartMaker, Charts){
		
		$scope.Charts = Charts;
		$scope.CurrentSuite = CurrentSuite;
		
	    $scope.homeChartLoaded = function(suite){
	    	return Charts.chartHomeConfig[suite.id] 
	    	!== undefined && Charts.chartHomeConfig[suite.id].loading 
	    	=== false && suite.lastTimeStamp 
	    	!== undefined;
	    }
		
	    $scope.getSuiteSkeleton = function(suite){
	    	RestLoader.getSuiteSkeleton(suite);
	    }
	    
	    function createHomeChartFromID(suite){
	    	ChartMaker.loadHomeChart(suite, Charts.chartHomeConfig);
	    }
	    
	    $http.get('/api/suite/getsuites')
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.allSuites = data;
	    		for (var i = 0; i < CurrentSuite.allSuites.length; i++) {
	    			createHomeChartFromID(CurrentSuite.allSuites[i]);
				}
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	};
})();