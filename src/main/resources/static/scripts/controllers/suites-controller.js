angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location', function($scope, $location){

		$scope.openSuite;
		
		var statHolder= {};
		
		function readDataToCharts(suite){
			getDriverNames(suite);
			getTestStats(suite);
			
			$scope.allDriversLabels = statHolder.drivers;
			$scope.allTests = statHolder.testStat;
		};
		  
		function getDriverNames(suite) {
			statHolder.drivers = suite.drivers;
		};
		
		function getTestStats(suite){
			statHolder.testStat = [suite.errors, suite.failures];
			statHolder.testStat[2] = (suite.tests - (suite.errors + suite.failures));
		}
		
		$scope.goToSuiteRuns = function(){
			$location.path('/test-suite-runs');
		}
		
		$scope.$watch('suites', function(suites){
		    angular.forEach(suites, function(test, idx){
		      if (test.open) {
		    	  $scope.openSuite = test;
		    	  readDataToCharts($scope.openSuite);
		      }
		    })   
		}, true);
		  
	}]);