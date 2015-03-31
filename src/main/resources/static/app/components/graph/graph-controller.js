angular.module('webLog')
.controller('GraphCtrl', ['$scope','$http', '$state', '$q', 'Charts', 'CurrentSuite', 'Utilities', 'RestLoader', 'ChartMaker', function($scope, $http, $state, $q, Charts, CurrentSuite, Utilities, RestLoader, ChartMaker){
	
	$scope.Charts = Charts;
    $scope.chartMainConfig = {};
    $scope.descTimestamps = [];
    $scope.CurrentSuite = CurrentSuite;
    
    $scope.highlightPoint = function(timestamp){
    	ChartMaker.highlightPoint(timestamp);
    }
    
    $scope.getMainGraphData = function(suiteID){
    	RestLoader.getMainGraphData(suiteID);
    }
	
    $scope.addCaseToGraph = function(osName, osVersion, deviceName, browserName, browserVer){
    	ChartMaker.addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer);
    }

    $scope.loadMainChart = function(suiteID, newLine){
    	ChartMaker.loadMainChart(suiteID,newLine);
    }
    
    $scope.togglePlatformChosen = function(platform){
    	if (platform.chosen != undefined){
    		if (platform.chosen === true) {
				CurrentSuite.clearPlatformChosen(platform);
			}
    	}
    }
    
    $scope.setChosen = function(value){
    	if(value.chosen){
    		delete value.chosen;
    	}
    	else{
    		value.chosen = true;
    	}
    }
    
//   function runTimeChart() {
//    	
//    	var chart = Charts.mainChart;
//    	chart.options.chart.type = "line";
//    	chart.series = [];
//    	chart.yAxis.max = undefined;
//    	for (var i = 0; i < Charts.data.length; i++) {
//    		chart.series.push({
//    					data : Charts.data[i].runTime,
//    					name : Charts.data[i].name,
//    					color: getSerieColor(i),
//    		});
//		}
//    	
//    	chart.yAxis.title.text = 'Seconds';
//    	chart.options.plotOptions.series.stacking = '';
//    	chart.title.text = "Time to run in seconds";
//    	Charts.mainChart.options.tooltip.valueDecimals = 2;
//	}
    
//    function totalPassChart() {
//    	var chart = Charts.mainChart;
//    	
//    	chart.options.chart.type = "";
//    	chart.series = [];
//    	chart.yAxis.max = undefined;
//    	for (var i = 0; i < Charts.data.length; i++) {
//    		chart.series.push({
//				data : Charts.data[i].totalPass,
//				name : Charts.data[i].name,
//				color: getSerieColor(i),
//				type : "column",
//				dashStyle : "Solid",
//				connectNulls : false
//    		});
//		}
//    	chart.yAxis.title.text = 'Passed tests';
//    	chart.options.plotOptions.series.stacking = '';
//    	chart.title.text = "Passed tests";
//    	delete Charts.mainChart.options.tooltip.valueDecimals;
//	}
    
//    function totalFailChart() {
//    	var chart = Charts.mainChart;
//    	
//    	chart.options.chart.type = "";
//    	chart.series = [];
//    	chart.yAxis.max = undefined;
//    	for (var i = 0; i < Charts.data.length; i++) {
//    		chart.series.push({
//				data : Charts.data[i].totalFail,
//				name : Charts.data[i].name,
//				color: getSerieColor(i),
//				type : "column",
//				dashStyle : "Solid",
//				connectNulls : false
//    		});
//		}
//    	chart.yAxis.title.text = 'Failed tests';
//    	chart.options.plotOptions.series.stacking = '';
//    	chart.title.text = "Failed tests";
//    	delete Charts.mainChart.options.tooltip.valueDecimals;
//	}
    
//    function passFailChart() {
//    	var chart = Charts.mainChart;
//    	
//    	chart.series = [];
//    	for (var i = 0; i < Charts.data.length; i++) {
//    		chart.series.push({
//    			data : Charts.data[i].passPercentage,
//    			name : Charts.data[i].name,
//    			color: getSerieColor(i),
//    			type: "line"
//    		});
//		}
//    	chart.yAxis.title.text = 'Pass percentage';
//    	chart.title.text = "Percentage of passed tests";
//    	delete Charts.mainChart.options.tooltip.valueDecimals;
//	}
    
    //checks if trashcan list contains more than one
    $scope.trashcanEmpty = function() {
    	if (Charts.mainChart.series.length < 2) {
    		return true;
    	}
    	else {
    		return false;
    	}
	};
	
	//remove object from data Array from trashcan
	$scope.remove = function(item) { 
		var index = Charts.mainChart.series.indexOf(item)
		Charts.mainChart.series.splice(index, 1);  
 		for (var i = 0; i < Charts.data.length; i++) {
			if (Charts.data[i].name === item.name) {
				Charts.data.splice(i, 1);
			}
		}
	}
	
    function getPassPercentage(pass, fail, error){
    	var totalFail = fail + error;
    	var total = pass + totalFail;
    	var percentage = (pass/total)*100;
    	return percentage;
    }
    
    $scope.changeChartVariant = function(input){
    	ChartMaker.changeChartVariant(input);
    }
    
//    $scope.changeChartVariant = function(input){
//    	Utilities.graphView = input;
//    	
//    	switch (input) {
//		case "Pass/Fail":
//			passFailChart();
//			break;
//		case "Run Time":
//			runTimeChart();
//			break;
//		case "Total Pass":
//			totalPassChart();
//			break;
//		case "Total Fail":
//			totalFailChart();
//			break;
//		default:
//			Utilities.graphView = "Pass/Fail"
//			passFailChart();
//			break;
//		}
//    }
    
//	function getSerieColor(i){
//		if (i > Utilities.colors.length -1) {
//			i = i - (Utilities.colors.length - 1);
//		}
//		return Utilities.colors[i];
//	}
 		
}]);