angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$document', '$rootScope', '$timeout', function($scope, $document, $rootScope, $timeout){
    $scope.message = "Welcome to the home page!";
    
    
    $scope.labels = ["Failed tests on Chrome", "Failed tests on Fire Fox", "Failed tests on Safari"];
    $scope.data = [300, 500, 100];
    
    }]);