(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('ScreenshotServices', ScreenshotServices);
	
	ScreenshotServices.$inject = ['$http'];
	
	function ScreenshotServices($http){
		
		var service = {//.1
				getConsolePrints: 			getConsolePrints,
				loadScreenshotsFromClass: 	loadScreenshotsFromClass
		};
		
		function getConsolePrints(suiteID, timestamp){//.2
		    return $http.get('api/suite/syso?suiteid=' + suiteID + '&timestamp=' + timestamp)
			    .then(getConsoleComplete)
			    .catch(getConsoleFailed);
		    
		    function getConsoleComplete(response){//.3
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
		
		return service;//.4
	}
})();

/*
 * Broken down and explained below ---------------------------------------------------------------------------------
 */

//basic setup is explained in the controller-example

(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('ScreenshotServices', ScreenshotServices);
	
	ScreenshotServices.$inject = ['$http'];
	
	function ScreenshotServices($http){
		
		/*.1
		 * 
		 * This is where we will store all functions and variables we want to be
		 * reachable outside of this file. this service will be returned at the bottom
		 * of this file. If you have read the controller-example, imagine this
		 * to have somewhat the same functionality as the var vm = this.
		 */
		var service = {
				getConsolePrints: 			getConsolePrints,
				loadScreenshotsFromClass: 	loadScreenshotsFromClass
		};
		
		/*.2
		 * 
		 * A service in angular should always return itself. The reasoning behind
		 * this is because if we return the actual call we are able to chain
		 * an action from this after we use the function. Lets imagine we call
		 * this from a controller in which we have injected this factory.
		 * 
		 * We then have the possibility of using
		 * 
		 * ScreenshotServices.getConsolePrints(id, timestamp)
		 * .then(function(data){
		 * 		some-Other-Function-Which-Is-Dependent-On-This-Data(data);
		 * });
		 * 
		 * The then statement means: run this function as soon as the request from
		 * this service returns.
		 * 
		 * Make sure to only perform the actual business logic in the service.
		 * Any other logic, such as calculating or setting the data should be done
		 * in the controller or factory which uses the service. This is simply
		 * because we want the service to be as general as possible so we can
		 * use it where ever we want in the application to fetch the data.
		 */
		function getConsolePrints(suiteID, timestamp){
		    return $http.get('api/suite/syso?suiteid=' + suiteID + '&timestamp=' + timestamp)
			    .then(getConsoleComplete)
			    .catch(getConsoleFailed);
		    
		    /*.3
		     * 
		     * define the functions to use in the .then statement down under here
		     * for readability purposes. This also gives us the ability of only
		     * fetching the data variable in the response and throw away the rest
		     * if we have no use for it. 
		     * 
		     * You will notice we use this in the same manner as in the
		     * .controller or the .factory where we pass this in as a reference
		     * in the .then() statement.
		     */
		    function getConsoleComplete(response){
		    	return response.data;
		    }
		    
		    function getConsoleFailed(error){
		    	console.error(error);
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
		
		/*.4
		 * 
		 * finally return the service so we can reach all the jazz in here.
		 * If this service is injected into a controller, it will be reached via:
		 * 
		 * ScreenshotServices.methodNameInTheServiceObject();
		 */
		return service;
	}
})();
