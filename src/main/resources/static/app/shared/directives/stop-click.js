(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('stopClick', stopClick);
	
	function stopClick (){
		return {
	        restrict: 'A',
	        link: function(scope, element, attr){
	        	element.bind('click', function(e){
	        		e.stopPropagation();
	        	});
	        }
	    };
	}
})();