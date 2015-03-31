angular.module('webLog')
.directive('checkImage', function($http) {
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
});