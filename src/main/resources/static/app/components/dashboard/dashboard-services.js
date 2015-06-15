(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DashboardServices', DashboardServices);
	
	DashboardServices.$inject = ['$http', '$q', 'DeviceData', 'CurrentSuite', 'Utilities'];
	
	function DashboardServices ($http, $q, DeviceData, CurrentSuite, Utilities) {

		var existingPlatforms = getPlatforms;
		var devices = getDevices;
		var classes = getClasses;
		
		return {
			getPlatforms: existingPlatforms,
			getDevices: devices,
			getClasses: classes
		};
		
		function getDevices(suiteid){
			DeviceData.devices = [];
		    var promises = [];

		    if (DeviceData.platforms.length > 0) {
			    angular.forEach(DeviceData.existingPlatforms, function(key) {
					var promise = $http.get('/api/stats/device/fail/' + suiteid + '/' + key + "?limit=50")
					.success(function(data, status, headers, config){ 
					}).error(function(data, status, headers, config){
					});
					promises.push(promise);
			    });
		    return $q.all(promises).then(function(request){
		    	for (var i = 0; i < request.length; i++) {
					DeviceData.devices.push(request[i].data);
				}
		    });
		    }
		}
		
		function getPlatforms(suiteid) {
			var promises = [];
			DeviceData.existingPlatforms = [];
			for (var i = 0; i < DeviceData.platforms.length; i++) {
				var promise = $http.get('/api/stats/device/fail/' + suiteid + '/' + DeviceData.platforms[i] + "?limit=50")
				.success(function(data, status, headers, config){ 
				}).error(function(data, status, headers, config){
				});
				promises.push(promise);
			}
			return $q.all(promises).then(function(request){
				for (var i = 0; i < request.length; i++) {
					if(request[i].data.length > 0){
						DeviceData.existingPlatforms.push(DeviceData.platforms[i]);
					}
				}
		    });
		}
		
		function getClasses(suiteid) {
			var promises = [];
			DeviceData.className = [];
			DeviceData.classes = [];
			DeviceData.lastFail = [];
			
			var promise = $http.get('/api/stats/methodfail/' + suiteid + "?limit=50" + "&maxres=10")
			.success(function(data, status, headers, config){ 
			}).error(function(data, status, headers, config){
			});
			promises.push(promise);
			return $q.all(promises).then(function(request){
				var requestLength = request.length;
				if(requestLength > 0){
					for (var i = 0; i < requestLength; i++) {
						DeviceData.classes.push(request[i].data);
					}
					for (var a = 0; a < request[0].data.length; a++) {
						for (var b = 0; b < Utilities.descTimestamps.length; b++) {
							if(request[0].data[a].lastfail === Utilities.descTimestamps[b]){
								DeviceData.lastFail.push(b);
							}
						}
					}
					
					/* Split className to get only className without src path --> Not currently used. Using suite name from CurrentSuite instead.
		    		var obj = DeviceData.classes[0];
		    		
		    		var objArray = $.map(obj, function(value, index) {
		    		    return [value];
		    		});
		    		
			    	var array = objArray[0].classname.split('.');
			    	var arrayLength = array.length;
			    	var className = array[array.length-1];
			    	DeviceData.className.push(className);
			    	return className;
			    	
			    	*/
				}
		    });
		}
	}
})();