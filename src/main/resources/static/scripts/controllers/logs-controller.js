angular.module('webLog')
    .controller('LogsCtrl',['$scope', '$http', function($scope, $http){
    $scope.message = "Logs Controller";
    $scope.errorReport={};
    $scope.log = {};
    $http.get('/api/log/getloglist')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.log = data;
    		console.log(data);
    	}
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    }]);