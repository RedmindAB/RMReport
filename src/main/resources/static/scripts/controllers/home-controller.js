angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', function($scope, $location){
    $scope.message = "Welcome to the home page!";
    
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
        $location.path('/project');
    };
    
    $scope.imagePaths = ['img/logo1.png', 'img/logo2.jpg', 'img/logo3.jpg'];
    
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
			       ]
			}
    				
    };
      
    
    }]);