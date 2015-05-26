angular
	.module('webLog')
	.directive('ratioKeeper', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
        	var width = $(".logo").width();
        	$(".box").width(width);
        }
    };
});