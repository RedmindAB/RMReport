angular.module('webLog')
.factory('Polling', ['$http', 'GridData', function($http, GridData) {
	var defaultPollingTime = 5000;
	var polls = {};
	return {
		startPolling: function(name, url, gridDataObj, pollingTime){
			if(!polls[name]){
				var poller = function(){
					$http.get(url)
				    .success(function(data, status, headers, config){ 
				    	if(data){
				    		GridData.data = data;
				    		console.log(data);
				    	};
				    }).error(function(data, status, headers, config){
				    	console.error(data);
				    });
				}
				poller();
				polls[name] = setInterval(poller, pollingTime || defaultPollingTime);
			}
		},
		stopPolling: function(name){
			console.log("Stopping poll");
			clearInterval(polls[name]);
			delete polls[name];
		}
	};
}]);