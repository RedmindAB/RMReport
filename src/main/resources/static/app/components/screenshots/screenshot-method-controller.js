(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ScreenshotMethodCtrl', ScreenshotMethodCtrl);
	
	ScreenshotMethodCtrl.$inject = ['$scope','ScreenshotMaster','RestLoader', 'CurrentSuite', 'ScreenshotServices', 'SuiteInfoHandler'];
	
	function ScreenshotMethodCtrl($scope, ScreenshotMaster, RestLoader, CurrentSuite, ScreenshotServices, SuiteInfoHandler){
		
		var vm = this;
		
		vm.ScreenshotMaster = ScreenshotMaster;
		vm.screenshotModalShown = false;
		vm.consoleModalShown = false;
		vm.caseArraySize = [];
		
		
		vm.noScreenshotsExists 			= noScreenshotsExists;
		vm.makeArray 					= makeArray;
		vm.containsScreenshots 			= containsScreenshots;
		vm.getMethodContentWidth 		= getMethodContentWidth;
		vm.toggleScreenshotModal 		= toggleScreenshotModal;
		vm.toggleConsoleModal 			= toggleConsoleModal;
		vm.getScreenshotsFromTimestamp 	= getScreenshotsFromTimestamp;
		vm.getCommentFromFileName 		= getCommentFromFileName;
		
		
		init();
		
		
		$scope.$on("closeScreenshotModal", function() {
			vm.screenshotModalShown = false;
		});
		
		$scope.$on("closeConsoleModal", function() {
			vm.consoleModalShown = false;
		});
		
		function init(){
			ScreenshotServices.loadScreenshotsFromClass(CurrentSuite.currentClass.id, CurrentSuite.currentTimestamp)
			.then(function(data){
	    		ScreenshotMaster.data = data;
	    		SuiteInfoHandler.setPassFailAllMethods(CurrentSuite.currentTimestamp, CurrentSuite.currentClass, ScreenshotMaster.data);
	    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id;
	    		ScreenshotMaster.currentTimestamp = CurrentSuite.currentTimestamp;
	    		setCaseSizeByMethod();
			});
		}
		
		function noScreenshotsExists(){
			return ScreenshotMaster.data.screenshotsExists !== undefined && ScreenshotMaster.data.screenshotsExists === false;
		}

		function makeArray(number) {
			return new Array(number);
		}
		
		function containsScreenshots(method){
			return method.screenshotLength > 0;
		}
		
		function getMethodContentWidth(method){
			return (method.testcases.length * 232)+10;
		}
		
		function toggleScreenshotModal() {
			vm.screenshotModalShown = !vm.screenshotModalShown;
		}
		
		function toggleConsoleModal() {
			vm.consoleModalShown = !vm.consoleModalShown;
		}
		
		function getScreenshotsFromTimestamp(timestamp){
			if ($state.$current.name === 'screenshots.methods') {
				RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass, timestamp);
			}
		}
		
		function getCommentFromFileName(fileName){
			var path = fileName;
			var index = path.indexOf("-_-");
			
			if (index === -1) {
				return undefined;
			} else {
				var comment = path.substring();
				return comment.slice(0,index);
			}
		}
		
		function setCaseSizeByMethod(){
			var data = ScreenshotMaster.data;
			
			for (var i = 0; i < data.length; i++) {
				var screenshotLength = 0;
				for (var j = 0; j < data[i].testcases.length; j++) {
					if (data[i].testcases[j].screenshots.length > screenshotLength) {
						screenshotLength = data[i].testcases[j].screenshots.length;
					}
				}
				data[i].screenshotLength = screenshotLength;
				if (data[i].screenshotLength > 0) {
					data.screenshotsExists = true;
				}
			}
			if (data.screenshotsExists === undefined) {
				data.screenshotsExists = false;
			}
		}
	}
})();