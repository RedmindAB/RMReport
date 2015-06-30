(function(){
	'use strict';
	
	angular
	.module('webLog')
	.controller('SuiteMethodCtrl', SuiteMethodCtrl);
	
	SuiteMethodCtrl.$inject = ['CurrentSuite','RestLoader', 'SuiteInfoHandler'];
	
	function SuiteMethodCtrl(CurrentSuite, RestLoader, SuiteInfoHandler){
		
		var vm = this;
		
		
		vm.setCurrentMethod = setCurrentMethod;
		
		
		getMethods();
		
		
		function getMethods(){
			CurrentSuite.clearOtherChosen(CurrentSuite.currentClass);
			CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
			SuiteInfoHandler.setPassFailAllMethods(CurrentSuite.currentTimestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
		}
		
		function setCurrentMethod(method){
			CurrentSuite.currentMethod = method;
		}
		
	}
	
})();