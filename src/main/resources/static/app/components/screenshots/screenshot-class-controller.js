(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ScreenshotClassCtrl', ScreenshotClassCtrl);
	
	ScreenshotClassCtrl.$inject = ['CurrentSuite', 'RestLoader'];
	
	function ScreenshotClassCtrl(CurrentSuite, RestLoader){
		
		var vm = this;
		
		vm.setCurrentClass = setCurrentClass;
		
		getSuiteSkeletonByTimestamp(CurrentSuite.currentTimeStamp);
		
		function getSuiteSkeletonByTimestamp(timestampVal){
			var timestamp = timestampVal !== undefined ? timestampVal : CurrentSuite.currentSuiteInfo.lastTimeStamp;
			RestLoader.loadTimestamp(timestamp);
		}
		
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
	}
})();