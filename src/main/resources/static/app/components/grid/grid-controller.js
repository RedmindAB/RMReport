angular.module('webLog')
.controller("GridCtrl", ["$scope", "$http", function($scope, $http){
	
	$scope.gridData = undefined;
	
	$scope.isDesktop = function(dataObj){
		return dataObj === "MAC" || dataObj === "PC";
	}
	
	$scope.getBrowserImage = function(browserName){
		if(browserName === "firefox"){
			return "assets/img/logos/console/firefox.png";
		}
		else{
			return "assets/img/logos/console/chrome.png";
		}
	}
	
	$scope.isDevicesConnected = function(){
		if($scope.gridData !== undefined){
		var proxies = $scope.gridData.FreeProxies;
			for(var i = 0; i < proxies.length; i++){
				if(proxies[i].capabilities[0].platform != "MAC" && proxies[i].capabilities[0].platform != "PC"){
					return true;
				}
			}
		}
		return false;
	}
	
	$http.get('/api/selenium/griddata')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.gridData = data;
    	};
    }).error(function(data, status, headers, config){
    	console.error(data);
    });
}]);