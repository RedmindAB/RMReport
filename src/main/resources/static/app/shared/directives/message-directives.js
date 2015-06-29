angular.module('webLog')
    .directive('messageList', function(){
      return {
        restrict: 'E',
        scope: false,
        templateUrl: '/app/components/dashboard/template.html'
     };
    });