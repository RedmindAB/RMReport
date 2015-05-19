angular.module('webLog')
.controller('AdminCtrl',['$scope', '$http','$state', function($scope, $http,$state){

	$scope.config = {};
	$scope.configCompare = {}
	$scope.newPath = '';
	
	var requestObj = {};

	loadRootConfig();
	
	$scope.addPath = function(path){
		$scope.configCompare.reportPaths.push(path);
		$scope.newPath = '';
	}
	
	$scope.removePath = function(index){
		if ($scope.isNewPath(index)) {
			$scope.configCompare.reportPaths.splice(index,1);
		} else {
			if ($scope.configCompare.removeList.indexOf(index) === -1) {
				$scope.configCompare.removeList.push(index);
			} else {
				var i = $scope.configCompare.removeList.indexOf(index);
				$scope.configCompare.removeList.splice(i, 1);
			}
		}
	}
	
	function loadRootConfig(){
		$http.get('/api/admin/config')
		.success(function(data, status, headers, config){ 
			if(data){
				$scope.config = data;
				angular.copy($scope.config,$scope.configCompare);
				$scope.configCompare.removeList = [];
			};
		}).error(function(data, status, headers, config){
			console.error(data);
		});
	}
	
	$scope.saveChanges = function(){
		addPaths();
		changePaths();
		removePaths();
		loadRootConfig();
	}
	
	function changePaths(){
		for (var i = 0; i < $scope.configCompare.reportPaths.length; i++) {
			if ($scope.isPathChanged(i)) {
				console.log("changing stuff");
				var request = $scope.configCompare.reportPaths[i];
				$http.put('/api/admin/reportdir/'+i, request)
		   		.success(function(data, status, headers, config){
		   		}).error(function(data, status, headers, config){
			   		console.error(data);
		   		});
			}
		}
	}
	
	function addPaths(){
		var request = [];
		for (var i = 0; i < $scope.configCompare.reportPaths.length; i++) {
			if ($scope.isNewPath(i)) {
				request.push($scope.configCompare.reportPaths[i]);
				$scope.configCompare.reportPaths.splice(i,1);
				i--;
			}
		}
		if (request.length > 0) {
			$http.post('/api/admin/reportdir', request)
			.success(function(data, status, headers, config){
			}).error(function(data, status, headers, config){
	   		console.error(data);
			});
		}
		for (var i = 0; i < request.length; i++) {
			
		}
	}
	
	function removePaths(){
		var request = $scope.configCompare.removeList;
		console.log(request);
		$http.delete('/api/admin/reportdir', request)
		.success(function(data, status, headers, config){
		}).error(function(data, status, headers, config){
   		console.error(data);
		});
	}
	
	$scope.isToBeRemoved = function(index){
		return $scope.configCompare.removeList.indexOf(index) != -1;
	}
	
	$scope.isPathChanged = function(index){
		return $scope.configCompare.reportPaths[index] !== $scope.config.reportPaths[index] || index >= $scope.config.reportPaths.length;
	}
	
	$scope.isNewPath = function(index){
		return index >= $scope.config.reportPaths.length;
	}
}]);