angular.module('webLog')
.service('GridRestLoader', ['$http', function($http){
	
	var gridRest = this;
	gridRest.getgridData = function(){
		$http.get('/api/selenium/griddata')
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		console.log(data);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
	    });
	}
}]);