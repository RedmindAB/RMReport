angular.module('webLog')
.controller("GridCtrl", ["$scope", "$rootScope", "$http", "Polling", "GridData", function($scope, $rootScope ,$http, Polling, GridData){
	
	$scope.GridData = GridData;
	$scope.mockData ={
			FreeProxies:[]
	};
	var restURL = "/api/selenium/griddata";
	
	var pollController = $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
		if(fromState.name === 'grid'){
			Polling.stopPolling("grid");
		}
		pollController();
		
	});
	
	console.log("Starting");
	Polling.startPolling('grid', restURL);
	
	$scope.getOSLogo = function(os){
		switch (os) {
		case "MAC":
			return "assets/img/logos/console/apple_mac.png";
			break;

		case "PC":
			return "assets/img/logos/console/windows.png";
			break;

		case "ANDROID":
			return "assets/img/logos/console/android.png";
			break;
			
		case "IOS":
			return "assets/img/logos/console/apple_device.png";
			break;
			

		default:
			return "";
			break;
		}
	}
	
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
		var proxies = $scope.mockData.FreeProxies;
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
    		console.log(data);
    	};
    }).error(function(data, status, headers, config){
    	console.error(data);
    });

	mockStuff();
	
	function mockStuff(){
		$scope.mockData.FreeProxies = [];
		
		for (var i = 0; i < 50; i++) {
			if (i < 10) {
				
				var data = {
						configuration:{
							port: 6651
						},
						capabilities: [
						               {
						            	   seleniumProtocol: "WebDriver",
						            	   osname: "OSX",
						            	   platform: "MAC",
						            	   browserName: "firefox",
						            	   
						               },
						               {
						   				seleniumProtocol: "WebDriver",
										osname: "OSX",
										platform: "MAC",
										browserName: "chrome",
						               }
						               ]
				}
				
				$scope.mockData.FreeProxies.push(data);
			}
			
			if (i > 10 && i < 20) {
				
				var data = {
						configuration:{
							port: 6652
						},
						capabilities: [
						               {
						            	   seleniumProtocol: "WebDriver",
						            	   osname: "WINDOWS",
						            	   platform: "PC",
						            	   browserName: "firefox",
						            	   
						               },
						               {
						   				seleniumProtocol: "WebDriver",
										osname: "WINDOWS",
										platform: "PC",
										browserName: "chrome",
						               }
						               ]
				}
				
				$scope.mockData.FreeProxies.push(data);
			}
			
			if (i > 20 && i < 30) {
				
				var data = {
						configuration:{
							port: 6653
						},
						capabilities: [
						               {
						            	    rmDeviceType: "Mobile",
						               		osname: "ANDROID",
											platform: "ANDROID",
											platformName: "Android",
											appActivity: ".debug.DebugMainActivity",
											appWaitActivity: ".Splash",
											platformVersion: "5.1",
											deviceName: "Nexus 6",
											deviceId: "ZX1G524BJK",
						               }
						               ]
				}
				
				$scope.mockData.FreeProxies.push(data);
				
				
			}
			
			if (i > 30 && i < 40) {
				var data = {
						configuration:{
							port: 6654
						},
						capabilities: [
						               {
						            	    rmDeviceType: "Mobile",
						               		osname: "IOS",
											platform: "IOS",
											platformName: "IOS",
											appActivity: ".debug.DebugMainActivity",
											appWaitActivity: ".Splash",
											platformVersion: "11",
											deviceName: "iPhone 6",
											deviceId: "ZX1G524BJK",
						               }
						               ]
				}
				
				$scope.mockData.FreeProxies.push(data);
			}
			
			if (i > 40 && i < 50) {
				
				var data = {
						configuration:{
							port: 6653
						},
						capabilities: [
						               {
						            	    rmDeviceType: "Surf Pad",
						               		osname: "IOS",
											platform: "IOS",
											platformName: "IOS",
											appActivity: ".debug.DebugMainActivity",
											appWaitActivity: ".Splash",
											platformVersion: "11",
											deviceName: "iPad Air",
											deviceId: "ZX1G524BJK",
						               }
						               ]
				}
				
				$scope.mockData.FreeProxies.push(data);
			}
		}
		console.log($scope.mockData);
	}
	
}]);