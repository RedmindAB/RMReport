(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DeviceData', DeviceData);
	
	function DeviceData () {
		return {
			platforms:['android', 'ios', 'windows', 'osx', 'linux'],
			devices:[],
			existingPlatforms:[],
			classes:[],
			className:[],
			calulcatePercent:function(platform) {
				console.log(platform)
				console.log(platform.totalFail / platform.total);
		        return platform.totalFail / platform.total;
		    }
		};
	}
})();