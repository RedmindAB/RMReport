(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteInfoCtrl', SuiteInfoCtrl);
		
	SuiteInfoCtrl.$inject = ['$http','CurrentSuite', 'RestLoader'];
	
	function SuiteInfoCtrl ($http, CurrentSuite, RestLoader){
		
		var vm = this;
		
		vm.CurrentSuite = CurrentSuite;
		

		vm.getCases 					= getCases;
		vm.getMethods 					= getMethods;
		vm.getSuiteSkeleton 			= getSuiteSkeleton;
		vm.getSuiteSkeletonByTimestamp 	= getSuiteSkeletonByTimestamp;
		
		
		function getCases(method){
			RestLoader.getCases(method);
		}
		
		//stores chosen class info and clears chosen for other classes
		//when going into a class
		function getMethods(classObj){
			CurrentSuite.clearOtherChosen(classObj);
			CurrentSuite.currentClass = classObj;
			CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
			RestLoader.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, classObj, CurrentSuite.currentMethods);
		}
		
		function getSuiteSkeleton(suite){
			RestLoader.getSuiteSkeleton(suite);
		}
		
		function getSuiteSkeletonByTimestamp(timestamp){
			RestLoader.loadTimestamp(timestamp);
		}
	}
})();