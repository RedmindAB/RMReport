angular.module('webLog')
	.controller('ChartCtrl',[ '$scope', function($scope){

		$scope.openSuite;
		
		$scope.allDriversLabels = ["Fire Fox", "Chrome", "Safari"];
		$scope.allDrivers = [15, 7, 13];
		
		$scope.allTestsLabels = ["Error", "Failure", "Passed"];
		$scope.allTests = [20, 10, 32];
		
		$scope.allDriversFailsLabels = ["Fire Fox", "Chrome", "Safari"];
		$scope.allDriversFails= [12, 3, 8];
		
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