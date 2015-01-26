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
    $scope.populateLog=function(){
    getTestSuite("CreateLogTest", "(20150121-160711)");
    }

    var getTestSuite = function(suiteName, timeStamp){
            var suiteToReturn = {};

            for(var suite in $scope.log){
                console.log(suite);
                if(suite.name.indexOf(suiteName) > -1 && suite.name.indexOf(timeStamp) > -1){
                $scope.suiteToReturn=suite;
                console.log($scope.suiteToReturn);
                }

            };
    return suiteToReturn;
    }

    }]);
