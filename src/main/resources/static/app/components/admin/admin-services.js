(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('AdminServices', AdminServices);
	
	AdminServices.$inject = ['$http', '$q'];
	
	function AdminServices ($http, $q) {
		
		return {
			addPaths: 		addPaths,
			changePaths:	changePaths,
			loadRootConfig: loadRootConfig,
			removePaths: 	removePaths
		};
		
		function addPaths(request, addErrorMessage) {
			var promise = $http({
					url   : '/api/admin/reportdir',
		            method: 'POST',
		            data  : request
		        }). error(function(data, status, headers, config){
		        	addErrorMessage(data, "create");
		        });
			
			return promise;
		}
		
		function changePaths(request, addErrorMessage){
			
		    var promises = [];
		    
		    if (request.length > 0) {
		    angular.forEach(request , function(change) {
		        var promise = $http({
		            url   : '/api/admin/reportdir',
		            method: 'PUT',
		            data  : change
		        }). error(function(data, status, headers, config){
		        	addErrorMessage(config, "change");
		        });
		        promises.push(promise);

		    });
		    }
		    return $q.all(promises);
		}
		
		function loadRootConfig(){
			return $http({
	            url   : '/api/admin/config',
	            method: 'GET'
	        });
		}
		
		function removePaths (request,addErrorMessage){
			
		    var promises = [];

		    angular.forEach(request , function(change) {
		        var promise = $http({
		            url   : '/api/admin/reportdir',
		            method: 'DELETE',
		            data  : change
		        }). error(function(data, status, headers, config){
		        	addErrorMessage(config, "remove");
		        });
		        promises.push(promise);
		    });
		    return $q.all(promises);
		}
	}
})();