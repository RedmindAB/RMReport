angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', function($scope, $http, $location){
    	
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

    $scope.getAlert = function(passed){
    	if(passed)
    		return 'alert alert-success';
    	else
    		return 'alert alert-danger';
    };
    
    $scope.goToTestCases = function(testName){
    	getTestSuite(testName);
    	console.log($scope.currentTestSuite);
    	$location.path('/test-case');
    };
}]);
