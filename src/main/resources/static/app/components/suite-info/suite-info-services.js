(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('SuiteInfoServices', SuiteInfoServices);
	
	SuiteInfoServices.$inject = ['$http'];
	
	function SuiteInfoServices($http){
		
		return {
			getPassFailByMethod: 			getPassFailByMethod,
			getPassFailByClass: 			getPassFailByClass,
			getSuiteSkeletonByTimestamp: 	getSuiteSkeletonByTimestamp,
			getSpecsInfo:					getSpecsInfo
		};
		
		function getSuiteSkeletonByTimestamp(timestamp, suiteID){
		    return $http.get("/api/suite/bytimestamp?suiteid="+ suiteID + "&timestamp="+timestamp)
			    .then(getSkeletonComplete)
			    .catch(getSkeletonFail);
		    
		    function getSkeletonComplete(response){
		    	return response.data;
		    }
		    
		    function getSkeletonFail(error){
		    	console.error(error);
		    }
		}
		
		function getPassFailByMethod(timestamp, classID, methodID){
			return $http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classID+'&testcaseid='+methodID)
				.then(getPassFailMethodComplete)
				.catch(getPassFailMethodFail);
			
			function getPassFailMethodComplete(response){
				return response.data;
			}
			
			function getPassFailMethodFail(error){
				console.log(error.data);
			}
		}
		
		function getPassFailByClass(timestamp, classID){
			
			return $http.get('/api/class/passfail?timestamp=' + timestamp + '&classid='+classID)
				.then(getPassFailClassComplete)
				.catch(getPassFailClassFail);
			
			function getPassFailClassComplete(response){
				return response.data;
			}
			
			function getPassFailClassFail(error){
				console.error(error.data);
			}
		}
		
		function getSpecsInfo(suiteID, resLimit){
		    return $http.get('/api/stats/options?suiteid='+suiteID+'&limit='+resLimit)
			    .then(getSpecsComplete)
			    .catch(getSpecsFail);
		    
		    function getSpecsComplete(response){
		    	return response.data;
		    }
		    
		    function getSpecsFail(error){
		    	console.error(error.data);
		    }
		}
	}
})();