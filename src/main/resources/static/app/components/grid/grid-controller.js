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
		vm.getBrowserImage 			= getBrowserImage;
		vm.getOSLogo 				= getOSLogo;
		vm.isDesktop 				= isDesktop;
		vm.isHubConnected 			= isHubConnected;
		vm.isDesktopNodesConnected 	= isDesktopNodesConnected;
		vm.isDeviceNodesConnected 	= isDeviceNodesConnected;
		vm.noNodesConnected 		= noNodesConnected;
		vm.setCurrentGrid 			= setCurrentGrid;
		
		
		init();
		
		
		$scope.$on("closeModal", function() {
			gridToggleModal();
		});
		
		/*
		 * starts the polling for selenium grid info
		 * when changes state TO grid and stops it when
		 * changing state FROM grid.
		 */
		var pollController = $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
			vm.gridModalShown = false;
			if(fromState.name === 'grid'){
				Polling.stopPolling("grid");
			}
			pollController();
			
		});
		
		/*
		 * init method, run all function to get
		 * data to prepare the state in here.
		 */
		function init(){
			Polling.startPolling('grid', restURL, GridData, setData);
		}
		
		/*
		 * returns a logo to use as background for OS
		 * in the grid view.
		 * 
		 * @param {String} os name as displayed in switch statement.
		 * @return {String} url to image.
		 */
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
		
		/*
		 * returns a logo to use as an image for
		 * browsers in grid view.
		 * 
		 * @param {String} browser name as displayed in if statement.
		 * @return {String} url to image.
		 */
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
		
		//toggles the modal with JSON file
		function gridToggleModal() {
			vm.gridModalShown = !vm.gridModalShown;
		}
		
		/*
		 * checks the value passed in is undefined
		 * to see if the object contains it. used
		 * to check if it is a desktop.
		 * 
		 * @param {Value} value in an object
		 * @return {Boolean} returns true if value is undefined.
		 */
		function isDesktop(platformName){
			return platformName === undefined;
		}
		
		/*
		 * checks if GridData.data.error is not undefined.
		 * If it isn't undefined we are live and are
		 * getting data from the grid.
		 * 
		 * @return {Boolean} returns true if connection is found.
		 */
		function isHubConnected(){
			if(GridData.data.error !== undefined){
				return true;
			}
			else{
				return false;
			}
		}
		
		/*
		 * Checks if any desktop are connected by
		 * iterating through all objects in GridData.data.FreeProxies
		 * and looks for either MAC or PC as a value in the platform var.
		 * Stops when one is found and determines atleast one is connected.
		 * 
		 * @return {Boolean} true if one MAC or PC is found.
		 */
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
		
		/*
		 * Checks if any devices are connected by
		 * iterating through all objects in GridData.data.FreeProxies
		 * and looks for an undefined value in platformName since
		 * it would only be defined in a desktop.
		 * 
		 * @return {Boolean} true if one platformName === undefined id found.
		 */
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
		
		/*
		 * Checks if GridData.data.FreeProxies and
		 * GridData.data.BusyProxies are empty to see if
		 * anything is connected at all.
		 * 
		 * @param {Boolean} returns true if nothing is connected.
		 */
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
		
		/*
		 * Converts the JSON element grid to an object
		 * and stores it in vm.currentGridObject.
		 * 
		 * @param {JSON} a json object to save.
		 */
		function setCurrentGrid(grid){
			vm.currentGridObject = JSON.stringify(grid,null,4);
		}
		
		/*
		 * saves an object into GridData.data
		 * to use for displaying the grid.
		 * 
		 * @param {Object}
		 */
		function setData(obj){
			GridData.data = obj;
		}
		

	}
})();