(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('checkImage', checkImage);
	
	checkImage.$inject = ['$http'];
	
	function checkImage ($http) {
		return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            attrs.$observe('ngSrc', function(ngSrc) {
	                $http.get(ngSrc).success(function(){
	                }).error(function(){
	                    element.attr('src', 'assets/img/logos/no-logo.png'); // set default image
	                });
	            });
	        }
	    };
	};
})();