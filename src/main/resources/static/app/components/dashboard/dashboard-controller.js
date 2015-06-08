(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('DashboardCtrl', DashboardCtrl);
	
	DashboardCtrl.$inject = ['$scope', '$http','$state', 'DashboardServices', 'Charts', 'CurrentSuite', 'DeviceData'];
			
	function DashboardCtrl($scope, $http, $state, DashboardServices, Charts, CurrentSuite, DeviceData){
		
		var vm = this;
		var requestObj = {};
		
		vm.platforms = ['Android', 'iOS', 'Windows', 'OSX', 'Linux'];
		vm.myData = [];
		vm.devices = [];
		vm.DeviceData = DeviceData;
		
		vm.DashboardServices = DashboardServices;
		vm.Charts = Charts;
		vm.getDevices = getDevices;
		vm.runGetDevices = runGetDevices;
		
		runGetDevices();
		
		function runGetDevices(){
			getDevices(CurrentSuite.currentSuiteInfo.id);
		}
		
		function orderDevices(){
			var myObj = getDevices(CurrentSuite.currentSuiteInfo.id);
			console.log("myObj: " + myObj);
		}
		
		function getDevices(suiteid) {
			DashboardServices.getDevices(suiteid).then(function(request){
				console.log(request);
				console.log(DeviceData.devices);
			});
		}
		
		function getPlatforms(suiteid, platform) {
			vm.platforms = DashboardServices.getPlatforms(suiteid, platform).then(function(request){
				console.log(request);
			});
		}
	}
})();