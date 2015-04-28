angular.module('webLog').controller('NavCtrl', ['$scope', '$rootScope','$state', '$location', 'CurrentSuite', 'Charts','Utilities','ScreenshotMaster', 'RestLoader', function($scope, $rootScope,$state, $location, CurrentSuite, Charts, Utilities, ScreenshotMaster, RestLoader){
	
	$scope.CurrentSuite = CurrentSuite;
	$scope.Charts = Charts;
	
	$scope.isNavActive = function(state1, state2){
		if(CurrentSuite.currentSuiteInfo.name !== undefined){
			return true;
		}
		else{	
			return false;
		}
	}
	
	$scope.isActive = function(state){
		if($state.includes(state)){
			return true;
		}
		else{	
			return false;
		}
	}
	
	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){ 
				//$scope.isActive(toState);
				/*console.log(toState.name);
				if(toState.name === "reports.classes"){
					$(".reports").removeClass("disabled");
				}
				else{
					$(".reports").addClass("disabled");
				}*/
	
				Utilities.searchField = '';
				Utilities.resetSorting();
				CurrentSuite.clearChosenClasses();
				CurrentSuite.clearChosenMethods();
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
	
	$scope.goToDashboardView = function(){
		$state.transitionTo("home");
	}
	
	$scope.goToGridView = function(){
		$state.transitionTo("grid");
	}
	
	$scope.goToGraphView = function(){
		if ($state.current.name === 'screenshots.classes') {
			$state.transitionTo('reports.classes');
		} else if ($state.current.name === 'screenshots.methods'){
			$state.transitionTo('reports.methods');
		}
		else{
			$state.transitionTo('reports.classes');
		}
	}
	
	$scope.goToScreenshotView = function(){
		console.log("0");
		ScreenshotMaster.previousView = $state.$current.name;
			if (CurrentSuite.currentSuite.length === 0) {
				console.log("1");
				$state.transitionTo('home');
			} else {
				if (CurrentSuite.currentClass.length === 0 || $state.$current.name === 'reports.classes') {
					console.log("2");
					$state.transitionTo('screenshots.classes');
				} else {
					if (!classExistsInSuite(CurrentSuite.currentClass)) {
						console.log("3");
						$state.transitionTo('screenshots.classes');
					} else {
						if (ScreenshotMaster.currentClass === CurrentSuite.currentClass.id && ScreenshotMaster.currentTimestamp === CurrentSuite.currentTimeStamp) {
							console.log("4");
							$state.transitionTo('screenshots.methods');
						} else {
							console.log("5");
							RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
							$state.transitionTo('screenshots.methods');
						}
					}
				}
			}
	}
	
	function classExistsInSuite(classObj){
		for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
			if (classObj.id === CurrentSuite.currentSuite[i].id) {
				return true;
			}
		}
		return false;
	};
	
	
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
		if (CurrentSuite.currentSuite.length === 0) {
			$location.path("/");
		}
	}
	resetWebApp();
}]);