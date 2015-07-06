(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('GlobalCtrl', GlobalCtrl);
		
			
	GlobalCtrl.$inject = ['$scope', '$rootScope', '$location', 'CurrentSuite', 'Utilities','LocalStorage','SuiteHandler','Charts'];
	
	function GlobalCtrl ($scope, $rootScope, $location,CurrentSuite, Utilities, LocalStorage, SuiteHandler, Charts){
		
		$scope.CurrentSuite = CurrentSuite;
		$scope.Utilities = Utilities;
		
		resetWebApp();
		
		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
			if (fromState.name === "screenshots.methods") {
				$rootScope.$broadcast("closeConsoleModal");
				$rootScope.$broadcast("closeScreenshotModal");
			}
			
			$rootScope.$broadcast('saveState');
			
			Utilities.searchField = '';
			Utilities.resetSorting();
			SuiteHandler.clearChosenClasses();
			SuiteHandler.clearChosenMethods();
		});
		
		function resetWebApp(){
//			console.log("hmm");
//			console.log(CurrentSuite);
//			var checkSuite = LocalStorage.getStorage();
//			console.log(checkSuite);
//			if (checkSuite !== undefined && checkSuite !== "null") {
//				$rootScope.$broadcast('restoreState');
//			}
//			console.log(CurrentSuite);
		}
	}
})();