angular.module('webLog').controller('NavCtrl', ['$scope', '$state', 'CurrentSuite', 'Charts', function($scope, $state, CurrentSuite, Charts){
	
	$scope.CurrentSuite = CurrentSuite;
	$scope.Charts = Charts;
	
	
	$scope.setState = function(newState){
		$state.transitionTo(newState);
		$scope.clearChosen();
	}

	$scope.move =  function(e){
	    var width = angular.element(e.srcElement)[0].offsetWidth;

	    console.log(e.offsetX ,"/", width);
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