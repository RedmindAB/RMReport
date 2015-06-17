(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller("GridCtrl", GridCtrl);
		
	GridCtrl.$inject = ["$scope", "$rootScope", "$http", "Polling", "GridData"];		
	
	function GridCtrl ($scope, $rootScope ,$http, Polling, GridData){
		
		var vm = this;
		
		var restURL = "/api/selenium/griddata";
		
		vm.GridData = GridData;
		
		vm.currentGridObject = {};
		
		vm.gridModalShown = false;
		
		
		vm.gridToggleModal 			= gridToggleModal;
		vm.setCurrentGrid 			= setCurrentGrid;
		vm.getOSLogo 				= getOSLogo;
		vm.isDesktop 				= isDesktop;
		vm.getBrowserImage 			= getBrowserImage;
		vm.noNodesConnected 		= noNodesConnected;
		vm.isHubConnected 			= isHubConnected;
		vm.isDesktopNodesConnected 	= isDesktopNodesConnected;
		vm.isDeviceNodesConnected 	= isDeviceNodesConnected;
		
		
		Polling.startPolling('grid', restURL, GridData, setData);
		
		$scope.$on("closeModal", function() {
			gridToggleModal();
		});
		
		var pollController = $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
			vm.gridModalShown = false;
			if(fromState.name === 'grid'){
				Polling.stopPolling("grid");
			}
			pollController();
			
		});
		
		function gridToggleModal() {
			vm.gridModalShown = !vm.gridModalShown;
		}
		
		
		function setCurrentGrid(grid){
			vm.currentGridObject = JSON.stringify(grid,null,4);
		}
		
		function setData(obj){
			GridData.data = obj;
		}
		
		function getOSLogo(os){
			switch (os) {
			case "MAC":
				return "assets/img/logos/console/apple_mac.png";
	
			case "PC":
				return "assets/img/logos/console/windows.png";
	
			case "ANDROID":
				return "assets/img/logos/console/android.png";
				
			case "IOS":
				return "assets/img/logos/console/apple_device.png";
				
	
			default:
				return "";
			}
		}
		
		function isDesktop(platformName){
			return platformName === undefined;
		}
		
		function getBrowserImage(browserName){
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
		
		function noNodesConnected(){
			if(GridData.data.FreeProxies !== undefined){
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
		
		function isHubConnected(){
			if(GridData.data.error !== undefined){
				return true;
			}
			else{
				return false;
			}
		}
		
		function isDesktopNodesConnected(){
			if(GridData.data.FreeProxies){
			var proxies = GridData.data.FreeProxies;
				for(var i = 0; i < proxies.length; i++){
					if(proxies[i].capabilities[0].platform === "MAC" ||
							proxies[i].capabilities[0].platform === "PC" &&
							proxies[i].capabilities[0].platformName === undefined){
						return true;
					}
				}
			}
			return false;
		}
		
		function isDeviceNodesConnected(){
			if(GridData.data.FreeProxies){
			var proxies = GridData.data.FreeProxies;
				for(var i = 0; i < proxies.length; i++){
					if(proxies[i].capabilities[0].platformName !== undefined){
						return true;
					}
				}
			}
			return false;
		}
	}
})();