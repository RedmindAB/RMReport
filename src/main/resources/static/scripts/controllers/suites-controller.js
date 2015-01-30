angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location', function($scope, $location){

		$scope.suiteTests;
		$scope.runRightNow;
		
	    $scope.onClick = function (points, evt) {
	        $location.path('/test-suite-runs');
	    };
		
	    $scope.project = {
	    		labels: ["January", 
	    		         "February", 
						 "March", 
						 "April", 
						 "May", 
						 "June", 
						 "July"],
				series: ["",
				         'Mac OSX',
				         'Windows',
				         'Andriod',
				         'iPhone'],
				data: [
				       	[null, null, null, null, null, null, null],
				        [65, 59, 80, 81, 56, 55, 40],
						[28, 48, 40, 19, 86, 27, 90],
				        [34, 27, 63, 83, 23, 96, 45],
						[23, 69, 38, 78, 15, 89, 56],
						]
	    };
		
		$scope.testClasses = [ "se.redmind.rmtest.selenium.example.CreateLogsTest",
		                       "se.redmind.rmtest.selenium.example.CreateLogsTestSecond"
		                      ];
		
		
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
		
//		function getTestStats(suite){
//			statHolder.testStat = [suite.errors, suite.failures];
//			statHolder.testStat[2] = (suite.tests - (suite.errors + suite.failures));
//		}
		
	    $scope.goToTestCases = function(currentRun){
	    	console.log("in suite controller: " + currentRun);
	    	$scope.runRightNow = currentRun;
	    	$location.path('/test-case');
	    	console.log($scope.runRightNow);
	    };
		
		$scope.goToSuiteClasses = function(){
			$location.path('/suite-run-classes');
		}
		
		$scope.goToSuiteRuns = function(){
			$location.path('/test-suite-runs');
		}
		
//		$scope.$watch('suites', function(suites){
//		    angular.forEach(suites, function(test, idx){
//		      if (test.open) {
//		    	  $scope.openSuite = test;
//		    	  readDataToCharts($scope.openSuite);
//		      }
//		    })   
//		}, true);
		  
	}]);