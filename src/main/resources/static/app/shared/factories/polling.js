angular.module('webLog')
.factory('Polling', ['$http', function($http) {
	var defaultPollingTime = 5000;
	var polls = {};
	return {
		startPolling: function(name, url, dataObj,func, pollingTime){
			if(!polls[name]){
				var poller = function(){
					$http.get(url)
				    .success(function(data, status, headers, config){
				    	if(data){
				    		if (dataObj !== data) {
				    			func(data);
							}
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
			clearInterval(polls[name]);
			delete polls[name];
		}
	};
}]);