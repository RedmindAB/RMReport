(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteClassCtrl', SuiteClassCtrl);
		
	SuiteClassCtrl.$inject = ['CurrentSuite', 'RestLoader'];
	
	function SuiteClassCtrl (CurrentSuite, RestLoader){
		
		var vm = this;
		
		vm.CurrentSuite = CurrentSuite;
		

		vm.getSuiteSkeletonByTimestamp 	= getSuiteSkeletonByTimestamp;
		vm.setCurrentClass 				= setCurrentClass;
		
		getSuiteSkeletonByTimestamp(CurrentSuite.currentTimeStamp);
		
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
		
		function getSuiteSkeletonByTimestamp(timestampVal){
			var timestamp = timestampVal !== undefined ? timestampVal : CurrentSuite.currentSuiteInfo.lastTimeStamp;
			RestLoader.loadTimestamp(timestamp);
		}
	}
})();