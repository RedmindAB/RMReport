(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DashboardServices', DashboardServices);
	
	DashboardServices.$inject = ['$http', '$q', 'DeviceData', 'CurrentSuite'];
	
	function DashboardServices ($http, $q, DeviceData, CurrentSuite) {

		var existingPlatforms = getPlatforms;
		var devices = getDevices;
		var classes = getClasses;
		var deviceRange = getDeviceRange;
		
		return {
			getPlatforms: existingPlatforms,
			getDevices: devices,
			getClasses: classes,
			getDeviceRange
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
			var promise = $http.get('/api/stats/methodfail/' + suiteid + "?limit=50")
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
		
		function getDeviceRange(suiteid, timestamp){
			var promises = [];
			DeviceData.deviceRange = [];
			var promise = $http.get('/api/stats/devicerange/' + suiteid + '/' + timestamp)
			.success(function(data, status, headers, config){ 
			}).error(function(data, status, headers, config){
			});
			promises.push(promise);
			return $q.all(promises).then(function(request){
				var requestLength = request.length;
				if(requestLength > 0){
					for (var i = 0; i < requestLength; i++) {
						DeviceData.deviceRange.push(request[i].data);
					}

				}
		    });
		}
	}
})();