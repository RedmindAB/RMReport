(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('ScreenshotMethodCtrl', ScreenshotMethodCtrl);
	
	ScreenshotMethodCtrl.$inject = ['$scope','ScreenshotMaster','RestLoader', 'CurrentSuite'];
	
	function ScreenshotMethodCtrl($scope, ScreenshotMaster, RestLoader, CurrentSuite){
		
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
		
		
		loadScreenshotsFromClass();
		
		$scope.$on("closeScreenshotModal", function() {
			vm.screenshotModalShown = false;
		});
		
		$scope.$on("closeConsoleModal", function() {
			vm.consoleModalShown = false;
		});
		
		function loadScreenshotsFromClass(){
			RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass);
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
		
	}
})();