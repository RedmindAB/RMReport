(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('ChartService', ChartService);
	
	ChartService.$inject = ['$http'];
	
	function ChartService($http){
		
		var service = {
				getTooltipData: getTooltipData
		};
		
		function getTooltipData(id, timestamp){
			return $http({
				url:'/api/stats/devicerange/'+id+'/'+timestamp,
	            method: 'GET',
	            cache: true,
	        })
	        .then(complete)
	        .catch(fail);
			
			function complete(response){
				return response.data;
			}
			
			function fail(error){
				console.error(error.data);
			}
		}
		
		return service;
	}
})();