(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('AdminServices', AdminServices);
	
	AdminServices.$inject = ['$http'];
	
	function AdminServices ($http) {
		
		var addPath = addPaths;
		var changePath = changePaths;
		var removePaths = removePaths;
		
		return {
			addPaths: addPaths,
			changePaths: changePaths,
			removePaths: removePaths
		};
		
		function addPaths(request, errorMessages) {
			return $http.post('/api/admin/reportdir', request)
			.success(function(data, status, headers, config){
			}).error(function(data, status, headers, config){
				errorMessages.push({index: -1, message: "An added path was incorrect"});
			});
		};
		
		function changePaths(request, errorMessages){
			return $http.put('/api/admin/reportdir/'+i, request)
	   		.success(function(data, status, headers, config){
	   		}).error(function(data, status, headers, config){
		   		errorMessages.push({index: getIndexFromError(config.url), message: "Path did not exist"});
	   		});
		}
		
		function removePaths(request){
			return $http.delete('/api/admin/reportdir', {data:request})
			.success(function(data, status, headers, config){
			}).error(function(data, status, headers, config){
	   		console.error(data);
			});
		}
	}
})();