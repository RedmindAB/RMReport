(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['$http','$state', '$scope'];
			
	function DashboardCtrl($http, $state, $scope){
		$scope.platforms = ['Android', 'iOS', 'Windows', 'OSX', 'Linux'];
	}
})();