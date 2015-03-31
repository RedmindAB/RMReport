angular.module('webLog')
.controller('DashboardCtrl',['$scope', '$http','$state', 'CurrentSuite','RestLoader', 'ChartMaker', 'Charts', function($scope, $http,$state, CurrentSuite, RestLoader, ChartMaker, Charts){
    	
	$scope.Charts = Charts;
	$scope.allSuites = [];
	
    $scope.homeChartLoaded = function(suite){
    	return Charts.chartHomeConfig[suite.id] !== undefined && Charts.chartHomeConfig[suite.id].loading === false;
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
    		$scope.allSuites = data;
    		for (var i = 0; i < $scope.allSuites.length; i++) {
    			createHomeChartFromID($scope.allSuites[i]);
			}
    	};
    }).error(function(data, status, headers, config){
    	console.error(data);
    });
}]);