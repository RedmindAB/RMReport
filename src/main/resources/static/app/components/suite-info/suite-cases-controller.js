(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteCaseCtrl', SuiteCaseCtrl);
	
	SuiteCaseCtrl.$inject = ['CurrentSuite','RestLoader','ChartMaker'];
	
	function SuiteCaseCtrl(CurrentSuite, RestLoader, ChartMaker){
		
		var vm = this;
		
		vm.sorting = 'Pass / Fail';
		vm.reverseSorting = false;
		
		
		vm.getOrder			= getOrder;
		vm.sortBy 			= sortBy;
		vm.addCaseToGraph 	= addCaseToGraph;
		
		
		getCases();
		
		
		function getCases(){
			RestLoader.getCases();
		}
		
	    function addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer){
	    	ChartMaker.addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer);
	    }
		
		function sortBy(sorting){
			vm.sorting = sorting;
			vm.reverseSorting = !vm.reverseSorting;
		}
		
		function getOrder(){
			
			switch (vm.sorting) {
			case 'Pass / Fail':
				return ['result', 'osname', 'devicename','browsername'];
				
			case 'Platform':
				return ['osname','result', 'devicename','browsername'];
				
			case 'Device':
				return ['devicename','result', 'osname', 'browsername'];
				
			case 'Browser':
				return ['browsername','result', 'osname', 'devicename'];
				
			case 'Runtime':
				return ['timetorun','result'];

			default:
				return ['result', 'osname', 'devicename','browsername'];
			}
		}
	}
})();