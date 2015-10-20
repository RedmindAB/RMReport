(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ParametersCtrl', ParametersCtrl);
	
	ParametersCtrl.$inject = ["$scope","$rootScope","$http",'CurrentSuite', 'Utilities', 'ParameterService'];
	
	function ParametersCtrl($scope, $rootScope, $http, CurrentSuite, Utilities, ParameterService) {
		var vm = this;
		vm.CurrentSuite = CurrentSuite;
		vm.getTimestamp = getTimestamp;
		vm.getParameters = getParameters;
		vm.currentParameters = getParameters();
		
		$scope.$watch(function(){return CurrentSuite.currentTimestamp}, function(){
			getParameters();
		});
		
		function getTimestamp(){
			var timestamp = CurrentSuite.currentTimestamp;
			return Utilities.makeTimestampReadable(timestamp);
		}
		
		function getParameters(){
			ParameterService.getParameters(CurrentSuite.currentSuiteInfo.id, CurrentSuite.currentTimestamp).then(function(data) {
				vm.currentParameters = data;
			});
		}
		
	}
	
})();