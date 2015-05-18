angular.module('webLog')
.controller('AdminCtrl',['$scope', '$http','$state', function($scope, $http,$state){

	$scope.config = {};
	$scope.newPath = '';
	
	var requestObj = {};

	loadRootConfig();
	
	$scope.addPath = function(path){
		var request = [path];
		$http.post('/api/admin/reportdir', request)
   		.success(function(data, status, headers, config){
   			$scope.newPath = '';
   			loadRootConfig();
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
		
	}
	
	$scope.removePath = function(index){
		$http.delete('/api/admin/reportdir/'+index)
   		.success(function(data, status, headers, config){
   			loadRootConfig();
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
		
	}
	
	$scope.changePath = function(path, index){
		var request = path;
		$http.put('/api/admin/reportdir/'+index, request)
   		.success(function(data, status, headers, config){
   			loadRootConfig();
   		}).error(function(data, status, headers, config){
	   		console.error(data);
   		});
	}
	
	function loadRootConfig(){
		$http.get('/api/admin/config')
		.success(function(data, status, headers, config){ 
			if(data){
				$scope.config = data;
			};
		}).error(function(data, status, headers, config){
			console.error(data);
		});
	}
}]);