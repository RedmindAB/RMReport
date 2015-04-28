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
	
	function setData(obj){
		GridData.data = obj;
	}
	
	Polling.startPolling('grid', restURL, GridData, setData);
	
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
	
	$scope.isDesktop = function(platformName){
		return platformName === undefined;
	}
	
	$scope.getBrowserImage = function(browserName){
		if(browserName === "firefox"){
			return "assets/img/logos/console/firefox.png";
		}
		else{
			return "assets/img/logos/console/chrome.png";
		}
	}
	
	$scope.noNodesConnected = function(){
		if(GridData.data.FreeProxies != undefined){
			if(GridData.data.FreeProxies.length === 0 && GridData.data.BusyProxies.length === 0){
				return true;
			}
			else {
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	$scope.isHubConnected = function(){
		if(GridData.data.error != undefined){
			return true;
		}
		else{
			return false;
		}
	}
	
	$scope.isDesktopNodesConnected = function(){
		if(GridData.data.FreeProxies){
		var proxies = GridData.data.FreeProxies;
			for(var i = 0; i < proxies.length; i++){
				if(proxies[i].capabilities[0].platform === "MAC" || proxies[i].capabilities[0].platform === "PC" && proxies[i].capabilities[0].platformName == undefined){
					return true;
				}
			}
		}
		return false;
	}
	
	$scope.isDeviceNodesConnected = function(){
		if(GridData.data.FreeProxies){
		var proxies = GridData.data.FreeProxies;
			for(var i = 0; i < proxies.length; i++){
				if(proxies[i].capabilities[0].platformName != undefined){
					return true;
				}
			}
		}
		return false;
	}
	

//	$http.get('/api/selenium/griddata')
//    .success(function(data, status, headers, config){ 
//    	if(data){
//    		$scope.gridData = data;
//    		console.log(data);
//    	};
//    }).error(function(data, status, headers, config){
//    	console.error(data);
//    });

//	mockStuff();
//	
//	function mockStuff(){
//		$scope.mockData= {
//				   "BusyProxies":[
//
//				                  ],
//				                  "FreeProxies":[
//				                     {
//				                        "class":"org.openqa.grid.common.RegistrationRequest",
//				                        "capabilities":[
//				                           {
//				                              "newCommandTimeout":"300",
//				                              "osname":"ANDROID",
//				                              "rmDeviceType":"mobile",
//				                              "platform":"ANDROID",
//				                              "platformVersion":"4.4.4",
//				                              "deviceName":"C6903",
//				                              "platformName":"Android",
//				                              "browserName":"Chrome",
//				                              "description":"Sony C6903  4.4.4",
//				                              "maxInstances":1,
//				                              "appWaitActivity":"WAIT_ACTIVITY",
//				                              "deviceId":"BH905NSB0D"
//				                           }
//				                        ],
//				                        "configuration":{
//				                           "port":8081,
//				                           "host":"192.168.75.120",
//				                           "servlets":"se.redmind.rmtest.selenium.grid.servlets.GridQueryServlet",
//				                           "cleanUpCycle":5000,
//				                           "browserTimeout":0,
//				                           "hubHost":"192.168.75.120",
//				                           "registerCycle":5000,
//				                           "capabilityMatcher":"org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
//				                           "newSessionWaitTimeout":-1,
//				                           "url":"http://192.168.75.120:8081/wd/hub",
//				                           "prioritizer":null,
//				                           "remoteHost":"http://192.168.75.120:8081",
//				                           "register":true,
//				                           "throwOnCapabilityNotPresent":true,
//				                           "nodePolling":5000,
//				                           "proxy":"org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
//				                           "maxSession":1,
//				                           "role":"hub",
//				                           "jettyMaxThreads":-1,
//				                           "hubPort":4444,
//				                           "timeout":300000
//				                        }
//				                     },
//				                     {
//				                        "class":"org.openqa.grid.common.RegistrationRequest",
//				                        "capabilities":[
//				                           {
//				                              "newCommandTimeout":"300",
//				                              "osname":"ANDROID",
//				                              "rmDeviceType":"mobile",
//				                              "platform":"ANDROID",
//				                              "platformVersion":"5.0.1",
//				                              "deviceName":"Nexus 9",
//				                              "platformName":"Android",
//				                              "browserName":"Chrome",
//				                              "description":"google Nexus 9  5.0.1",
//				                              "maxInstances":1,
//				                              "appWaitActivity":"WAIT_ACTIVITY",
//				                              "deviceId":"HT4CPJT00302"
//				                           }
//				                        ],
//				                        "configuration":{
//				                           "port":8082,
//				                           "host":"192.168.75.120",
//				                           "servlets":"se.redmind.rmtest.selenium.grid.servlets.GridQueryServlet",
//				                           "cleanUpCycle":5000,
//				                           "browserTimeout":0,
//				                           "hubHost":"192.168.75.120",
//				                           "registerCycle":5000,
//				                           "capabilityMatcher":"org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
//				                           "newSessionWaitTimeout":-1,
//				                           "url":"http://192.168.75.120:8082/wd/hub",
//				                           "prioritizer":null,
//				                           "remoteHost":"http://192.168.75.120:8082",
//				                           "register":true,
//				                           "throwOnCapabilityNotPresent":true,
//				                           "nodePolling":5000,
//				                           "proxy":"org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
//				                           "maxSession":1,
//				                           "role":"hub",
//				                           "jettyMaxThreads":-1,
//				                           "hubPort":4444,
//				                           "timeout":300000
//				                        }
//				                     },
//				                     {
//				                        "class":"org.openqa.grid.common.RegistrationRequest",
//				                        "capabilities":[
//				                           {
//				                              "newCommandTimeout":"300",
//				                              "platform":"MAC",
//				                              "platformVersion":"8.3",
//				                              "rmDeviceType":"mobile",
//				                              "platformName":"iOS",
//				                              "deviceName":"iPhone 5",
//				                              "browserName":"Safari",
//				                              "maxInstances":1,
//				                              "description":"iPhone 5  8.3 AutogubbesMini3",
//				                              "launchTimeout":"30000",
//				                              "version":"8.3"
//				                           }
//				                        ],
//				                        "configuration":{
//				                           "port":4723,
//				                           "servlets":"se.redmind.rmtest.selenium.grid.servlets.GridQueryServlet",
//				                           "host":"192.168.75.120",
//				                           "cleanUpCycle":2000,
//				                           "browserTimeout":0,
//				                           "hubHost":"192.168.75.120",
//				                           "registerCycle":5000,
//				                           "capabilityMatcher":"org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
//				                           "newSessionWaitTimeout":-1,
//				                           "url":"http://192.168.75.120:4723/wd/hub",
//				                           "prioritizer":null,
//				                           "remoteHost":"http://192.168.75.120:4723",
//				                           "register":true,
//				                           "throwOnCapabilityNotPresent":true,
//				                           "nodePolling":5000,
//				                           "proxy":"org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
//				                           "maxSession":1,
//				                           "role":"hub",
//				                           "jettyMaxThreads":-1,
//				                           "hubPort":4444,
//				                           "timeout":30000
//				                        }
//				                     },
//				                     {
//				                        "class":"org.openqa.grid.common.RegistrationRequest",
//				                        "capabilities":[
//				                           {
//				                              "seleniumProtocol":"WebDriver",
//				                              "osname":"OSX",
//				                              "platform":"MAC",
//				                              "browserName":"firefox",
//				                              "description":"OSX firefox",
//				                              "maxInstances":1
//				                           },
//				                           {
//				                              "seleniumProtocol":"WebDriver",
//				                              "osname":"OSX",
//				                              "platform":"MAC",
//				                              "browserName":"chrome",
//				                              "description":"OSX chrome",
//				                              "maxInstances":1
//				                           }
//				                        ],
//				                        "configuration":{
//				                           "port":6650,
//				                           "nodeConfig":"macNodeConf.json",
//				                           "servlets":"se.redmind.rmtest.selenium.grid.servlets.GridQueryServlet",
//				                           "host":"localhost",
//				                           "cleanUpCycle":5000,
//				                           "browserTimeout":0,
//				                           "hubHost":"localhost",
//				                           "registerCycle":5000,
//				                           "capabilityMatcher":"org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
//				                           "newSessionWaitTimeout":-1,
//				                           "url":"http://localhost:6650",
//				                           "prioritizer":null,
//				                           "remoteHost":"http://localhost:6650",
//				                           "register":true,
//				                           "throwOnCapabilityNotPresent":true,
//				                           "nodePolling":5000,
//				                           "proxy":"org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
//				                           "maxSession":5,
//				                           "role":"node",
//				                           "jettyMaxThreads":-1,
//				                           "hubPort":4444,
//				                           "timeout":300000
//				                        }
//				                     }
//				                  ]
//				               }
//		
//		
//	}
	
}]);