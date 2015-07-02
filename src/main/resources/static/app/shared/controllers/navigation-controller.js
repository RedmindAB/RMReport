(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavCtrl', NavCtrl);
		
			
	NavCtrl.$inject = ['$state', 'CurrentSuite','SuiteHandler'];
	
	function NavCtrl ($state, CurrentSuite, SuiteHandler){
		
		var vm = this;

		
		vm.getPosition = getPosition;
		vm.subNavLinks = subNavLinks;
		vm.setState	= setState;
		
		
		function collapseNavbar(){
			var myEl = angular.element(document.querySelector('#navbar-collapse-2'));
			myEl.removeClass('in'); 
		}
		
		function getPosition(){
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
		}
		
		function subNavLinks(){
			switch ($state.$current.name) {
			case 'reports.classes':
				setState('home');
				break;
			case 'reports.methods':
				setState('reports.classes');
				break;
			case 'reports.cases':
				setState('reports.methods');
				break;
			default:
				break;
			}
		}
		
		function setState(newState){
			SuiteHandler.clearChosenClasses();
			SuiteHandler.clearChosenMethods();
			$state.transitionTo(newState);
		}
	}
})();