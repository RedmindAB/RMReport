angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', '$state','$http', function($scope, $location, $state, $http){
    
    	$scope.suites = [];
    	
        $scope.homeData = [];
    	
    $http.get('/api/suite/data?suiteid=1')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.suites = data;
    		createChart($scope.suites);
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    function createChart(suite) {
    	for (var i = 0; i < suite.length ; i++) {
    		var dataObject = {}
				dataObject["x"] = i;
				dataObject['fail'] = getTotalFail(suite[i]);
				dataObject['pass'] = getTotalPass(suite[i]);
				$scope.homeData.push(dataObject);
		}
    	console.log($scope.homeData);
	}
    
    function getTotalFail(suiteRun) {
		return suiteRun.fail + suiteRun.error;
	}
    
    
    function getTotalPass(suiteRun) {
    	return suiteRun.pass;
	}
    
    $scope.goToProject = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
    $scope.projects = ['1','2','3']
    
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
    		      y: "passed",
    		      label: "Failed",
    		      type: "column",
    		      color: "#ff0000",
    		      axis: "y",
    		      visible: true,
    		      id: "failed"
    		    },
    		    {
    		      y: "fail",
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