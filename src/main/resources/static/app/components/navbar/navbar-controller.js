(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('NavBarCtrl', NavBarCtrl);
		
	NavBarCtrl.$inject = ['$scope', '$state', '$window', 'ChartMaker', 'RestLoader', 'Utilities', 'CurrentSuite'];
	
	function NavBarCtrl ($scope, $state,$window, ChartMaker, RestLoader, Utilities, CurrentSuite) {
		
		$scope.CurrentSuite = CurrentSuite;
		
		$scope.isSmallWindowToggle = function(){
			return $window.window.innerWidth < 900  ? 'collapse' : '';
		}
		
		$scope.isSmallWindowTarget = function(){
			return $window.width < 900  ? '.navbar-collapse' : '';
		}
		
	    $scope.highlightPoint = function(timestamp){
	    	ChartMaker.highlightPoint(timestamp);
	    }
	    
		$scope.getSuiteSkeletonByTimestamp = function(timestamp){
			RestLoader.loadTimestamp(timestamp);
		}
		
		$scope.getScreenshotsFromTimestamp = function(){
			if ($state.$current.name === 'screenshots.methods') {
				RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass)
			}
		}
		
		$scope.getSuiteSkeleton= function(suite){
			RestLoader.getSuiteSkeleton(suite);
			if ($state.includes("reports")) {
				$state.transitionTo("reports.classes");
			} else if($state.includes("screenshots")){
				$state.transitionTo("screenshots.classes");
			} else {
			}
		}
		
	    $scope.loadMainChart = function(suiteID, newLine, name){
	    	ChartMaker.loadMainChart(suiteID,newLine, name);
	    }
		
	    $scope.chooseProject = function(){
	    	if(CurrentSuite.currentSuiteInfo.name === undefined){
	    		return 'Choose Project';
	    	}
	    	else{
	    		return CurrentSuite.currentSuiteInfo.name;
	    	}
	    }
	};
})();