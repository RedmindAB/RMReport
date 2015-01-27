angular.module('webLog')
    .controller('TestCtrl',['$scope', '$http','$location', function($scope, $http, $location){
    	
    $scope.errorReport={};
    $scope.log = {};
    $scope.display ={};
    
    $http.get('/api/log/getloglist')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.log = data;
    		console.log($scope.log);
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    $scope.populateLog = function(name, timestamp){
    	$scope.display = getTestSuite(name, timestamp);
    };

    var getTestSuite = function(suiteName, timeStamp){
        var suiteToReturn = {};
        for(var suite in $scope.log){
        	var log = $scope.log;
            if(log[suite].name.indexOf(suiteName) > -1 && log[suite].name.indexOf(timeStamp) > -1){
            	if ($scope.display === log[suite]) {
					display = {};
					return;
				}
            suiteToReturn=log[suite];
            }

        }
        return suiteToReturn;
    };

    $scope.getButtonValue = function(timestamp){
    	if (!$scope.display) {
			return "Expand";
		}
    	if ($scope.display.timestamp != timestamp) {
			return "Expand";
		} else {
			return "Collapse";
		}
    };
    
    $scope.getAlert = function(passed){
    	if(passed)
    		return 'alert alert-success';
    	else
    		return 'alert alert-danger';
    };
    
    $scope.goToTestCases = function(){
    	
    	$location.path('/test-case');
    	
    };
    
}]);
