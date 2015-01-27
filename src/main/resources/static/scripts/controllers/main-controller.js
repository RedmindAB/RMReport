angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', '$timeout', function($scope, $http, $location, $timeout){
    	
    $scope.errorReport={};
    $scope.suites = {};
    $scope.currentTestSuite = {};
    
    $http.get('/api/log/getloglist')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.suites = data;
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });

    var getTestSuite = function(testName){
        var suiteToReturn = {};
        for(var suite in $scope.suites){
        	var suiteCol = $scope.suites;
            if(suiteCol[suite].name === testName){
            	$scope.currentTestSuite = suiteCol[suite];
            }
        }
    };

    $scope.getPanel = function(passed){
    	if(passed)
    		return 'panel panel-success bg-success success';
    	else
    		return 'panel panel-danger bg-danger';
    };
    
    $scope.getBG = function(passed){
    	if(passed)
    		return 'bg-success';
    	else
    		return 'bg-danger';
    };
    
    $scope.getCo = function(passed){
    	if(passed)
    		return '#DFF0D8';
    	else
    		return '#F2DEDE';
    };
    
    $scope.goToTestCases = function(testName){
    	getTestSuite(testName);
    	console.log($scope.currentTestSuite);
    	$location.path('/test-case');
    };
    $scope.labels2 = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    $scope.data2 = [300, 500, 100];
    
}]);
