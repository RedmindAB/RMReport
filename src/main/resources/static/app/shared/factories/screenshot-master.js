(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('ScreenshotMaster', screenshotMaster);
	
	function screenshotMaster () {
		
		return {
			data:[],
			currentClass: undefined,
			currentTimestamp: undefined,
			previousView: undefined,
			getScreenshotsFromFileName: getScreenshotsFromFileName
		};
		
		function getScreenshotsFromFileName(fileName, caseObj){
			if (!fileName) {
				return 'assets/img/screenshots/no-screenshot.png';
			}
			return '/api/screenshot/byfilename?timestamp='+this.currentTimestamp+'&filename='+fileName;
		}
	}
})();