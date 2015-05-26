(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteInfoCtrl', SuiteInfoCtrl);
		
	SuiteInfoCtrl.$inject = ['$scope', '$http','CurrentSuite', 'RestLoader'];
	
	function SuiteInfoCtrl ($scope, $http, CurrentSuite, RestLoader){
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
	};
})();