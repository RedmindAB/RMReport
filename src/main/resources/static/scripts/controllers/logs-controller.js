angular.module('webLog')
    .controller('LogsCtrl',['$scope', '$http', function($scope, $http){
    $scope.message = "Logs Controller";
    $scope.errorReport={};
    $scope.log = {};
    $scope.display ={};
    $http.get('/api/log/getloglist')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.log = data;
    		console.log($scope.log);
    	}
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    $scope.populateLog = function(name, timestamp){
    	$scope.display = getTestSuite(name, timestamp);
    }

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

            };
    return suiteToReturn;
    }

    $scope.getButtonValue = function(timestamp){
    	if (!$scope.display) {
			return "Expand";
		}
    	if ($scope.display.timestamp != timestamp) {
			return "Expand";
		} else {
			return "Collapse";
		}
    }
    
    $scope.getSuiteColor = function(){
    	return '#7f8c8d';
    }    
}]);
