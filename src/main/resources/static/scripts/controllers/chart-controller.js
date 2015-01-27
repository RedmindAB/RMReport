angular.module('webLog')
	.controller('ChartCtrl',[ '$scope', function($scope){

		$scope.openSuite;
		$scope.labels = ["Fire Fox", "Chrome"];
		$scope.data = [0, 0];
		
		var testData = {
				fireFox: 0,
				chrome: 0
		};
		
		function getDrivers() {
			
			for(var key in $scope.openSuite.testcases){
				if ($scope.openSuite.testcases[key].driverName === "OSX firefox") {
					testData.fireFox =  testData.fireFox + 1;
				} else if($scope.openSuite.testcases[key].driverName === "OSX chrome"){
					testData.chrome = testData.chrome + 1;
				}
			}
			$scope.data = [testData.fireFox, testData.chrome];
		};
		
		$scope.isOpenSuite = function(testName){
			if ($scope.openSuite) {
				return $scope.openSuite.name === testName;
			}
		};
		
		  $scope.$watch('suites', function(suites){
			    angular.forEach(suites, function(test, idx){
			      if (test.open) {
			    	  testData = {
			  				fireFox: 0,
			  				chrome: 0
			  		};
			    	  $scope.openSuite = test;
			    	  getDrivers();
			        console.log($scope.openSuite);
			      }
			    })   
			  }, true);
		  
	}]);