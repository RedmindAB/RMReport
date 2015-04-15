angular.module('webLog')
.directive('ratioKeeper', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
        	var width = $(".box").width();
        	$(".logo").width(width);
        	console.log(width);
        }
    };
});