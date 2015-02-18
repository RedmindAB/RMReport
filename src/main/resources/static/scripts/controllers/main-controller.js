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
    $scope.chartVariants = ["Pass/Fail", "Total Pass", "Total Fail", "Run Time"];
    $scope.currentChartVariant = "Pass/Fail";
    
    $scope.changeChartVariant = function(input){
    	$scope.currentChartVariant = input;
    	
    	switch (input) {
		case "Pass/Fail":
			passFailChart();
			break;
		case "Run Time":
			runTimeChart();
			break;
		case "Total Pass":
			totalPassChart();
			break;
		case "Total Fail":
			totalFailChart();
			break;
		default:
			$scope.currentChartVarint = "Pass/Fail"
			passFailChart();
			break;
		}
    }
    
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
	
	$scope.clearAllChosen = function(){
		console.log("clearing chosen");
		//remove classes checkbox
		clearChosenClasses();
		
		//remove method checkbox
		clearChosenMethods();
		
		//remove browser checkbox
		clearChosenBrowsers();
		
		//remove device checkbox
		clearChosenDevices();
		
		//remove os checkbox
		clearChosenOs();
	}
	
	function clearChosenOs() {
		if (CurrentSuite.currentSpecObject.platforms) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				var versions = platforms[i].versions;
				for (var j = 0; j < versions.length; j++) {
					if (versions[j].chosen) {
						chosen.os.push(versions[j].osid);
					}
				}
			}
		}
	}
	
	function clearChosenDevices() {
		if (CurrentSuite.currentSpecObject.platforms) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
				for (var j = 0; j < CurrentSuite.currentSpecObject.platforms[i].devices.length; j++) {
					if (CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen) {
						delete CurrentSuite.currentSpecObject.platforms[i].devices[j].chosen;
					}
				}
			}
		}
	}
	
	function clearChosenBrowsers() {
		if (CurrentSuite.currentSpecObject.browsers) {
			for (var i = 0; i < CurrentSuite.currentSpecObject.browsers.length; i++) {
				if (CurrentSuite.currentSpecObject.browsers[i].chosen) {
					delete CurrentSuite.currentSpecObject.browsers[i].chosen;
				}
			}
		}
	}
	
	function clearChosenMethods(chosenMethod) {
		if (CurrentSuite.currentClass.testcases) {
			for (var i = 0; i < CurrentSuite.currentClass.testcases.length; i++) {
				if (CurrentSuite.currentClass.testcases[i].chosen) {
					delete CurrentSuite.currentClass.testcases[i].chosen;
				}
			}
		}
	}
	
	function clearChosenClasses(chosenClass) {
		if (CurrentSuite.currentSuite) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].chosen) {
					delete CurrentSuite.currentSuite[i].chosen
				}
			}
		}
	}
	
	$scope.getChosen = function(){
		var chosen = {
				os: [],
				devices: [],
				browsers: [],
				classes: [],
				testcases: [],
		};
		
		//add os to send
		if (CurrentSuite.currentSpecObject.platforms) {
			var platforms = CurrentSuite.currentSpecObject.platforms;
			for (var i = 0; i < platforms.length; i++) {
				var versions = platforms[i].versions;
				for (var j = 0; j < versions.length; j++) {
					if (versions[j].chosen) {
						chosen.os.push(versions[j].osid);
					}
				}
			}
		}
		
		//add devices to send
		if (CurrentSuite.currentSpecObject.platforms) {
			var platforms = CurrentSuite.currentSpecObject.platforms;
			for (var i = 0; i < platforms.length; i++) {
				var devices= platforms[i].devices;
				for (var j = 0; j < devices.length; j++) {
					if (devices[j].chosen) {
						chosen.devices.push(devices[j].deviceid);
					}
				}
			}
		}
		
		//add browsers to send
		if (CurrentSuite.currentSpecObject.browsers) {
			var browsers = CurrentSuite.currentSpecObject.browsers;
			for (var i = 0; i < browsers.length; i++) {
				if (browsers[i].chosen) {
					chosen.browsers.push(browsers[i].browserid);
				}
			}
		}
		
		//add classes to send
		if (CurrentSuite.currentSuite) {
			var classes = CurrentSuite.currentSuite;
			for (var i = 0; i < classes.length; i++) {
				if (classes[i].chosen) {
					chosen.classes.push(classes[i].id);
				}
			}
		}
		
		//add methods to send
		if (CurrentSuite.currentClass.testcases) {
			var testcases = CurrentSuite.currentClass.testcases
			for (var i = 0; i < testcases.length; i++) {
				if (testcases[i].chosen) {
					chosen.testcases.push(testcases[i].id);
				}
			}
		}
		return chosen;
	}
	
    // HTTP -----------------------------------------------------------------------------------------------------------
    
	$scope.getSpecsInfo = function(suiteID){
		var reslimit = getResLimit();
	    $http.get('/api/stats/options?suiteid='+suiteID+'&limit='+reslimit)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSpecObject = data;
	    		console.log(CurrentSuite.currentSpecObject);
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
	    		$scope.getSpecsInfo(suite.id);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getSuiteSkeletonByTimeStamp = function(timestamp){
	    $http.get('/api/suite/specificbyid?suiteid=' + CurrentSuite.currentSuiteInfo.id + "&timestamp="+timestamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentSuite = data;
	    		$scope.getSpecsInfo(CurrentSuite.currentSuiteInfo.id);
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getCases = function(method){
		CurrentSuite.currentMethod = method;
	    $http.get('/api/driver/bytestcase?id='+CurrentSuite.currentMethod.id+'&timestamp='+CurrentSuite.currentTimeStamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentCases = data;
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
	
	$scope.setCurrentSuiteByTimestamp = function(suite, timestamp){
		CurrentSuite.currentSuite = suite;
	    $http.get('/api/class/getclasses?suiteid='+CurrentSuite.currentSuite.id+"&timestamp"+timestamp)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		CurrentSuite.currentClasses = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	};
	
   $scope.loadMainChart = function(suiteID) {
	   console.log("Specs with chosen");
	   console.log(CurrentSuite.currentSpecObject);
    	var requestObject = $scope.getGraphDataObject(suiteID)
    	console.log("requestObject");
    	console.log(requestObject);
    	$http.post('/api/stats/graphdata', requestObject)
    	.success(function(data, status, headers, config){
    		CurrentSuite.currentTimeStamp = data[0].timestamp;
    		$scope.createMainChart(data);
    	}).error(function(data, status, headers, config){
    		console.log(data);
    	});
   };
   
   $scope.loadNewTimeStamp = function(timestamp){
	   $scope.getSuiteSkeletonByTimeStamp(timestamp);
   }
    
    $scope.createHomeChartFromID = function(id) {
    	var requestObject = {
    			suiteid:id,
    			reslimit:51,
    			os: [],
    			devices: [],
    			browsers: [],
    			classes:[],
    			testcases:[]
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
    	
    	dataRequest.name = '';
		dataRequest.suiteid = suiteID;
		dataRequest.reslimit = getResLimit();
		
		dataRequest.os = chosen.os;
		dataRequest.devices = chosen.devices;
		dataRequest.browsers = chosen.browsers;
		dataRequest.classes = chosen.classes;
		dataRequest.testcases = chosen.testcases;
    	return dataRequest;
    };
    
    
    function getResLimit() {
		var reslimit = Utilities.amountField;
		if (!(isNaN(reslimit)) && !(reslimit === "")) {
			reslimit = parseInt(reslimit) +1;
		} else {
			reslimit = 51;
		}
		return reslimit;
	}
	// CHART OBJECTS -----------------------------------------------------------------------------------------------------------
	
    function runTimeChart() {
    	
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "line";
    	chart.series = [{
			data : Charts.data.runTime,
			name : 'Run Time',
			color : '#FF0000',
			id : "runTime"
		}];
    	chart.yAxis.title.text = 'Time to run';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Time to run in seconds for the last " + Charts.data.size + " runs";
	}
    
    function totalPassChart() {
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "";
    	chart.series = [{
				data : Charts.data.totalPass,
				id : "totalPass",
				name : "Total Pass",
				type : "column",
				color : "green",
				dashStyle : "Solid",
				connectNulls : false
			}];
    	chart.yAxis.title.text = 'Passed test';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Amount of passed tests for the last " + Charts.data.size + " runs";
	}
    
    function totalFailChart() {
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "";
    	chart.series = [{
				data : Charts.data.totalFail,
				id : "totalFail",
				name : "Total Fail",
				type : "column",
				color : "#FF0000",
				dashStyle : "Solid",
				connectNulls : false
			}];
    	chart.yAxis.title.text = 'Failed test';
    	chart.options.plotOptions.series.stacking = '';
    	chart.title.text = "Amount of failed tests for the last " + Charts.data.size + " runs";
	}
    
    function passFailChart() {
    	var chart = Charts.mainChart;
    	
    	chart.options.chart.type = "areaspline";
    	chart.series = [ {
			data : Charts.data.totalPass,
			name : 'Pass',
			color : '#D4D9DD',
			id : "mainPass"
		}, {
			data : Charts.data.totalFail,
			name : 'Fail',
			color : '#FF0000',
			id : "mainFail"
		} ];
    	chart.yAxis.title.text = 'Percentage';
    	chart.options.plotOptions.series.stacking = 'percent';
    	chart.title.text = "Pass/Fail ratio for the last " + Charts.data.size + " runs";
	}
    
    $scope.createMainChart = function(data){
    	CurrentSuite.currentTimeStampArray = [];
    	for (var index = 0; index < data.length; index++) {
			CurrentSuite.currentTimeStampArray.push(data[index].timestamp);
			
		}
    	
    	Charts.data.runTime = [];
    	Charts.data.totalPass = [];
    	Charts.data.totalFail = [];
    	Charts.data.size = data.length;
    	
    	for (var i = 0; i < Charts.data.size; i++) {
			Charts.data.totalPass.push(data[i].pass);
			Charts.data.totalFail.push(data[i].fail + data[i].error);
			Charts.data.runTime.push(data[i].time);
		}
    	
    	Charts.mainChart.xAxis.categories = CurrentSuite.currentTimeStampArray;
    	Charts.mainChart.options.plotOptions.series.point.events.click = function (e) {
    		$scope.loadNewTimeStamp(this.category);
    	};
    	$scope.changeChartVariant();
		$scope.chartMainConfig = Charts.mainChart;
    };
    
    $scope.toggleMainChart = function(){
    	if ($scope.chartMainConfig === Charts.mainChart) {
    		$scope.chartMainConfig = Charts.mainTime;
    		$scope.mainGraphToggle = true;
		} else {
			$scope.chartMainConfig = Charts.mainChart;
			$scope.mainGraphToggle = false;
		}
    }
    
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
