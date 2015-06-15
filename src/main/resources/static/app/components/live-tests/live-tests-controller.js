(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('LiveTestsCtrl', LiveTestsCtrl);
	
	LiveTestsCtrl.$inject = ['$scope', '$http','$state', 'LiveTestsServices', 'CurrentSuite'];
			
	function LiveTestsCtrl($scope, $http, $state, LiveTestsServices, CurrentSuite){
		
		var vm = this;
		var requestObj = {};
	
	}
})();