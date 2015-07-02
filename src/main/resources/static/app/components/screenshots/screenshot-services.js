(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('ScreenshotServices', ScreenshotServices);
	
	ScreenshotServices.$inject = ['$http'];
	
	function ScreenshotServices($http){
		
		return {
			getConsolePrints: 			getConsolePrints,
			loadScreenshotsFromClass: 	loadScreenshotsFromClass
		};
		
		function getConsolePrints(suiteID, timestamp){
		    return $http.get('api/suite/syso?suiteid=' + suiteID + '&timestamp=' + timestamp)
			    .then(getConsoleComplete)
			    .catch(getConsoleFailed);
		    
		    function getConsoleComplete(response){
		    	return response.data;
		    }
		    
		    function getConsoleFailed(error){
		    	console.log(error);
		    }
		    
		}
		
		function loadScreenshotsFromClass(classID, timestamp){
			
		    return $http.get('/api/screenshot/structure?timestamp='+timestamp+'&classid='+classID)
			    .then(loadScreensComplete)
			    .catch(loadScreensFail);
		    
		    function loadScreensComplete(response){
		    	return response.data;
		    }
		    
		    function loadScreensFail(error){
		    	console.error(error);
		    }
		}
		
//		function loadScreenshotsFromClass(classID){
//			
//		    return $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimestamp+'&classid='+classID)
//		    .success(function(data, status, headers, config){ 
//		    	if(data){
//		    		ScreenshotMaster.data = data;
//		    		vm.getPassFailTotByMethod(CurrentSuite.currentTimestamp, classObj, ScreenshotMaster.data);
//		    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id;
//		    		ScreenshotMaster.currentTimestamp = CurrentSuite.currentTimestamp;
//		    		setCaseSizeByMethod();
//		    	}
//		    }).error(function(data, status, headers, config){
//		    	console.error(data);
//		    });
//		}
	}
})();