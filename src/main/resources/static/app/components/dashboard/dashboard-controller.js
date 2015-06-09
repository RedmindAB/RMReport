(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['$scope', '$http','$state', 'DashboardServices', 'Charts', 'CurrentSuite', 'DeviceData'];
			
	function DashboardCtrl($scope, $http, $state, DashboardServices, Charts, CurrentSuite, DeviceData){
		
		var vm = this;
		var requestObj = {};
		
		vm.myData = [];
		vm.devices = [];
		vm.DeviceData = DeviceData;
		vm.existingPlatforms = [];
	    
		vm.DashboardServices = DashboardServices;
		vm.Charts = Charts;
		vm.getDevices = getDevices;
		
		getPlatforms(CurrentSuite.currentSuiteInfo.id);
		
		function orderDevices(){
			var myObj = getDevices(CurrentSuite.currentSuiteInfo.id);
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