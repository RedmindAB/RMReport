(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
		
		var directive = {
				controller: controller,
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
		
		function controller(Utilities){
			
			var vm = this;
			
			vm.Utilities = Utilities;
			
		}
		
	    return directive;
	}
})();