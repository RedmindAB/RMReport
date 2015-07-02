(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('HomeServices', HomeServices);
	
	HomeServices.$inject = ['$http'];
	
	function HomeServices($http){
		
		var service = {
			loadAllSuites: loadAllSuites	
		};
		
		function loadAllSuites(){
	    	return $http.get('/api/suite/getsuites')
		    	.then(loadSuitesComplete)
		    	.catch(loadSuitesFail);
	    	
	    	function loadSuitesComplete(response){
	    		return response.data;
	    	}
	    	
	    	function loadSuitesFail(error){
	    		console.error(error.data);
	    	}
		}
		
		return service;
	}
})();