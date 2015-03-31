angular.module('webLog')
	.directive('stopClick', [function(){
	    return {
	        restrict: 'A',
	        link: function(scope, element, attr){
	        	element.bind('click', function(e){
	        		e.stopPropagation();
	        		console.log('stop');
	        	})
	        }
    };
}])