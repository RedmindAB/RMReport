angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location','$state','$http','$document', function($scope, $location, $state, $http, $document){
		
		$scope.currentSuiteID;;
		$scope.currentClass=[];
		$scope.mockMethods = [];
	    $scope.searchText = "";
	    $scope.graphData = {};
	    $scope.currentGraphData = [];
	    $scope.data = {};
		$scope.options = {};
		var chartLoaded = false;
		
	    $scope.test = [1,2,3,4,5];
	    
		$scope.setCurrentSuiteID = function(id){
			$scope.currentSuiteID = id;

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
	    
		$scope.dataStuff = 100;

		$scope.resetFilterField = function(){
	    	$scope.searchText="";
	    };
		
	    $scope.getCases = function(method){
	    	mockMethods(method.name);
	    }
	    
	    $scope.getGraphData = function(suiteID, classIDs, caseIDs, drivers){
	    	$scope.requestObject =  $scope.getGraphDataObject(suiteID, classIDs, caseIDs, drivers);
	    	$http.post('/api/stats/graphdata', $scope.requestObject)
	    	.success(function(data, status, headers, config){ 
	    		$scope.currentGraphData = data;
	    		console.log(data);
	    		$scope.createMainChart($scope.currentGraphData);
	    		chartLoaded = true;
	    	}).error(function(data, status, headers, config){
	    		console.log(data);
	    	});
	    }
	    
	    $scope.createMainChart = function(data) {
	    	var dataObject = [];
		    	for (var i = 0; i < data.length; i++) {
		    		var row = {}
		    		row['x'] = i;
		    		row['1'] = calcPercentage(data[i].error, data[i].fail, data[i].pass);
						dataObject.push(row);
				}
	    	$scope.data = dataObject;
	    	$scope.options = $scope.getMainOptions(dataObject.length);
	    };
	    
	    $scope.getMethods = function(suiteClass){
	        $http.get('/api/method/getmethods?classid='+suiteClass.id)
	        .success(function(data, status, headers, config){ 
	        	if(data){
	        		$scope.currentClass = data;
	        	};
	        }).error(function(data, status, headers, config){
	        	console.log(data);
	        });
	    };
	    
	    function calcPercentage(error,fail,pass) {
			var totFail = error + fail;
			var total = totFail + pass;
			return Math.round((totFail/total*100));
		}
	    
		function mockMethods(name) {
			var mock = $scope.mockMethods;
			mock.push(name+"(OSX Firefox)");
			mock.push(name+"(OSX Chrome)");
			mock.push(name+"(OSX Safari)");
			mock.push(name+"(Windows Firefox)");
			mock.push(name+"(Windows Chrome)");
			mock.push(name+"(Windows IExplorer)");
		}
		
	    $scope.getMainOptions = function(spacing){
	    	console.log($scope.currentGraphData);
	    	return {
				  axes: {
					    x: {key: 'x', ticks: $scope.currentGraphData.length, labelFunction: function(value) {return value;}, type: 'linear'},
					    y: {type: 'linear', ticks: 10, min: 0, max: 100},
					  },
					  series: [
					    {y: '1', type: 'area',color: 'brown', thickness: '2px',visible: true, drawDots: false,label: 'Combined Aggregation'}
					  ],
					  lineMode: 'cardinal',
					  tension: 0.7,
					  tooltip: {mode: 'scrubber', interpolate: 'true', formatter: function(x, y, series) {return series.label+" - " + y;}},
					  drawLegend: true,
					  drawDots: true,
					  interpolate: '',
					  columnsHGap: 5
					}
	    };
	}]);