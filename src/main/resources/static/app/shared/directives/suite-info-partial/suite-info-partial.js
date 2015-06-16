(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
	    return {
	        restrict: 'E',
	        controller: 'SuiteInfoCtrl',
	        controllerAs: 'ctrl',
	        scope: { 
	        	iterationObject: '=iterationObject',
	        	loadFunction: '=loadFunction',
	        	loadFunctionTwo: '=loadFunctionTwo',
	        	toState: '=toState', 
	        	type: '=type'
	        },
	        templateUrl: 'app/shared/directives/suite-info-partial/suite-info-partial.html'
	    };
	}
})();