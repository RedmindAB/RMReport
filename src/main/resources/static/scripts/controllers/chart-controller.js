angular.module('webLog')
	.controller('ChartCtrl',[ '$scope', function($scope){

		$scope.openSuite;
		
		$scope.labels = ["Fire Fox", "Chrome"];
		$scope.data = [100, 5];
		
		$scope.isOpenSuite = function(testName){
			if ($scope.openSuite) {
				return $scope.openSuite.name === testName;
				console.log($scope.opensuite);
			}
		};
		
		  $scope.$watch('suites', function(suites){
			    angular.forEach(suites, function(test, idx){
			      if (test.open) {
			    	  $scope.openSuite = test;
			        console.log($scope.openSuite.name + " is open");
			      }
			    })   
			  }, true);
		  
	}]);