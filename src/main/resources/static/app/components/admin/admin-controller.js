angular.module('webLog')
.controller('AdminCtrl',['$scope', '$http','$state', function($scope, $http,$state){

	$scope.config = {};
	$scope.newPath = '';
	
	$scope.addPath = function(path){
		console.log("adding path");
	}
	
	$scope.removePath = function(index){
		console.log("removing path: " + index);
	}
	
	$scope.changePath = function(path, index){
		console.log("changing path: " + index);
	}
	
    $http.get('/api/admin/config')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.config = data;
    		console.log($scope.config);
    	};
    }).error(function(data, status, headers, config){
    	console.error(data);
    });
	
}]);