angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location','$state', function($scope, $location, $state){
		
		$scope.dataStuff = 100;
		$scope.changeStuff = function(){
			$scope.data[0].hej = 100;
		}
		
		
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
		               {x: 0, 120124: 56, 120314: 87, 120718: 99, 121224: 45, hej: 8},
		               {x: 1, 120124: 23, 120314: 75, 120718: 78, 121224: 13, hej: 5},
		               {x: 2, 120124: 15, 120314: 63, 120718: 45, 121224: 23, hej: 7},
		               {x: 3, 120124: 16, 120314: 78, 120718: 38, 121224: 23, hej: 3},
		               {x: 4, 120124: 23, 120314: 45, 120718: 26, 121224: 33, hej: 12},
		               {x: 5, 120124: 3, 120314: 34, 120718: 14, 121224: 20, hej: 15}
		             ];


		$scope.options = {
				  axes: {
				    x: {key: 'x', labelFunction: function(value) {return value;}, type: 'linear', ticks: 5},
				    y: {type: 'linear', ticks: 5},
				  },
				  series: [
				    {y: '120124', type: 'area', color: 'steelblue', thickness: '2px', drawDots: false, label: 'android'},
				    {y: '120314', type: 'area',color: 'brown', thickness: '2px',visible: true, drawDots: false,label: 'OSX'},
				    {y: '120718', type: 'area',color: 'lightsteelblue', thickness: '2px', visible: true, drawDots: false,label: 'windows'},
				    {y: '121224', type: 'area',color: 'red', thickness: '2px', visible: true, drawDots: false,label: 'iPhone'},
				    {y: 'hej', type: 'area',color: 'green', thickness: '2px', visible: true, drawDots: false,label: 'Linux'}
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