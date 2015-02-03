angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', '$state', function($scope, $location, $state){
    $scope.message = "Welcome to the home page!";
    
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
        $state.transitionTo('reports.classes');
    };
    
    $scope.goToProject = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
    $scope.projects = ['1','2','3']
    
    $scope.homeData = [
                   {x: 0, one: 4, two: 14},
                   {x: 1, one: 8, two: 34},
                   {x: 2, one: 15, two: 48},
                   {x: 3, one: 16, two: 147},
                   {x: 4, one: 23, two: 87},
                   {x: 5, one: 45, two: 23},
                   {x: 6, one: 25, two: 56},
                   {x: 7, one: 56, two: 67},
                   {x: 8, one: 12, two: 73},
                   {x: 9, one: 67, two: 25},
                   {x: 10, one: 12, two: 67},
                   {x: 11, one: 8, two: 87},
                   {x: 12, one: 3, two: 99}
                 ];
    
    
    
    $scope.homeOptions = {
    		  lineMode: "cardinal",
    		  tension: 0.5,
    		  axes: {x: {type: "", key: "x", ticks: $scope.homeData.length}, y: {type: "linear"}, y2: {type: "linear"}},
    		  tooltipMode: "dots",
    		  drawLegend: true,
    		  drawDots: true,
    		  stacks: [{axis: "y", series: ["failed", "passed"]}],
    		  series: [
    		    {
    		      y: "one",
    		      label: "Failed",
    		      type: "column",
    		      color: "#ff0000",
    		      axis: "y",
    		      visible: true,
    		      id: "failed"
    		    },
    		    {
    		      y: "two",
    		      label: "Passed",
    		      type: "column",
    		      color: "#19cf16",
    		      axis: "y",
    		      visible: true,
    		      id: "passed"
    		    }
    		  ],
    		  tooltip: {mode: "scrubber", formatter: function(x, y, series) {return y;}},
    		  columnsHGap: 5
    		};
    
    
    
    
    
    }]);