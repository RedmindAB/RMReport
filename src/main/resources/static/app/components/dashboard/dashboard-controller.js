(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['DashboardServices', 'Charts', 'CurrentSuite', 'DeviceData'];
			
	function DashboardCtrl(DashboardServices, Charts, CurrentSuite, DeviceData){
		
		var vm = this;

		vm.myData 				= [];
		vm.devices 				= [];
		vm.existingPlatforms 	= [];
		vm.className			= [];
		
		vm.Charts 				= Charts;
		vm.DeviceData 			= DeviceData;
		vm.DashboardServices 	= DashboardServices;
	    
		
		vm.getDevices 			= getDevices;
		vm.getClasses 			= vm.getClasses;
		vm.getPlatforms 		= getPlatforms;
		
		
		init();
		
		
		/*
		 * Setup method for the controller.
		 * Everything needed to fetch data
		 * should be run in here.
		 */
		function init(){
			getClasses(CurrentSuite.currentSuiteInfo.id);
			getPlatforms(CurrentSuite.currentSuiteInfo.id)
		}
		
		/*
		 * get class statistics based
		 * on suite id and sets it to
		 * @param {Integer} suite id
		 */
		function getClasses(suiteid){
			DashboardServices.getClasses(suiteid);
		}
		
		function getDevices(suiteid) {
			DashboardServices.getDevices(suiteid).then(function(request){
			});
		}
		
		function getPlatforms(suiteid) {
			DashboardServices.getPlatforms(suiteid).then(function(request){
				getDevices(suiteid);
			});
		}
	}
})();