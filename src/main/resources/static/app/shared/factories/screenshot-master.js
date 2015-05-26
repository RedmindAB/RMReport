angular
	.module('webLog')
	.factory('ScreenshotMaster', screenshotMaster);

function screenshotMaster () {
	return {
		data:[],
		currentClass: undefined,
		currentTimestamp: undefined,
		previousView: undefined
	};
};