angular.module('webLog').controller('NavBarCtrl', ['$scope', 'ChartMaker', 'RestLoader', 'Utilities', function($scope, ChartMaker, RestLoader, Utilities){
	
    $scope.highlightPoint = function(timestamp){
    	ChartMaker.highlightPoint(timestamp);
    }
    
	$scope.getSuiteSkeletonByTimestamp = function(timestamp){
		RestLoader.loadTimestamp(timestamp);
	}
	
	$scope.getSuiteSkeleton= function(suite){
		RestLoader.getSuiteSkeleton(suite);
	}
	
    $scope.loadMainChart = function(suiteID, newLine){
    	ChartMaker.loadMainChart(suiteID,newLine);
    }
	
}])