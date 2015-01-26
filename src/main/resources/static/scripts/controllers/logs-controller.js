angular.module('webLog')
    .controller('LogsCtrl',['$scope', function($scope){
    $scope.message = "Logs Controller";
    $scope.errorReport={};
    }]);