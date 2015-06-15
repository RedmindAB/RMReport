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
			deviceRange: [],
			modalColors: ['#87AFC7', '#8BB381', '#92C7C7', '#786D5F', '#48B3DF', '#ECA4A9'],
			lastFail: [],
		};
	}
})();