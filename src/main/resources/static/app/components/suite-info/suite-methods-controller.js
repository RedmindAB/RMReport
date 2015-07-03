(function(){
	'use strict';
	
	angular
	.module('webLog')
	.controller('SuiteMethodCtrl', SuiteMethodCtrl);
	
	SuiteMethodCtrl.$inject = ['CurrentSuite','RestLoader', 'SuiteInfoHandler', 'SuiteHandler'];
	
	function SuiteMethodCtrl(CurrentSuite, RestLoader, SuiteInfoHandler, SuiteHandler){
		
		var vm = this;
		
		
		vm.setCurrentMethod = setCurrentMethod;
		
		
		getMethods();
		
		
		function getMethods(){
			SuiteHandler.clearOtherChosen(CurrentSuite.currentClass);
			CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
			SuiteInfoHandler.setPassFailAllMethods(CurrentSuite.currentTimestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
		}
		
		function setCurrentMethod(method){
			CurrentSuite.currentMethod = method;
		}
		
	}
	
})();