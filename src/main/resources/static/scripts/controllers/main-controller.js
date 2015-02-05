angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', '$timeout','$state', function($scope, $http, $location, $timeout, $state){
    	
    $scope.$state = $state;
    $scope.methods = {};
    $scope.errorReport={};
    $scope.currentSuiteRun;
    $scope.mockSuites = []
    $scope.currentClasses = {};
    $scope.currentSuite = {};
    $scope.chartHomeConfig = {};
    $scope.allSuites = [1,2,3];
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
    $http.get('/api/suite/getsuites')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.allSuites = data;
    		console.log($scope.allSuites);
    		for (var i = 0; i < $scope.allSuites.length; i++) {
    			$scope.createHomeChartFromID($scope.allSuites[i].id);
			}
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
	for (var int = 0; int < 50; int++) {
		$scope.mockSuites.push("Suite Run " + int);
	};
    
    $scope.setCurrentSuite = function(suite){
		$scope.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+$scope.currentSuite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		$scope.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
    };
	
//    $scope.setCurrentSuiteRun = function(run){
//    	console.log("setting current suite run");
//    	$scope.currentSuiteRun = run;
//    	for(var testCase in $scope.currentSuiteRun.testcases){
//    		if ($scope.currentSuiteRun.testcases[testCase].failure) {
//    			$scope.currentSuiteRun.testcases[testCase].failure.message.replace(/at /g, '\nat ');
//			} else if($scope.currentSuiteRun.testcases[testCase].error) {
//				$scope.currentSuiteRun.testcases[testCase].error.message.replace(/at /g, '\nat ');
//			}
//    	}
//    	console.log($scope.currentSuiteRun);
//    }
    
    $scope.createHomeChartFromID = function(id) {
    	var requestObject = {
    			suiteid:id,
    			reslimit:50,		
    			classes:[],
    			testcases:[],
    			drivers:[]
    		}
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){ 
    		console.log(data);
    		$scope.createHomeChart(data, id);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
	};
	
    $scope.createHomeChart = function(data, id) {
    	
        var chartHomeConfigObject = {
        		  options: {
        			    chart: {
        			      type: "areaspline"
        			    },
        			    plotOptions: {
        			      series: {
        			        stacking: "normal"
        			      }
        			    }
        			  }, 
        		  series: [{
        			  data: [],
        		      id: "fail",
        		      name: "Fail",
        		      type: "column",
        		      color: "red",
        		      dashStyle: "Solid",
        		      connectNulls: false
        		  },{
        			  data: [],
      	   		      id: "pass",
      	   		      name: "Pass",
      	   		      type: "column",
      	   		      color: "green",
      	   		      dashStyle: "Solid",
      	   		      connectNulls: false
        		  }],
        		  title: {
        		     text: 'Pass Fail - Last 50 Runs'
        		  },
        		  loading: false,
        		  xAxis: {
        		  currentMax: data.length,
        		  title: {text: 'values'}
        		  },
        		  useHighStocks: false,
        		  size: {
        		   height: 400
        		  },
        		  //function (optional)
        		  func: function (chart) {
        		   //setup some logic for the chart
        		  }
        		};
    	
		for (var j = 0; j < data.length; j++) {
			chartHomeConfigObject.series[0].data.push(data[j].fail + data[j].error);
			chartHomeConfigObject.series[1].data.push(data[j].pass);
		}
		$scope.chartHomeConfig[id] = chartHomeConfigObject;
    };
    
    function getTotalFail(suiteRun) {
		return suiteRun.fail + suiteRun.error;
	}
    
    function getTotalPass(suiteRun) {
    	return suiteRun.pass;
	}
    
    $scope.getPanel = function(passed){
    	if(passed)
    		return 'panel panel-success bg-success success';
    	else
    		return 'panel panel-danger bg-danger';
    };
    
    $scope.getBG = function(passed){
    	if(passed)
    		return 'bg-success';
    	else
    		return 'bg-danger';
    };
    
    $scope.getBgCo = function(passed){
    	if(passed)
    		return '#DFF0D8';
    	else
    		return '#F2DEDE';
    };
    $scope.getCo = function(passed){
    	if(passed)
    		return '#3C763D';
    	else
    		return '#A94442';
    };
    $scope.getLogo = function(passed){
    	if(passed == 1)
    		return "img/logo1.png";
    	else if(passed == 2)
    		return 'img/logo1.jpg';
    	else
    		return 'img/logo3.jpg';    
    };
    
    $scope.goToHome= function(){
    	$state.transitionTo('home');
    }
    
    $scope.goToProject = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.goToClasses = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.goToMethods = function(){
    	$state.transitionTo('reports.methods');
    }
    
    $scope.goToCases = function(){
    	$state.transitionTo('reports.cases');
    };
    
    $scope.labels2 = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    $scope.data2 = [300, 500, 100];
    
    $scope.getCurrentState= function(state){
    	return $state.includes(state);
    }
    
    $scope.getGraphData = function(suiteID, classIDs, caseIDs, drivers){
    	$scope.requestObject =  $scope.getGraphDataObject(suiteID, classIDs, caseIDs, drivers);
    	$http.post('/api/stats/graphdata', $scope.requestObject)
    	.success(function(data, status, headers, config){ 
    		$scope.currentGraphData = data;
    		console.log(data);
    		chartLoaded = true;
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
    }
    
    $scope.getGraphDataObject = function(suiteID, classIDs, caseIDs, drivers){
    	var dataRequest = {};
    	
    		dataRequest.suiteid = suiteID;
    		if (!(isNaN($scope.amountOfRuns)) && !($scope.amountOfRuns == "")) {
				dataRequest.reslimit = parseInt($scope.amountOfRuns) +1;
			} else {
				dataRequest.reslimit = 50;
			}
    		
    		if (classIDs) {
				dataRequest.classes= classIDs;
			} else {
				dataRequest.classes=[];
			}
    		
    		if (caseIDs) {
				dataRequest.testcases = caseIDs;
			} else {
				dataRequest.testcases = [];
			}
    		
    		if (drivers) {
				dataRequest.drivers = drivers;
			} else {
				dataRequest.drivers = [];
			}
    	return dataRequest;
    };
    
}]);
