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
			modalColors: ['#FBF5E6', '#F6F9ED' ,'#FFFFF2', '#EBECE4', '#EEF3E2', '#FDFDF0'],
			lastFail: [],
		};
	}
})();