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
		
		return {
			addPaths: add,
			changePaths: change,
			removePaths: remove
		};
		
		function addPaths(request, errorMessages) {
			return $http.post('/api/admin/reportdir', request)
			.success(function(data, status, headers, config){
			}).error(function(data, status, headers, config){
				errorMessages.push({index: -1, message: "An added path was incorrect"});
			});
		}
		
		function changePaths(request, errorMessages){
			
		    var promises = [];
		    
		    if (request.length > 0) {
		    angular.forEach(request , function(change) {
		        var promise = $http({
		            url   : '/api/admin/reportdir',
		            method: 'PUT',
		            data  : change
		        });
		        promises.push(promise);

		    });
		    }
		    return $q.all(promises);
		}
		
		function removePaths (request){
			
		    var promises = [];

		    angular.forEach(request , function(change) {
		        var promise = $http({
		            url   : '/api/admin/reportdir',
		            method: 'DELETE',
		            data  : change
		        });
		        promises.push(promise);
		    });
		    return $q.all(promises);
		}
	}
})();