(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('Utilities', utilities);
			
	utilities.$inject = ['$state', 'CurrentSuite', 'ScreenshotMaster', 'Charts'];
	
	function utilities ($state, CurrentSuite, ScreenshotMaster, Charts){
	    return { 
	    	currentSection: 'Most Failing Devices',
	    	searchField: 			'',
	    	resultAmount: 			'50',
	    	timestamps: 			'',
	    	graphView: 				'Pass/Fail',
	        breakPointChoice: 		"None",
	        suitePartailSorting: 	'name',
	        chosen: 				[],
	        descTimestamps: 		[],
	        runValues: 				["10", "20", "50", "100", "500"],
	        chartVariants: 			["Pass/Fail", "Total Pass", "Total Fail", "Total Skipped", "Run Time"],
	        breakPoints: 			["None", "Browser", "Version", "Device", "Platform"],
	        caseSorting: 			['result','osname', 'devicename', 'osversion', 'browsername'],
	        colors: 				['#2ecc71', '#e74c3c', '#3498db', '#8e44ad', '#2c3e50', '#f1c40f', '#7f8c8d', '#e67e22', '#c0392b', '#1abc9c', '#9b59b6', '#34495e', '#16a085', '#f39c12', '#27ae60', '#d35400'],
	        sorting: 				['-stats.totFail','result','name'],
	        dashboardSections: 		['Most Failing Devices', 'Devices of this timestamp', 'Most Failing Classes'],
	        graphTypes:				['Line','Area','Column'],
	        currentGraphType:		'Line',
	        setGraphType:			setGraphType,
	        getCurrentGraphType:	getCurrentGraphType,
	        setDashboardSection: 	setDashboardSection,
			getSize: 				getSize,
			makeArray: 				makeArray,
			getIndexByTimestamp: 	getIndexByTimestamp,
			makeSuiteReadable: 		makeSuiteReadable,
			makeTimestampReadable: 	makeTimestampReadable,
			setBreakPoint: 			setBreakPoint,
			setResultAmount: 		setResultAmount,
			newContent: 			newContent,
			setSorting: 			setSorting,
			resetSorting: 			resetSorting,
			getSuiteLogo: 			getSuiteLogo,
			getResLimit: 			getResLimit,
			getCurrentPosName:		getCurrentPosName,
			clearData: 				clearData,
	    };
	    
	    function getCurrentGraphType(){
	    	
	    	switch (this.currentGraphType) {
			case "Line":
				return 'line';
			case "Area":
				return 'area';
			case "Column":
				return 'column';

			default:
				break;
			}
	    	
	    }
	    
	    function setGraphType(newVal){
	    	var series, seriesLength;
	    	this.currentGraphType = newVal;
	    	
	    	series = Charts.mainChartConfig.series;
	    	seriesLength = Charts.mainChartConfig.series.length;
	    	
	    	for(var i = 0; i < seriesLength; i++){
	    		series[i].type=this.getCurrentGraphType();
	    	}
	    	
	    }
	    
        function setDashboardSection (value){
        	this.currentSection = value;
        }
        
        function getSize (obj) {
		    var size = 0, key;
		    for (key in obj) {
		        if (obj.hasOwnProperty(key)) size++;
		    }
		    return size;
		}
        
		function makeArray (number) {
			return new Array(number);
		}
		
		function getIndexByTimestamp(timestamp){
	    	var rawArray = CurrentSuite.timestampRaw[CurrentSuite.currentSuiteInfo.id];
	    	return rawArray.indexOf(timestamp);
	    }
		
	    function makeSuiteReadable(suiteName){
			var obj = [suiteName];
			
			var objArray = $.map(obj, function(value, index) {
			    return [value];
			});
			
	    	var array = objArray[0].split('.');
	    	var arrayLength = array.length;
	    	var suite = array[array.length-1];
	    	return suite;
	    }
	    
		function makeTimestampReadable(timestamp){
			var stringStamp = timestamp.toString();
			var readableStamp = stringStamp.substring(0,4)+ "-" +
								stringStamp.substring(4,6)+"-" +
								stringStamp.substring(6,8)+" " +
								stringStamp.substring(8,10)+":" +
								stringStamp.substring(10,12);
			return readableStamp;
		}
		
        function setBreakPoint(choice){
        	this.breakPointChoice = choice;
        }
        
        function setResultAmount(value){
        	this.resultAmount = value;
        }
        
        function newContent(){
        	document.getElementById('button_reload').className = 'btn btn-primary';
        }
        
        function setSorting(sorting){
        	switch (sorting) {
        	
    		case 'pass/fail':
    			this.sorting = ['-stats.totFail','result','name'];
    			this.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
    			break;
    			
    		case 'time':
    			this.sorting = '-time';
    			this.caseSorting = '-timetorun';
    			break;

    		default:
    			break;
    		}
        }
        
        function resetSorting(){
	    	this.sorting = ['-stats.totFail','result','name'];
	    	this.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
        }
        
    	function getSuiteLogo(suiteName){
    		return "assets/img/suites/" + suiteName + ".png";
    	}
    	
    	function getResLimit() {
    		var reslimit = this.resultAmount;
    		if (!(isNaN(reslimit)) && reslimit !== "") {
    			reslimit = parseInt(reslimit);
    		} else {
    			reslimit = 50;
    		}
    		return reslimit;
    	}
    	
    	//returns suite-, class- or method-name depending on state
    	function getCurrentPosName(){
    		switch ($state.current.name) {
    		case 'reports.classes':
    			return CurrentSuite.currentSuiteInfo.name;
    			
    		case 'reports.methods':
    			var className = CurrentSuite.currentClass.name.substring(CurrentSuite.currentClass.name.lastIndexOf('.') +1);
    			return className;
    			
    		case 'reports.cases':
    			return CurrentSuite.currentMethod.name;
    			
    		case 'home':
    			return 'Aggregation';
    			
    		default:
    			return 'Aggregation';
    		}
    	}
    	
        function clearData(){
        	
        	ScreenshotMaster.data = [];
        	ScreenshotMaster.currentClass = undefined;
        	ScreenshotMaster.currentTimestamp = undefined;
        	ScreenshotMaster.previousView = undefined;
        	
        	CurrentSuite.activeQueries = [];
        	CurrentSuite.currentCases = [];
        	CurrentSuite.currentClass = [];
        	CurrentSuite.currentClasses = [];
        	CurrentSuite.currentDriver = [];
        	CurrentSuite.currentDriverRuns = [];
        	CurrentSuite.currentMethod = [];
        	CurrentSuite.currentMethods = [];
        	CurrentSuite.currentSuite = [];
        	CurrentSuite.currentSuiteInfo = [];
        	CurrentSuite.currentTimestamp = '';
        	CurrentSuite.currentTimestampArray = [];
        	CurrentSuite.lastRunSize = 50;
        	CurrentSuite.newLine = false;
        	CurrentSuite.currentSpecObject = [];
        	this.searchField = '';
        	this.resultAmount = '50';
        	this.timestamps = '';
        	this.graphView = 'Pass/Fail';
        	this.breakPointChoice = 'None';
        }
	}
})();