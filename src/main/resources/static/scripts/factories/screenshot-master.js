angular.module('webLog')
	.factory('ScreenshotMaster', function(){
		return {
			data:[],
			currentClass: undefined,
			currentTimestamp: undefined,
			previousView: undefined
		};
	});