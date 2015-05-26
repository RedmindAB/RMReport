(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller("GridCtrl", GridCtrl);
		
	GridCtrl.$inject = ["$scope", "$rootScope", "$http", "Polling", "GridData"];		
	
	function GridCtrl ($scope, $rootScope ,$http, Polling, GridData){
		
		$scope.GridData = GridData;
		$scope.mockData ={
				FreeProxies:[]
		};
		$scope.currentGridObject = {};
		
		$scope.gridModalShown = false;
		
		$scope.gridToggleModal = function() {
			$scope.gridModalShown = !$scope.gridModalShown;
		};
		
		$scope.$on("closeModal", function() {
			$scope.gridToggleModal();
		});
		
		$scope.setCurrentGrid = function(grid){
			$scope.currentGridObject = JSON.stringify(grid,null,4);
		}
		
		var restURL = "/api/selenium/griddata";
		
		var pollController = $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
			$scope.gridToggleModal();
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
			else if(browserName === "chrome"){
				return "assets/img/logos/console/chrome.png";
			}
			else{
				return "assets/img/logos/console/phantomjs.png";
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
	};
})();