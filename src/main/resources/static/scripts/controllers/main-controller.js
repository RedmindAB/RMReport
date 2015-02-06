angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', '$timeout','$state', function($scope, $http, $location, $timeout, $state){
    	
    $scope.$state = $state;
    $scope.methods = {};
    $scope.errorReport={};
    $scope.currentSuiteRun;
    $scope.mockSuites = []
    $scope.currentClasses = {};
    $scope.currentClass= [];
    $scope.currentSuiteID;
    $scope.chartHomeConfig = {};
    $scope.chartMainConfig = {};
    $scope.allSuites = [];
    $scope.chosen={
    		classes: [],
    		methods: [],
    		drivers: []
    };
    $scope.amountOfRuns = "";
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
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
    
	for (var int = 0; int < 50; int++) {
		$scope.mockSuites.push("Suite Run " + int);
	};
    
	$scope.getMethods = function(testClass){
		$scope.currentClass=testClass;
	    $http.get('/api/method/getmethods?classid=' + testClass.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		if ($scope.methods != data) {
	    			$scope.methods = data;
				}
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.clearChosen = function(){
		for (var i = 0; i < $scope.methods.length; i++) {
			if ($scope.methods[i].chosen) {
				delete $scope.methods[i].chosen;
			}
		}
		for (var i = 0; i < $scope.currentClasses.length; i++) {
			if ($scope.currentClasses[i].chosen) {
				delete $scope.currentClasses[i].chosen
			}
		}
	}
	
	$scope.getChosen = function(){
		var chosen = {
				classes: [],
				methods: []
		};
		for (var i = 0; i < $scope.methods.length; i++) {
			if ($scope.methods[i].chosen) {
				chosen.methods.push($scope.methods[i].id);
			}
		}
		for (var i = 0; i < $scope.currentClasses.length; i++) {
			if ($scope.currentClasses[i].chosen) {
				chosen.classes.push($scope.currentClasses[i].id);
			}
		}
		return chosen;
	}
	
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
    		$scope.createHomeChart(data, id);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
	};
	
	   $scope.loadMainChart = function(suiteID, reslimit, drivers) {
	    	var requestObject = $scope.getGraphDataObject(suiteID, reslimit, drivers)
	    	$http.post('/api/stats/graphdata', requestObject)
	    	.success(function(data, status, headers, config){
	    		$scope.createMainChart(data);
	    	}).error(function(data, status, headers, config){
	    		console.log(data);
	    	});
		};
	
    $http.get('/api/suite/getsuites')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.allSuites = data;
    		for (var i = 0; i < $scope.allSuites.length; i++) {
    			$scope.createHomeChartFromID($scope.allSuites[i].id);
			}
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
	
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
    $scope.createMainChart = function(data){
    	var chartMainConfigObject = {
    			  options: {
    				    chart: {
    				      type: "areaspline",
    				      backgroundColor: '#ecf0f1'
    				    },
    				    plotOptions: {
    				      series: {
    				    	  stacking: "percent"
    				      }
    				    }
    				  },
    				  series: [
    				    {
    				      data: [],
    				      name:'Pass',
    				      color: '#2ecc71',
    				      id: "mainPass"
    				    },
    				    {
    				      data: [],
    				      name: 'Fail',
    				      color: '#e74e3e',
    				      id: "mainFail"
    				    }
    				  ],
    				  title: {
    				    text: "Main Graph"
    				  },
    				  credits: {
    				    enabled: false
    				  },
    				  loading: false,
    				  size: {},
    				  useHighStocks: false,
    				};
    	
		for (var j = 0; j < data.length; j++) {
			chartMainConfigObject.series[1].data.push(data[j].fail + data[j].error);
			chartMainConfigObject.series[0].data.push(data[j].pass);
		}
		$scope.chartMainConfig = chartMainConfigObject;
    };
    
    $scope.createHomeChart = function(data, id) {
    	
        var chartHomeConfigObject = {
        		  options: {
        			    chart: {
        			      type: "areaspline",
        			      backgroundColor: '#ecf0f1'
        			    },
        			    plotOptions: {
        			      series: {
        			        stacking: "normal"
        			      }
        			    }
        			  }, 
        		  series: [{
        			  data: [],
        		      id: "pass",
        		      name: "Pass",
        		      type: "column",
        		      color: "green",
        		      dashStyle: "Solid",
        		      connectNulls: false
        		  },{
        			  data: [],
      	   		      id: "fail",
      	   		      name: "Fail",
      	   		      type: "column",
      	   		      color: "red",
      	   		      dashStyle: "Solid",
      	   		      connectNulls: false
        		  }],
        		  title: {
        		     text: 'Pass / Fail - Last 50 Runs'
        		  },
        		  loading: false,
        		  xAxis: {
        		  title: {text: 'Tests run'}
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
			console.log(data[j]);
			chartHomeConfigObject.series[0].data.push(data[j].pass);
			chartHomeConfigObject.series[1].data.push(data[j].fail + data[j].error);
		}
		$scope.chartHomeConfig[id] = chartHomeConfigObject;
    };
    
    function getTotalFail(suiteRun) {
		return suiteRun.fail + suiteRun.error;
	}
    
    function getTotalPass(suiteRun) {
    	return suiteRun.pass;
	}
    
    $scope.getMainGraphData = function(suiteID, classIDs, caseIDs, drivers){
    	$scope.requestObject =  $scope.getGraphDataObject(suiteID, classIDs, caseIDs, drivers);
    	$http.post('/api/stats/graphdata', $scope.requestObject)
    	.success(function(data, status, headers, config){ 
    		$scope.currentGraphData = data;
    		
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
    }
    
    $scope.getGraphDataObject = function(suiteID, reslimit, drivers){
    	console.log("methods");
    	console.log($scope.methods);
    	console.log("classes");
    	console.log($scope.currentClasses);
    	var chosen = $scope.getChosen();
    	var dataRequest = {};
    		dataRequest.suiteid = suiteID;
    		if (!(isNaN(reslimit)) && !(reslimit == "")) {
				dataRequest.reslimit = parseInt(reslimit) +1;
			} else {
				dataRequest.reslimit = 50;
			}
    		
			dataRequest.classes=chosen.classes;
			dataRequest.testcases = chosen.methods;
    		
    		if (drivers) {
				dataRequest.drivers = drivers;
			} else {
				dataRequest.drivers = [];
			}
    		console.log("request");
    		console.log(dataRequest);
    	return dataRequest;
    };
    
    //  CSS -----------------------------------------------------------------------------------------------------------------
    
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
    
}]);
