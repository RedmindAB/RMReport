(function(){
	'user strict';
	
	angular
		.module("webLog")
		.service("ParameterService", ParameterService);
	
	ParameterService.$inject = ['$http', '$q']
	
	function ParameterService($http, $q){
		return {
			getParameters: getParameters
		};
		
		
		function getParameters(suiteid, timestamp){
			return $http.get("/api/suite/parameters/"+suiteid+"/"+timestamp)
			.then(completeGetParameters)
			.catch(errorGetParameters);
			
			function completeGetParameters(response){
				return response.data;
			}
			
			function errorGetParameters(response){
				console.log("error, error, ERROR");
			}
		}
	}
})();