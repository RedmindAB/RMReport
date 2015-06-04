(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['$http','$state', '$scope'];
			
	function DashboardCtrl($http, $state, $scope){
		
		var vm = this;
		var requestObj = {};
		
		vm.random = random;
		
		vm.platforms = ['Android', 'iOS', 'Windows', 'OSX', 'Linux'];
		
		function random(){
			
		}
	}
})();