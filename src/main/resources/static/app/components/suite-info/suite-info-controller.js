(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteInfoCtrl', SuiteInfoCtrl);
		
	SuiteInfoCtrl.$inject = ['$http','CurrentSuite', 'RestLoader', 'ChartMaker', 'Utilities'];
	
	function SuiteInfoCtrl ($http, CurrentSuite, RestLoader, ChartMaker, Utilities){
		
		var vm = this;
		
		vm.CurrentSuite = CurrentSuite;
		vm.Utilities = Utilities;
		
		vm.sorting = 'Pass / Fail';
		vm.reverseSorting = false;
		
		
		vm.addCaseToGraph				= addCaseToGraph;
		vm.getCases 					= getCases;
		vm.getMethods 					= getMethods;
		vm.getSuiteSkeleton 			= getSuiteSkeleton;
		vm.getSuiteSkeletonByTimestamp 	= getSuiteSkeletonByTimestamp;
		vm.sortBy 						= sortBy;
		vm.getOrder						= getOrder;
		
		
	    function addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer){
	    	ChartMaker.addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer);
	    }
		
		function getCases(method){
			RestLoader.getCases(method);
		}
		
		//stores chosen class info and clears chosen for other classes
		//when going into a class
		function getMethods(classObj){
			CurrentSuite.clearOtherChosen(classObj);
			CurrentSuite.currentClass = classObj;
			CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
			RestLoader.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, classObj, CurrentSuite.currentMethods);
		}
		
		function getSuiteSkeleton(suite){
			RestLoader.getSuiteSkeleton(suite);
		}
		
		function getSuiteSkeletonByTimestamp(timestamp){
			RestLoader.loadTimestamp(timestamp);
		}
		
		function sortBy(sorting){
			vm.sorting = sorting;
			vm.reverseSorting = !vm.reverseSorting;
			
		}
		
		function getOrder(){
			
			switch (vm.sorting) {
			case 'Pass / Fail':
				return ['result', 'osname', 'devicename','browsername'];
				break;
				
			case 'Platform':
				return ['osname','result', 'devicename','browsername'];
				break;
				
			case 'Device':
				return ['devicename','result', 'osname', 'browsername'];
				break;
				
			case 'Browser':
				return ['browsername','result', 'osname', 'devicename'];
				break;
				
			case 'Runtime':
				return ['timetorun','result'];
				break;

			default:
				break;
			}
			
		}
	}
})();