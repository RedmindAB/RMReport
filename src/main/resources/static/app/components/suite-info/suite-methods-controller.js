(function(){
	'use strict';
	
	angular
	.module('webLog')
	.controller('SuiteMethodCtrl', SuiteMethodCtrl);
	
	SuiteMethodCtrl.$inject = ['CurrentSuite','RestLoader'];
	
	function SuiteMethodCtrl(CurrentSuite, RestLoader){
		
		var vm = this;
		
		
		vm.setCurrentMethod = setCurrentMethod;
		
		
		getMethods();
		
		function getMethods(){
			CurrentSuite.clearOtherChosen(CurrentSuite.currentClass);
			CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
			RestLoader.getPassFailTotByMethod(CurrentSuite.currentTimeStamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
		}
		
		function setCurrentMethod(method){
			CurrentSuite.currentMethod = method;
		}
		
	}
	
})();