(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ScreenshotClassCtrl', ScreenshotClassCtrl);
	
	ScreenshotClassCtrl.$inject = ['CurrentSuite', 'RestLoader','ScreenshotMaster'];
	
	function ScreenshotClassCtrl(CurrentSuite, RestLoader, ScreenshotMaster){
		
		var vm = this;
		
		vm.setCurrentClass = setCurrentClass;
		
		getSuiteSkeletonByTimestamp(CurrentSuite.currentTimeStamp);
		
		console.log(ScreenshotMaster);
		
		function getSuiteSkeletonByTimestamp(timestampVal){
			var timestamp = timestampVal !== undefined ? timestampVal : CurrentSuite.currentSuiteInfo.lastTimeStamp;
			RestLoader.loadTimestamp(timestamp);
		}
		
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
		
		
	}
})();