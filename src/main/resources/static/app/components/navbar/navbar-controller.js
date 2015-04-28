angular.module('webLog').controller('NavBarCtrl', ['$scope', '$state', 'ChartMaker', 'RestLoader', 'Utilities', 'CurrentSuite', function($scope, $state, ChartMaker, RestLoader, Utilities, CurrentSuite){
	
	$scope.CurrentSuite = CurrentSuite;
	
    $scope.highlightPoint = function(timestamp){
    	ChartMaker.highlightPoint(timestamp);
    }
    
	$scope.getSuiteSkeletonByTimestamp = function(timestamp){
		RestLoader.loadTimestamp(timestamp);
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
   
    
}])