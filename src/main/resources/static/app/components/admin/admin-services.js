(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('AdminServices', AdminServices);
	
	AdminServices.$inject = ['$http', '$q'];
	
	function AdminServices ($http, $q) {
		
		var add = addPaths;
		var change = changePaths;
		var remove = removePaths;
		var loadRoot = loadRootConfig;
		
		return {
			addPaths: add,
			changePaths: change,
			removePaths: remove,
			loadRootConfig: loadRoot
		};
		
		function addPaths(request, addErrorMessage, addMessage) {
			var promise = $http({
					url   : '/api/admin/reportdir',
		            method: 'POST',
		            data  : request
		        }). error(function(data, status, headers, config){
		        	addErrorMessage(config, "create");
		        });;
			
			return promise;
		}
		
		function loadRootConfig(){
			return $http({
	            url   : '/api/admin/config',
	            method: 'GET'
	        });
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