angular.module('webLog').controller('NavCtrl', ['$scope', '$state', 'CurrentSuite', function($scope, $state, CurrentSuite){
	
	$scope.CurrentSuite = CurrentSuite;
	
	$scope.setState = function(newState){
		$state.transitionTo(newState);
		$scope.clearChosen();
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
}]);