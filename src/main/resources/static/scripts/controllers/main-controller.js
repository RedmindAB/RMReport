angular.module('webLog')
    .controller('MainCtrl',['$scope', '$rootScope', '$http','$location', '$timeout','$state', 'CurrentSuite', 'Charts', 'Utilities', function($scope, $rootScope,$http, $location, $timeout, $state, CurrentSuite, Charts, Utilities){
    	
    $scope.CurrentSuite = CurrentSuite;
    $scope.Utilities = Utilities;
    $scope.suiteSkeleton = [];	
    $scope.$state = $state;
    $scope.methods = {};
    $scope.errorReport={};
    $scope.chartHomeConfig = {};
    $scope.chartMainConfig = {};
    $scope.allSuites = [];
    $scope.chosen={
    		classes: [],
    		methods: [],
    		drivers: []
    };
    
    $scope.mockDriverArray = ["Andriod", "Chrome", "OSX", "Windows"];
    
    
    $scope.resetFilterField = function(){
    	Utilities.searchField = "";
    }
    
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
    
    $scope.goToDrivers = function(){
    	$state.transitionTo('reports.drivers');
    };
    
    $scope.goToCases = function(){
    	$state.transitionTo('reports.cases');
    };
    
	$scope.getMethods = function(testClass){
		CurrentSuite.currentClass=testClass;
		CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
	}
	
	$scope.clearChosen = function(){
		//remove classes checkbox
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					delete CurrentSuite.currentSuite[i].chosen
				}
			}
		}
		
		//remove method checkbox
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
		
		//remove driver checkbox
		if (CurrentSuite.currentDrivers) {
			for (var i = 0; i < CurrentSuite.currentDrivers.length; i++) {
				if (CurrentSuite.currentDrivers[i].chosen) {
					delete CurrentSuite.currentDrivers[i].chosen
				}
			}
		}
	}
	
	$scope.getChosen = function(){
		var chosen = {
				classes: [],
				methods: [],
				drivers: []
		};
		
		//add classes to send
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					chosen.classes.push(CurrentSuite.currentSuite[i].id);
				}
			}
		}
		
		//add methods to send
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen) {
					chosen.methods.push(CurrentSuite.currentClass.testcases[i].id);
				}
			}
		}
		
		//add drivers to send
		if (CurrentSuite.currentDrivers) {
			for (var i = 0; i < CurrentSuite.currentDrivers.length; i++) {
				if (CurrentSuite.currentDrivers[i].chosen) {
					chosen.drivers.push(CurrentSuite.currentDrivers[i].driver);
				}
			}
		}
		return chosen;
	}
	
    // HTTP -----------------------------------------------------------------------------------------------------------
    
	$scope.getCases = function(driver){
		CurrentSuite.currentDriver = driver;
		$http.get("/api/testcase/caserunsbydriver?id=" + CurrentSuite.currentMethod.id + "&driver='" + driver.driver + "'")
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentDriverRuns = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getSuiteSkeleton = function(suite){
		CurrentSuite.currentSuiteInfo = suite;
	    $http.get('/api/suite/latestbyid?suiteid=' + suite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSuite = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getDrivers = function(method){
		CurrentSuite.currentMethod = method;
	    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentDrivers = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.setCurrentSuite = function(suite){
		CurrentSuite.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+CurrentSuite.currentSuite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	};
	
   $scope.loadMainChart = function(suiteID) {
    	var requestObject = $scope.getGraphDataObject(suiteID)
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){
    		$scope.createMainChart(data);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
   };
    
    $scope.createHomeChartFromID = function(id) {
    	var requestObject = {
    			suiteid:id,
    			reslimit:51,		
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
	
    $scope.getMainGraphData = function(suiteID){
    	$scope.requestObject =  $scope.getGraphDataObject(suiteID);
    	$http.post('/api/stats/graphdata', $scope.requestObject)
    	.success(function(data, status, headers, config){ 
    		$scope.currentGraphData = data;
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
    }
    
    $scope.getGraphDataObject = function(suiteID){
    	var chosen = $scope.getChosen();
    	var dataRequest = {};
    	
    	var reslimit = Utilities.amountField;
    	
		dataRequest.suiteid = suiteID;
		
		if (!(isNaN(reslimit)) && !(reslimit === "")) {
			dataRequest.reslimit = parseInt(reslimit) +1;
		} else {
			dataRequest.reslimit = 51;
		}
		
		dataRequest.classes=chosen.classes;
		dataRequest.testcases = chosen.methods;
		dataRequest.drivers= chosen.drivers;
		console.log(dataRequest);
    	return dataRequest;
    };
    
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
    $scope.createMainChart = function(data){
    	var timestamp = [];
    	for (var index = 0; index < data.length; index++) {
			timestamp.push(data[index].timestamp);
		}
    	
    	var chartMainConfigObject = {
    			  options: {
    				    chart: {
    				      type: "areaspline",
    				      backgroundColor: '#ecf0f1',
    			            events: {
    			                selection: function (event) {
    			                	console.log(event);
    			                    var text,
    			                        label;
    			                    if (event.xAxis) {
    			                        text = 'min: ' + Highcharts.numberFormat(event.xAxis[0].min, 2) + ', max: ' + Highcharts.numberFormat(event.xAxis[0].max, 2);
    			                    } else {
    			                        text = 'Selection reset';
    			                    }
    			                    label = this.renderer.label(text, 100, 120)
    			                        .attr({
    			                            fill: Highcharts.getOptions().colors[0],
    			                            padding: 10,
    			                            r: 5,
    			                            zIndex: 8
    			                        })
    			                        .css({
    			                            color: '#FFFFFF'
    			                        })
    			                        .add();

    			                    setTimeout(function () {
    			                        label.fadeOut();
    			                    }, 1000);
    			                }
    			            },
    			            zoomType: 'x'
    				    },
			            tooltip: {
			            },
    				    plotOptions: {
    				      series: {
    				    	  stacking: "percent",
    		                    cursor: 'pointer',
    		                    point: {
    		                        events: {
    		                            click: function (e) {
    		                            	console.log(this.category);
    		                            }
    		                        }
    		                    },
    		                    marker: {
    		                        lineWidth: 1
    		                    }
    				      }
    				    }
    				  },
    				  xAxis:{
    					  categories: timestamp,
    					  minTickInterval: 5,
    					  labels:{
    						  rotation: 45
    					  }
    				  },
						yAxis: {
							title: {
								text: 'Percentage'
							},
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
    				    text: "Pass / Fail for the last " + data.length + " results" 
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
			chartHomeConfigObject.series[0].data.push(data[j].pass);
			chartHomeConfigObject.series[1].data.push(data[j].fail + data[j].error);
		}
		$scope.chartHomeConfig[id] = chartHomeConfigObject;
    };
    
    //  CSS -----------------------------------------------------------------------------------------------------------------
    
    $scope.getCurrentState= function(state){
    	return $state.includes(state);
    }
    
    $scope.showClassLink = function(page){
    	switch (page) {
		case "classes":
			return $scope.getCurrentState('reports.classes') || 
			$scope.getCurrentState('reports.methods') || 
			$scope.getCurrentState('reports.drivers') || 
			$scope.getCurrentState('reports.cases');
			break;
		case "methods":
			return $scope.getCurrentState('reports.methods')  || 
			$scope.getCurrentState('reports.drivers') || 
			$scope.getCurrentState('reports.cases');
			break;
		case "drivers":
			return $scope.getCurrentState('reports.drivers') || 
			$scope.getCurrentState('reports.cases');
			break;
		case "cases":
			return $scope.getCurrentState('reports.cases');
			break;

		default:
			return false;
			break;
		}
    }
    
    $scope.getPanel = function(passed){
    	if(passed === "failure" || passed === "error")
    		return 'panel panel-danger bg-danger';
    	else
    		return 'panel panel-success bg-success success';
    };
    
    $scope.getBG = function(passed){
    	if(passed === "failure" || passed === "error")
    		return 'bg-danger';
    	else
    		return 'bg-success';
    };
    
    $scope.getBgCo = function(passed){
    	if(passed === "failure" || passed === "error")
    		return '#F2DEDE';
    	else
    		return '#DFF0D8';
    };
    $scope.getCo = function(passed){
    	if(passed === "failure" || passed === "error")
    		return '#A94442';
    	else
    		return '#3C763D';
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
