angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location','$state', function($scope, $location, $state){

		$scope.suiteTests;
		$scope.runRightNow;
		
//	    $scope.onClick = function (points, evt) {
//	    	console.log(evt);
//	    };
//		
//	    $scope.project = {
//	    		labels: ["1", 
//	    		         "2", 
//						 "3", 
//						 "4", 
//						 "5", 
//						 "6",
//						 "7"],
//				series: ['Mac OSX',
//				         'Windows',
//				         'Andriod',
//				         'iPhone',
//				         'Windows Phone',
//				         'four',
//				         'five',
//				         ],
//				data: [
//				       	[100, 70, 50, 30, 20, 10, 3],
//				        [65, 59, 80, 81, 56, 55, 40],
//						[28, 48, 40, 19, 86, 27, 90],
//				        [34, 27, 63, 83, 23, 96, 45],
//						[23, 69, 38, 78, 15, 89, 56],
//						[2, 2, 2, 2, 3, 2, 2],
//						[3, 3, 3, 3, 4, 3, 3],
//						]
//	    };
//		
//		$scope.testClasses = [ "se.redmind.rmtest.selenium.example.CreateLogsTest",
//		                       "se.redmind.rmtest.selenium.example.CreateLogsTestSecond"
//		                      ];
		
		
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
		
	    $scope.goToTestCases = function(){
	    	console.log("going to cases");
	    	$state.transitionTo('reports.methods');
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
		
		$scope.data = [
		               {x: 0, value: 4, otherValue: 1},
		               {x: 1, value: 8, otherValue: 4},
		               {x: 2, value: 15, otherValue: 6},
		               {x: 3, value: 16, otherValue: 78},
		               {x: 4, value: 23, otherValue: 45},
		               {x: 5, value: 42, otherValue: 34}
		             ];


		$scope.options = {
				  axes: {
				    x: {key: 'x', labelFunction: function(value) {return value;}, type: 'linear', ticks: 2},
				    y: {type: 'linear', ticks: 5},
				  },
				  series: [
				    {y: 'value', color: 'steelblue', thickness: '2px', type: 'line', drawDots: false, label: 'Pouet'},
				    {y: 'otherValue', color: 'lightsteelblue', visible: true, drawDots: false,}
				  ],
				  lineMode: 'positive',
				  tension: 0.7,
				  tooltip: {mode: 'none', formatter: function(x, y, series) {return 'pouet';}},
				  drawLegend: true,
				  drawDots: true,
				  interpolate: 'linear',
				  columnsHGap: 5
				}
		
	}]);