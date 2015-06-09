(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DeviceData', DeviceData);
	
	function DeviceData () {
		return {
			platforms:['android', 'ios', 'windows', 'osx', 'linux'],
			devices:[],
			existingPlatforms:[]
		};
	}
})();