(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('ratioKeeper', ratioKeeper);
	
	ratioKeeper.$inject = ['$http'];
	
	function ratioKeeper ($http) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	        	var width = $(".logo").width();
	        	$(".box").width(width);
	        }
	    };
	};
})();