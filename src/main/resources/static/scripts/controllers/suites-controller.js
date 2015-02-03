angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location','$state', function($scope, $location, $state){
		
		
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
		
		$scope.data = [
		               {x: 0, 120124: 4, 120314: 1, 120718: 21, 121224: 45, 130423: 8},
		               {x: 1, 120124: 8, 120314: 4, 120718: 23, 121224: 13, 130423: 5},
		               {x: 2, 120124: 15, 120314: 6, 120718: 13, 121224: 23, 130423: 7},
		               {x: 3, 120124: 16, 120314: 78, 120718: 5, 121224: 23, 130423: 3},
		               {x: 4, 120124: 23, 120314: 45, 120718: 56, 121224: 33, 130423: 12},
		               {x: 5, 120124: 42, 120314: 34, 120718: 24, 121224: 20, 130423: 15}
		             ];


		$scope.options = {
				  axes: {
				    x: {key: 'x', labelFunction: function(value) {return value;}, type: 'linear', ticks: 5},
				    y: {type: 'linear', ticks: 5},
				  },
				  series: [
				    {y: '120124', color: 'steelblue', thickness: '2px', drawDots: false, label: 'android'},
				    {y: '120314', color: 'brown', thickness: '2px',visible: true, drawDots: false,label: 'OSX'},
				    {y: '120718', color: 'lightsteelblue', thickness: '2px', visible: true, drawDots: false,label: 'windows'},
				    {y: '121224', color: 'red', thickness: '2px', visible: true, drawDots: false,label: 'iPhone'},
				    {y: '130423', color: 'green', thickness: '2px', visible: true, drawDots: false,label: 'Linux'}
				  ],
				  lineMode: 'cardinal',
				  tension: 0.7,
				  tooltip: {mode: 'scrubber', interpolate: 'true', formatter: function(x, y, series) {return series.label+" - " + y;}},
				  drawLegend: true,
				  drawDots: true,
				  interpolate: '',
				  columnsHGap: 5
				}
		
	}]);