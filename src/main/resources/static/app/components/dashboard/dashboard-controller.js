(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['DashboardServices', 'Charts', 'CurrentSuite', 'DeviceData','ChartMaker'];
			
	function DashboardCtrl(DashboardServices, Charts, CurrentSuite, DeviceData, ChartMaker){
		
		var vm = this;

		vm.myData 				= [];
		vm.devices 				= [];
		vm.existingPlatforms 	= [];
		vm.className			= [];
		vm.methodPass			= [];
		
		vm.Charts 				= Charts;
		vm.DeviceData 			= DeviceData;
		vm.DashboardServices 	= DashboardServices;
		vm.methodPassOrder		= 'averagepass';
		vm.methodPassReverse	= true;
	    
		
		vm.getDevices 			= getDevices;
		vm.getClasses 			= vm.getClasses;
		vm.getPlatforms 		= getPlatforms;
		vm.getMethodPass		= getMethodPass;
		vm.limit				= 50;
		vm.updatePage			= update;
		vm.lastRunRMDocs		= lastRunRMDocs;
		
		init();
		
		
		/*
		 * Setup method for the controller.
		 * Everything needed to fetch data
		 * should be run in here.
		 */
		function init(){
			ChartMaker.loadHomeChart(CurrentSuite.currentSuiteInfo, Charts.chartHomeConfig);
			getClasses(CurrentSuite.currentSuiteInfo.id);
			getPlatforms(CurrentSuite.currentSuiteInfo.id);
			getMethodPass(CurrentSuite.currentSuiteInfo.id);
		}
		
		/*
		 * get class statistics based
		 * on suite id and sets it to
		 * @param {Integer} suite id
		 */
		function getClasses(suiteid){
			DashboardServices.getClasses(suiteid, vm.limit);
		}
		
		function getDevices(suiteid) {
			DashboardServices.getDevices(suiteid, vm.limit).then(function(request){
			});
		}
		
		function getPlatforms(suiteid) {
			DashboardServices.getPlatforms(suiteid, vm.limit).then(function(request){
				getDevices(suiteid);
			});
		}
		
		function getMethodPass(suiteid){
			DashboardServices.getMethodPass(suiteid, vm.limit).then(function(result){
				vm.methodPass = result;
			});
		}
		
		function lastRunRMDocs(limit, threshold, downloadAsFile){
			var file = "";
			if(downloadAsFile){
				file = "&file";
			}
			var url = "/api/stats/rmdocs/"+CurrentSuite.currentSuiteInfo.id+"?limit="+limit+"&threshold="+threshold+file;
			window.location.assign(url);
		}
		
		function update(){
			init();
		}
	}
})();