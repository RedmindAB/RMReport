(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('SuiteClassCtrl', SuiteClassCtrl);
		
	SuiteClassCtrl.$inject = ['$scope', 'CurrentSuite', 'RestLoader', 'SuiteInfoHandler'];
	
	function SuiteClassCtrl ($scope, CurrentSuite, RestLoader, SuiteInfoHandler){
		
		var vm = this;
		
		vm.CurrentSuite = CurrentSuite;

		
		vm.getSuiteSkeletonByTimestamp 	= getSuiteSkeletonByTimestamp;
		vm.setCurrentClass 				= setCurrentClass;
		
		
		init();
		
		function init(){
			console.log("class");
			getSuiteSkeletonByTimestamp(CurrentSuite.currentTimestamp);
		}
		
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
		
		function getSuiteSkeletonByTimestamp(timestamp){
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
	}
})();