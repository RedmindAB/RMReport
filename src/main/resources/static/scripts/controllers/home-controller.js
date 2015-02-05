angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', '$state','$http', function($scope, $location, $state, $http){
    
    	$scope.homeData={};
    	$scope.homeOptions = {};
    	
    $scope.getSuiteByID = function(id) {
	}
    
    $scope.createChart = function(data, id) {
    };
    
    function getTotalFail(suiteRun) {
		return suiteRun.fail + suiteRun.error;
	}
    
    function getTotalPass(suiteRun) {
    	return suiteRun.pass;
	}
    
    }]);