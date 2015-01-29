angular.module('webLog')
    .controller('ProjectCtrl',['$scope', '$location', function($scope, $location){
    $scope.message = "Welcome to the home page!";
    
    $scope.boolToClick = false;
    
    $scope.onClick = function (points, evt) {
//        console.log(points, evt);
        $location.path('/test-suite-runs');
    };
    
    var projectData = {
    		labels: ["January", 
    		         "February", 
					 "March", 
					 "April", 
					 "May", 
					 "June", 
					 "July"],
			series: ['mac OSX', 
			         'Windows',
			         'Andriod',
			         'iPhone'],
			data: [
			        [65, 59, 80, 81, 56, 55, 40],
					[28, 48, 40, 19, 86, 27, 90],
			        [34, 27, 63, 83, 23, 96, 45],
					[23, 69, 38, 78, 15, 89, 56]
					]
    };
    
    $scope.project = {
    		labels: ["January", 
    		         "February", 
					 "March", 
					 "April", 
					 "May", 
					 "June", 
					 "July"],
			series: ['mac OSX', 
			         'Windows',
			         'Andriod',
			         'iPhone'],
			data: [
			        [65, 59, 80, 81, 56, 55, 40],
					[28, 48, 40, 19, 86, 27, 90],
			        [34, 27, 63, 83, 23, 96, 45],
					[23, 69, 38, 78, 15, 89, 56]
					]
    };
    
    $scope.toggleInfo = function(index){
    	console.log(index);
    	
    	if($scope.project.data[index] != projectData.data[index]){
    		$scope.project.data[index] = projectData.data[index];
    	} else {
    		delete $scope.project.data[index];
    		delete $scope.project.series[index];
    	}
    	
    }
    
    
}]);