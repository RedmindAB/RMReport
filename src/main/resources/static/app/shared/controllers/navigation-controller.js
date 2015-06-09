(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavCtrl', NavCtrl);
		
			
	NavCtrl.$inject = ['$scope', '$rootScope', '$state', '$location', 'CurrentSuite', 'Charts', 'Utilities', 'ScreenshotMaster', 'RestLoader'];
	
	function NavCtrl ($scope, $rootScope, $state, $location, CurrentSuite, Charts, Utilities, ScreenshotMaster, RestLoader){
		
		$scope.CurrentSuite = CurrentSuite;
		$scope.Charts = Charts;
		
		$scope.isNavActive = function(state1, state2){
			if(CurrentSuite.currentSuiteInfo.name !== undefined){
				return true;
			}
			else{	
				return false;
			}
		};
		
		$scope.isActive = function(state){
			if($state.includes(state)){
				return true;
			}
			else{	
				return false;
			}
		};
		
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
		
		function collapseNavbar(){
			var myEl = angular.element(document.querySelector('#navbar-collapse-2'));
			myEl.removeClass('in'); 
		}
		
		
		
		$scope.getPosition = function(){
			switch ($state.$current.name) {
			case 'reports.classes':
				return CurrentSuite.currentSuiteInfo.name;
			case 'reports.methods':
				return CurrentSuite.currentClass.name;
			case 'reports.cases':
				return CurrentSuite.currentMethod.name;
			default:
				break;
			}
		};
		
		$scope.subNavLinks = function(){
			switch ($state.$current.name) {
			case 'reports.classes':
				$scope.setState('home');
				break;
			case 'reports.methods':
				$scope.setState('reports.classes');
				break;
			case 'reports.cases':
				$scope.setState('reports.methods');
				break;
			default:
				break;
			}
		};
		
		$scope.goToDashboardView = function(){
			$state.transitionTo("dashboard");
		};
		
		$scope.goToGridView = function(){
			$state.transitionTo("grid");
		};
		
		$scope.goToGraphView = function(){
			if ($state.current.name === 'screenshots.classes') {
				$state.transitionTo('reports.classes');
			} else if ($state.current.name === 'screenshots.methods'){
				$state.transitionTo('reports.methods');
			}
			else{
				$state.transitionTo('reports.classes');
			}
		};
		
		$scope.goToHomeView = function(){
			$state.transitionTo("home");
		};
		
		$scope.goToScreenshotView = function(){
			ScreenshotMaster.previousView = $state.$current.name;
				if (CurrentSuite.currentSuite.length === 0) {
					$state.transitionTo('home');
				} else {
					if (CurrentSuite.currentClass.length === 0 || $state.$current.name === 'reports.classes') {
						$state.transitionTo('screenshots.classes');
					} else {
						if (!classExistsInSuite(CurrentSuite.currentClass)) {
							$state.transitionTo('screenshots.classes');
						} else {
							if (ScreenshotMaster.currentClass === CurrentSuite.currentClass.id && ScreenshotMaster.currentTimestamp === CurrentSuite.currentTimeStamp) {
								$state.transitionTo('screenshots.methods');
							} else {
								RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
								$state.transitionTo('screenshots.methods');
							}
						}
					}
				}
		};
		
		function classExistsInSuite(classObj){
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (classObj.id === CurrentSuite.currentSuite[i].id) {
					return true;
				}
			}
			return false;
		}
		
		
		$scope.setState = function(newState){
			$state.transitionTo(newState);
			$scope.clearChosen();
		};
	
		$scope.move =  function(e){
		    var width = angular.element(e.srcElement)[0].offsetWidth;
		};
	    
		$scope.clearChosen = function(){
			//remove classes checkbox
			if (CurrentSuite.currentSuite) {
				for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
					if (CurrentSuite.currentSuite[i].chosen) {
						delete CurrentSuite.currentSuite[i].chosen;
					}
				}
			}
			
			//remove method checkbox
			if (CurrentSuite.currentClass.testcases) {
				for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
					if (CurrentSuite.currentClass.testcases[i].chosen) {
						delete CurrentSuite.currentClass.testcases[i].chosen;
					}
				}
			}
			
			//remove driver checkbox
			if (CurrentSuite.currentDrivers) {
				for (var i = 0; i < CurrentSuite.currentDrivers.length; i++) {
					if (CurrentSuite.currentDrivers[i].chosen) {
						delete CurrentSuite.currentDrivers[i].chosen;
					}
				}
			}
		};
		
		var resetWebApp = function(){
			if (CurrentSuite.currentSuite.length === 0) {
				$location.path("/");
			}
		};
		resetWebApp();
	}
})();