(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('GraphCtrl', GraphCtrl);
	
	GraphCtrl.$inject = ['$http', '$state', '$q', 'Charts', 'CurrentSuite', 'Utilities', 'RestLoader', 'ChartMaker'];
			
	function GraphCtrl($http, $state, $q, Charts, CurrentSuite, Utilities, RestLoader, ChartMaker){
		
		var vm = this;
		
		vm.Charts = Charts;
		vm.CurrentSuite = CurrentSuite;
		
	    vm.chartMainConfig = {};
	    vm.descTimestamps = [];
	    
	    
	    vm.loadMainChart		= loadMainChart;
	    vm.togglePlatformChosen = togglePlatformChosen;
	    vm.setChosen 			= setChosen;
	    vm.trashcanEmpty 		= trashcanEmpty;
	    vm.remove 				= remove;
	    vm.changeChartVariant 	= changeChartVariant;
	    
		
	    /*
	     * Tells ChartMaker to pass the necessary parameters
	     * to a RESTful call to generate new data for
	     * the graph.
	     * 
	     * @param {Integer} id for suite to load data from.
	     * @param {Booelean} pass in true to add line to graph
	     * 					 or false to wipe graph and load new data.
	     */
	    function loadMainChart(suiteID, newLine){
	    	ChartMaker.loadMainChart(suiteID,newLine);
	    }
	    
	    /*
	     * Toggles given platforms choosen variable between
	     * true and false.
	     * 
	     * @param {Object} platform object tho toggle var in.
	     */
	    function togglePlatformChosen(platform){
	    	if (platform.chosen !== undefined){
	    		if (platform.chosen === true) {
					CurrentSuite.clearPlatformChosen(platform);
				}
	    	}
	    }
	    
	    /*
	     * If given object has a chosen value defined it
	     * deletes it, otherwise creates it and sets it to true.
	     * 
	     * @param {Object} Object to manipulate
	     */
	    function setChosen(value){
	    	if(value.chosen){
	    		delete value.chosen;
	    	}
	    	else{
	    		value.chosen = true;
	    	}
	    }
	    
	    /*
	     * Checks if Charts.mainChart.series consists of
	     * more than one item. returns true if only one remains.
	     * 
	     * @return {Boolean} true if list size = 1
	     */
	    function trashcanEmpty() {
	    	if (Charts.mainChart.series.length < 2) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
		}
		
	    /*
	     * Removes the passed in item from the charts data object,
	     * iterates through Charts.data object and removes item from the array,
	     * iterates through active queries and removes it from that array.
	     * 
	     * @param {Object} Data object to remove from chart, data array, query array.
	     */
	    function remove(item) { 
			var index = Charts.mainChart.series.indexOf(item);
			Charts.mainChart.series.splice(index, 1);  
			
			var i = 0;
			
	 		for (i = 0; i < Charts.data.length; i++) {
				if (Charts.data[i].name === item.name) {
					Charts.data.splice(i, 1);
				}
			}
	 		var queries = CurrentSuite.activeQueries;
	 		for (i = 0; i < queries.length; i++) {
	 			for (var j = 0; j < queries[i].length; j++) {
	 				if (queries[i][j].name === item.name) {
	 					queries[i].splice(j,1);
	 				}
				}
			}
		}
		
	    /*
	     * Calculates percentage of param passed against
	     * fail and error combined.
	     * 
	     * @param {Integer} passed tests
	     * @param {Integer} failed tests
	     * @param {integer} tests with error
	     * @return {integer} percentage of passed
	     */
	    function getPassPercentage(pass, fail, error){
	    	var totalFail = fail + error;
	    	var total = pass + totalFail;
	    	var percentage = (pass/total)*100;
	    	return percentage;
	    }
	    
	    /*
	     * Repaints the main chart to display the data passed
	     * in, must be one from the Utilities.chartVariants array.
	     * 
	     * @param{String} chart variant to display
	     */
	    function changeChartVariant(input){
	    	ChartMaker.changeChartVariant(input);
	    }
	}
})();