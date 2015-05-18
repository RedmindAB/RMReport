angular.module('webLog')
.controller('AdminCtrl',['$scope', '$http','$state', function($scope, $http,$state){

	$scope.config = {};
	$scope.newPath = '';
	
	var requestObj = {};

	loadRootConfig();
	
	$scope.addPath = function(path){
		console.log("adding path");
		
		var request = [path];
		
		$http.post('/api/admin/reportdir', request)
   		.success(function(data, status, headers, config){
   			console.log("path added");
   			$scope.newPath = '';
   			loadRootConfig();
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
		loadRootConfig();
	}
	
	$scope.removePath = function(index){
		console.log("removing path: " + index);
		
		$http.delete('/api/admin/reportdir/'+index, request)
   		.success(function(data, status, headers, config){
   			console.log("path changed");
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
		
	}
	
	$scope.changePath = function(path, index){
		console.log("changing path: " + path);
		
		var request = path;
		
		$http.put('/api/admin/reportdir/'+index, request)
   		.success(function(data, status, headers, config){
   			console.log("path changed");
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
	}
	
	function loadRootConfig(){
		$http.get('/api/admin/config')
		.success(function(data, status, headers, config){ 
			if(data){
				$scope.config = data;
				console.log($scope.config);
			};
		}).error(function(data, status, headers, config){
			console.error(data);
		});
	}
	
}]);