angular.module('webLog')
    .controller('GraphViewCtrl',['$scope', function($scope){
    $scope.message = "Some cool & interesting graphs!";
    $scope.errorReport={};
    }]);