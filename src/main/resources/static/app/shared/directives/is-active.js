(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('isActive', isActive);
	
	isActive.$inject = ['$http'];
	
	function isActive($http) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	        	console.log(scope.$state);
	        	if($(".dash").class === 'active'){
	        		$(".reports").addClass('disabled');
	        	}
	        }
	    };
	};
})();