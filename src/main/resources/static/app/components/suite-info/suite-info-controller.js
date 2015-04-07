angular.module('webLog')
.controller('SuiteInfoCtrl', ['$scope', '$http','CurrentSuite', 'RestLoader', function($scope, $http, CurrentSuite, RestLoader){
	
	$scope.CurrentSuite = CurrentSuite;
	
	$scope.getSuiteSkeleton= function(suite){
		RestLoader.getSuiteSkeleton(suite);
	}
	
	$scope.getSuiteSkeletonByTimestamp = function(timestamp){
		RestLoader.loadTimestamp(timestamp);
	}
	
	$scope.getCases = function(method){
		RestLoader.getCases(method);
	}
	
	//stores chosen class info and clears chosen for other classes
	//when going into a class
	$scope.getMethods = function(classObj){
		CurrentSuite.clearOtherChosen(classObj);
		CurrentSuite.currentClass = classObj;
		CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
		RestLoader.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, classObj, CurrentSuite.currentMethods);
	}
	
//	function getPassFailTotByMethod(timestamp, classObj){
//		var methods = CurrentSuite.currentMethods;
//		for (var i = 0; i < methods.length; i++) {
//			RestLoader.getPassFailByMethod(timestamp, classObj, methods[i]);
//		}
//	}
	
}]);