angular.module('webLog')
.controller('GraphCtrl', ['$scope','$http', '$state', '$q', 'Charts', 'CurrentSuite', 'Utilities', 'RestLoader', 'ChartMaker', function($scope, $http, $state, $q, Charts, CurrentSuite, Utilities, RestLoader, ChartMaker){
	
	$scope.Charts = Charts;
    $scope.chartMainConfig = {};
    $scope.descTimestamps = [];
    $scope.CurrentSuite = CurrentSuite;
    
    $scope.newContent = function(){
    	Utilities.newContent();
    }
    
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
 		var queries = CurrentSuite.activeQueries;
 		for (var i = 0; i < queries.length; i++) {
 			for (var j = 0; j < queries[i].length; j++) {
 				if (queries[i][j].name === item.name) {
 					queries[i].splice(j,1);
 				}
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
}]);