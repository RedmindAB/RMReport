(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('OvertimeCtrl', OvertimeCtrl);
	
	OvertimeCtrl.$inject = ['$http','$state', '$scope'];
			
	function OvertimeCtrl($http, $state, $scope){
		$scope.platforms = ['Android', 'iOS', 'Windows', 'OSX', 'Linux'];
	}
})();