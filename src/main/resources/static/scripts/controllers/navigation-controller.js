angular.module('webLog').controller('NavCtrl', ['$scope', '$state', 'CurrentSuite', 'Charts', function($scope, $state, CurrentSuite, Charts){
	
	$scope.CurrentSuite = CurrentSuite;
	$scope.Charts = Charts;
	
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
		console.log(CurrentSuite.currentSuiteInfo);
		if (CurrentSuite.currentSuiteInfo.length === 0) {
			if ($state.$current.name !== 'home') {
				$scope.setState('home');
			}
		}
	}
	resetWebApp();
}]);