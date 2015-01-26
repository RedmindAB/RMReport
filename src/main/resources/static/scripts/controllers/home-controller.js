angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$document', '$rootScope', function($scope, $document, $rootScope){
    $scope.message = "Welcome to the home page!";
    
    
    var data = {
    	    labels: ["January", "February", "March", "April", "May", "June", "July"],
    	    datasets: [
    	        {
    	            label: "My First dataset",
    	            fillColor: "rgba(220,220,220,0.2)",
    	            strokeColor: "rgba(220,220,220,1)",
    	            pointColor: "rgba(220,220,220,1)",
    	            pointStrokeColor: "#fff",
    	            pointHighlightFill: "#fff",
    	            pointHighlightStroke: "rgba(220,220,220,1)",
    	            data: [65, 59, 80, 81, 56, 55, 40]
    	        },
    	        {
    	            label: "My Second dataset",
    	            fillColor: "rgba(151,187,205,0.2)",
    	            strokeColor: "rgba(151,187,205,1)",
    	            pointColor: "rgba(151,187,205,1)",
    	            pointStrokeColor: "#fff",
    	            pointHighlightFill: "#fff",
    	            pointHighlightStroke: "rgba(151,187,205,1)",
    	            data: [28, 48, 40, 19, 86, 27, 90]
    	        }
    	    ]
    	};
    
    var buyerData = {
    		labels : ["January","February","March","April","May","June"],
    		datasets : [
    			{
    				fillColor : "rgba(172,194,132,0.4)",
    				strokeColor : "#ACC26D",
    				pointColor : "#fff",
    				pointStrokeColor : "#9DB86D",
    				data : [203,156,99,251,305,247]
    			}
    		]
    	}
    
    $rootScope.$on('$viewContentLoaded', function (event) {
    	if(!document.getElementById('buyers')){
    		return;
    	}
        var buyers = document.getElementById('buyers').getContext('2d');
        new Chart(buyers).Line(buyerData);
})
    }]);