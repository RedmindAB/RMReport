(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LiveTestsServices', LiveTestsServices);
	
	LiveTestsServices.$inject = ['$http', '$q', 'CurrentSuite', 'Utilities'];
	
	function LiveTestsServices ($http, $q, CurrentSuite, Utilities) {

		return {
		};
	}
})();