(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ScreenshotClassCtrl', ScreenshotClassCtrl);
	
	ScreenshotClassCtrl.$inject = ['CurrentSuite', 'RestLoader','ScreenshotMaster', 'SuiteInfoHandler'];
	
	function ScreenshotClassCtrl(CurrentSuite, RestLoader, ScreenshotMaster, SuiteInfoHandler){
		
		var vm = this;
		
		vm.setCurrentClass = setCurrentClass;
		getSuiteSkeletonByTimestamp(CurrentSuite.currentTimestamp);
		
		function getSuiteSkeletonByTimestamp(timestamp){
//			var timestamp = timestampVal !== undefined ? timestampVal : CurrentSuite.currentSuiteInfo.lastTimetamp;
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
		
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
		
		
	}
})();