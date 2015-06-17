(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('GlobalCtrl', GlobalCtrl);
		
			
	GlobalCtrl.$inject = ['$scope', '$rootScope', '$location', 'CurrentSuite', 'Utilities'];
	
	function GlobalCtrl ($scope, $rootScope, $location,CurrentSuite, Utilities){
		
		$scope.CurrentSuite = CurrentSuite;
		$scope.Utilities = Utilities;
		
		this.resetWebApp = resetWebApp;
		
		this.resetWebApp();
		
		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
			if (fromState.name === "screenshots.methods") {
				$rootScope.$broadcast("closeConsoleModal");
				$rootScope.$broadcast("closeScreenshotModal");
			}
			
			Utilities.searchField = '';
			Utilities.resetSorting();
			CurrentSuite.clearChosenClasses();
			CurrentSuite.clearChosenMethods();
		});
		
		function resetWebApp(){
			if (CurrentSuite.currentSuite.length === 0) {
				$location.path("/");
			}
		};
	}
})();