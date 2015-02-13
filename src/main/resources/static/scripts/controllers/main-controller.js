angular.module('webLog')
    .controller('MainCtrl',['$scope', '$rootScope', '$http','$location', '$timeout','$state', 'CurrentSuite', 'Charts', 'Utilities', function($scope, $rootScope,$http, $location, $timeout, $state, CurrentSuite, Charts, Utilities){
    	
    $scope.Charts = Charts;
    $scope.CurrentSuite = CurrentSuite;
    $scope.Utilities = Utilities;
    $scope.suiteSkeleton = [];	
    $scope.$state = $state;
    $scope.methods = {};
    $scope.errorReport={};
    $scope.chartHomeConfig = {};
    $scope.chartMainConfig = {};
    $scope.allSuites = [];
    $scope.mainGraphToggle = false;
    
    $scope.mockDriverArray = ["Andriod", "iOS", "OSX", "Windows"];
    $scope.mockOsObject = [];
    $scope.mockOsObject.push({
    	id: 1,
    	os: "Andriod",
    	devices: ["Samsung", "HTC"],
    	versions: ["4.4", "4.5.4"],
    	browsers: ["Chrome", "Fire fox"]
    });
    
    $scope.mockOsObject.push({
    	id: 2,
    	os: "OSX",
    	devices: ["Mac"],
    	versions: ["5", "6"],
    	browsers: ["Chrome", "Fire fox", "Safari"]
    });
    
    $scope.mockOsObject.push({
    	id: 3,
    	os: "iOS",
    	devices: ["iPhone 6", "iPhone 5"],
    	versions: ["", "4.5.4"],
    	browsers: ["Chrome", "Fire fox"]
    });
    
    $scope.resetFilterField = function(){
    	Utilities.searchField = "";
    }
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
    $scope.getToggleButtonText = function(){
    	if ($scope.mainGraphToggle) {
			return "Show Pass/Fail"
		} else {
			return "Show Run Time";
		}
    }
    
	$scope.getMethods = function(testClass){
		$scope.clearOtherChosen(testClass);
		CurrentSuite.currentClass=testClass;
		CurrentSuite.currentMethods = CurrentSuite.currentClass.testcases;
	}
	
	$scope.clearOtherChosen = function(item){
		//remove classes checkbox
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen && CurrentSuite.currentSuite[i] != item) {
					delete CurrentSuite.currentSuite[i].chosen;
				}
			}
		}
		
		//remove method checkbox
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen && CurrentSuite.currentClass.testcases[i] != item) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
		
		//remove driver checkbox
		if (CurrentSuite.currentDrivers) {
			for (var i = 0; i < CurrentSuite.currentDrivers.length; i++) {
				if (CurrentSuite.currentDrivers[i].chosen && CurrentSuite.currentDrivers[i] != item) {
					delete CurrentSuite.currentDrivers[i].chosen;
				}
			}
		}
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
	    		console.log(data);
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
	   console.log(suiteID);
    	var requestObject = $scope.getGraphDataObject(suiteID)
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){
    		CurrentSuite.currentTimeStamp = data[0].timestamp;
    		console.log(CurrentSuite.currentTimeStamp);
    		$scope.createMainChart(data);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
   };
   
   $scope.loadNewTimeStamp = function(timeStamp){
	   console.log(timeStamp);
   }
    
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
    	return dataRequest;
    };
    
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
    $scope.toggleMainChart = function(){
    	if ($scope.chartMainConfig === Charts.mainChart) {
    		$scope.chartMainConfig = Charts.mainTime;
    		$scope.mainGraphToggle = true;
		} else {
			$scope.chartMainConfig = Charts.mainChart;
			$scope.mainGraphToggle = false;
		}
    }
    
    $scope.createMainChart = function(data){
    	CurrentSuite.currentTimeStampArray = [];
    	for (var index = 0; index < data.length; index++) {
			CurrentSuite.currentTimeStampArray.push(data[index].timestamp);
			
		}
    	Charts.mainChart.series[0].data = [];
    	Charts.mainChart.series[1].data = [];
    	
		for (var j = 0; j < data.length; j++) {
			Charts.mainChart.series[0].data.push(data[j].pass);
			Charts.mainChart.series[1].data.push(data[j].fail + data[j].error);
		}
		Charts.mainChart.xAxis.categories = CurrentSuite.currentTimeStampArray;
		Charts.mainChart.title.text = "Pass / Fail for the last " + data.length + " results";
		Charts.mainChart.options.plotOptions.series.point.events.click = function (e) {
			$scope.loadNewTimeStamp(this.category);
        };
		$scope.chartMainConfig = Charts.mainChart;
    };
    
    $scope.createHomeChart = function(data, id) {
    	var timeStamps = [];
    	for (var index = 0; index < data.length; index++) {
			timeStamps.push(data[index].timestamp);
		}
    	
        var chartHomeConfigObject = {
				options : {
					chart : {
						type : "areaspline",
						backgroundColor : '#ecf0f1'
					},
					plotOptions : {
						series : {
							stacking : "normal"
						}
					}
				},
				series : [ {
					data : [],
					id : "pass",
					name : "Pass",
					type : "column",
					color : "green",
					dashStyle : "Solid",
					connectNulls : false
				}, {
					data : [],
					id : "fail",
					name : "Fail",
					type : "column",
					color : '#FF0000',
					dashStyle : "Solid",
					connectNulls : false
				} ],
				xAxis : {
					minTickInterval : 5,
					labels : {
						rotation : 45
					}
				},	
				yAxis : {
					title : {
						text : 'Number of tests'
					},
				},
				useHighStocks : false,
				size : {
					height : 400
				},
				// function (optional)
				func : function(chart) {
					// setup some logic for the chart
				}
			}
    	
        chartHomeConfigObject.series[0].data = [];
        chartHomeConfigObject.series[1].data = [];
        
		for (var j = 0; j < data.length; j++) {
			chartHomeConfigObject.series[0].data.push(data[j].pass);
			chartHomeConfigObject.series[1].data.push(data[j].fail + data[j].error);
		}
		chartHomeConfigObject.xAxis.categories = timeStamps;
		$scope.chartHomeConfig[id] = chartHomeConfigObject;
    };
}]);
