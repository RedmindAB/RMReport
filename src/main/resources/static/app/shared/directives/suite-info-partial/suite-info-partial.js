(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
	    return {
	        controller: 'GlobalCtrl',
	        controllerAs: 'ctrl',
	        scope: { 
	        	iterationObject: '=',
	        	loadFunction: '=',
	        	loadFunctionTwo: '=',
	        	toState: '=', 
	        	type: '=',
	        	showCheckBox: '=',
	        	hideOn: '='
	        },
	        link: function(scope, element, attrs) {
	            scope.predicate = 'stats.totFail';
	            scope.reverseSort = true;

	            scope.sortBy = function(sorting){
	            	scope.predicate = sorting;
	            	scope.reverseSort = !scope.reverseSort;
	            };
	        },
	        templateUrl: 'app/shared/directives/suite-info-partial/suite-info-partial.html'
	    };
	}
})();