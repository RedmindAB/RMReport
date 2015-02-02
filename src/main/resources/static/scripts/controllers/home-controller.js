angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', '$state', function($scope, $location, $state){
    $scope.message = "Welcome to the home page!";
    
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
        $state.transitionTo('reports.classes');
    };
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
    $scope.projects = {
    	projectOne: {
			labels: ['AftonBladet 1', 'AftonBladet 2', 
			         'AftonBladet 3', 'AftonBladet 4', 
			         'TestBuild 5'],
			series: ['Passed', 
			         'Failed'],
			data: [
			       [65, 59, 80, 81, 56],
			       [28, 48, 40, 19, 86]
			       ],
			       colours: [
			                  {
			                    fillColor: "rgba(0, 151, 117,0.7)",
			                    strokeColor: "rgba(0, 151, 117,0)",
			                    pointColor: "rgba(148,159,177,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(148,159,177,0.8)"
			                  },
			                  {
			                    fillColor: "rgba(200,16,46,0.7)",
			                    strokeColor: "rgba(200,16,46,0)",
			                    pointColor: "rgba(77,83,96,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(77,83,96,1)"
			                  }
			                ]
    				},
    	projectTwo: {
			labels: ['AftonBladetTV 1', 'AftonBladetTV 2', 
			         'AftonBladetTV 3', 'AftonBladetTV 4', 
			         'TestBuild 5'],
			series: ['Passed', 
			         'Failed'],
			         
			data: [
			       [23, 67, 88, 25, 96],
			       [38, 37, 78, 56, 46]
			       ],
			       colours: [
			                  { // grey
			                    fillColor: "rgba(0, 151, 117,0.7)",
			                    strokeColor: "rgba(0, 151, 117,0)",
			                    pointColor: "rgba(148,159,177,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(148,159,177,0.8)"
			                  },
			                  { // dark grey
			                    fillColor: "rgba(200,16,46,0.7)",
			                    strokeColor: "rgba(200,16,46,0)",
			                    pointColor: "rgba(77,83,96,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(77,83,96,1)"
			                  }
			                ]
			},
			
    	projectThree: {
			labels: ['AftonBladetMobil 1', 'AftonBladetMobil2', 
			         'AftonBladetMobil 3', 'AftonBladetMobil 4', 
			         'AftonBladetMobil 5'],
			series: ['Passed', 
			         'Failed'],
			data: [
			       [63, 65, 35, 98, 24],
			       [28, 48, 12, 98, 84]
			       ],
			       colours: [
			                  { // grey
			                    fillColor: "rgba(0, 151, 117,0.7)",
			                    strokeColor: "rgba(0, 151, 117,0)",
			                    pointColor: "rgba(148,159,177,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(148,159,177,0.8)"
			                  },
			                  { // dark grey
			                    fillColor: "rgba(200,16,46,0.7)",
			                    strokeColor: "rgba(200,16,46,0)",
			                    pointColor: "rgba(77,83,96,1)",
			                    pointStrokeColor: "#fff",
			                    pointHighlightFill: "#fff",
			                    pointHighlightStroke: "rgba(77,83,96,1)"
			                  }
			                ]
			}
    				
    };
      
    
    }]);