angular.module('webLog')
    .controller('HomeCtrl',['$scope', function($scope){
    $scope.message = "Welcome to the home page!";
    $scope.errorReport={};
    }]);