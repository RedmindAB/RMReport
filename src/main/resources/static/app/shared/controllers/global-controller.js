(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('GlobalCtrl', GlobalCtrl);
	
	GlobalCtrl.$inject = ['$scope','CurrentSuite', 'Utilities'];
	
	function GlobalCtrl($scope, CurrentSuite, Utilities){
		
		$scope.CurrentSuite = CurrentSuite;
		$scope.Utilities = Utilities;
		
	}
})();