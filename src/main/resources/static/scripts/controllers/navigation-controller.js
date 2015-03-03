angular.module('webLog').controller('NavCtrl', ['$scope', '$state', 'CurrentSuite', 'Charts','Utilities', function($scope, $state, CurrentSuite, Charts, Utilities){
	
	$scope.CurrentSuite = CurrentSuite;
	$scope.Charts = Charts;
	
	$scope.$watch('$state.$current.name', function() {
		Utilities.searchField = '';
		Utilities.sorting = ['result', 'name'];
		Utilities.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
	});
	
	$scope.getPosition = function(){
		switch ($state.$current.name) {
		case 'reports.classes':
			return CurrentSuite.currentSuiteInfo.name;
			reak;
		case 'reports.methods':
			return CurrentSuite.currentClass.name;
			break;
		case 'reports.cases':
			return CurrentSuite.currentMethod.name;
			break;
		default:
			break;
		}
	}
	
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
	}
	
	$scope.setState = function(newState){
		$state.transitionTo(newState);
		$scope.clearChosen();
	}

	$scope.move =  function(e){
	    var width = angular.element(e.srcElement)[0].offsetWidth;
	}
    
	$scope.clearChosen = function(){
		//remove classes checkbox
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					delete CurrentSuite.currentSuite[i].chosen
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
					delete CurrentSuite.currentDrivers[i].chosen
				}
			}
		}
	}   
	var resetWebApp = function(){
		if (CurrentSuite.currentSuiteInfo.length === 0) {
			if ($state.$current.name !== 'home') {
				$scope.setState('home');
			}
		}
	}
	resetWebApp();
}]);