angular.module('webLog')
.directive('isActive', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
        	console.log(scope.$state);
        	if($(".dash").class === 'active'){
        		$(".reports").addClass('disabled');
        	}
        }
    };
});