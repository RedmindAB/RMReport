(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DashboardServices', DashboardServices);
	
	DashboardServices.$inject = ['$http', '$q', 'DeviceData'];
	
	function DashboardServices ($http, $q, DeviceData) {

		var devices = getDevices;
		var existingPlatforms = getPlatforms;
		
		return {
			getPlatforms: existingPlatforms,
			getDevices: devices
			
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
	}
})();