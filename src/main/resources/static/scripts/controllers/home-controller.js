angular.module('webLog')
    .controller('HomeCtrl',['$scope', '$location', '$state','$http', function($scope, $location, $state, $http){
    
    	$scope.suites = [];
    	$scope.homeData=[];
    	$scope.homeOptions = [];
    	
    $scope.getSuiteByID = function(id) {
    	$http.get('/api/suite/data?suiteid=' + id)
    	.success(function(data, status, headers, config){ 
    		if(data){
    			$scope.createChart(data);
    		};
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
	}
    
    $scope.createChart = function(data) {
    	var dataObject = [];
    	for (var i = 0; i < data.length; i++) {
				dataObject.push({
										'x': i, 'pass': getTotalPass(data[i]), 'fail': getTotalFail(data[i])
									});
		}
    	$scope.homeData.push(dataObject);
    	$scope.homeOptions.push($scope.getHomeOptions(dataObject.length));
    };
    
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
    
    $scope.getHomeOptions = function(spacing){
    	return {
  		  lineMode: "cardinal",
		  tension: 0.5,
		  axes: {x: {type: "", key: "x", ticks: spacing}, y: {type: "linear", min: 0}, y2: {type: "linear"}},
		  tooltipMode: "dots",
		  drawLegend: false,
		  drawDots: true,
		  stacks: [{axis: "y", series: ["failed", "passed"]}],
		  series: [
		    {
		      y: "fail",
		      label: "Failed",
		      type: "column",
		      color: "#ff0000",
		      axis: "y",
		      visible: true,
		      id: "failed",
		      min: 1
		    },
		    {
		      y: "pass",
		      label: "Passed",
		      type: "column",
		      color: "#19cf16",
		      axis: "y",
		      visible: true,
		      id: "passed",
		      min: 1
		    }
		  ],
		  tooltip: {mode: "scrubber", formatter: function(x, y, series) {return y;}},
		  columnsHGap: 5
		};
    };
    
    $scope.homeOptionsSetup = {
    		  lineMode: "cardinal",
    		  tension: 0.5,
    		  axes: {x: {type: "", key: "x", ticks: 0}, y: {type: "linear", min: 0}, y2: {type: "linear"}},
    		  tooltipMode: "dots",
    		  drawLegend: false,
    		  drawDots: true,
    		  stacks: [{axis: "y", series: ["failed", "passed"]}],
    		  series: [
    		    {
    		      y: "fail",
    		      label: "Failed",
    		      type: "column",
    		      color: "#ff0000",
    		      axis: "y",
    		      visible: true,
    		      id: "failed",
    		      min: 1
    		    },
    		    {
    		      y: "pass",
    		      label: "Passed",
    		      type: "column",
    		      color: "#19cf16",
    		      axis: "y",
    		      visible: true,
    		      id: "passed",
    		      min: 1
    		    }
    		  ],
    		  tooltip: {mode: "scrubber", formatter: function(x, y, series) {return y;}},
    		  columnsHGap: 5
    		};
    
    
    $http.get('/api/suite/getsuites')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.suites = data;
    		for (var i = 0; i < $scope.suites.length; i++) {
				$scope.getSuiteByID($scope.suites[i].id);
			}
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    }]);