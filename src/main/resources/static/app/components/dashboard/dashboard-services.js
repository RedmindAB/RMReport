(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('DashboardServices', DashboardServices);
	
	DashboardServices.$inject = ['$http', '$q', 'DeviceData'];
	
	function DashboardServices ($http, $q, DeviceData) {

		var devices = getDevices;
		var platforms = getPlatforms;
		
		return {
			getDevices: devices,
			getPlatforms: platforms
		};
		
		function getDevices(suiteid){
			DeviceData.devices = [];
		    var promises = [];
		    
		    if (DeviceData.platforms.length > 0) {
			    angular.forEach(DeviceData.platforms, function(key) {
			    	console.log(key);
			    	console.log(suiteid);
					var promise = $http.get('/api/stats/device/fail/' + suiteid + '/' + key)
					.success(function(data, status, headers, config){ 
						console.log(data);
					}).error(function(data, status, headers, config){
						console.error(data);
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
		
		function getPlatforms(suiteid, platform) {
			return $http({
	            url   : '/api/stats/device/fail/ ' + key.suiteid + '/' + key.platform,
	            method: 'GET'
	        });
			return promise;
		}
	}
})();