(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('Utilities', utilities);
			
	utilities.$inject = ['$state', 'CurrentSuite', 'ScreenshotMaster'];
	
	function utilities ($state, CurrentSuite, ScreenshotMaster){
	    return { 
	    	// Dashboard
	    	dashboardSections: ['Most Failing Devices', 'Devices of this timestamp', 'Most Failing Classes'],
	    	currentSection: 'Most Failing Devices',
	        setDashboardSection: function(value){
	        	this.currentSection = value;
	        },
	    	// Reports
	    	chosen: [],
	    	descTimestamps: [],
	    	searchField: '',
	    	resultAmount: '50',
	    	timestamps: '',
	    	graphView: 'Pass/Fail',
	    	runValues: ["10", "20", "50", "100", "500"],
	        chartVariants: ["Pass/Fail", "Total Pass", "Total Fail", "Total Skipped", "Run Time"],
	        breakPoints: ["None", "Browser", "Version", "Device", "Platform"],
	        caseSorting: ['result','osname', 'devicename', 'osversion', 'browsername'],
	        colors: ['#2ecc71', '#e74c3c', '#3498db', '#8e44ad', '#2c3e50', '#f1c40f', '#7f8c8d', '#e67e22', '#c0392b', '#1abc9c', '#9b59b6', '#34495e', '#16a085', '#f39c12', '#27ae60', '#d35400'],
	        breakPointChoice: "None",
	        sorting: ['-stats.totFail','result','name'],
	        suitePartailSorting: 'name',
		    getIndexByTimestamp: function(timestamp){
		    	var rawArray = CurrentSuite.timestampRaw[CurrentSuite.currentSuiteInfo.id];
		    	return rawArray.indexOf(timestamp);
		    },
			makeTimestampReadable: function(timestamp){
				var stringStamp = timestamp.toString();
				var readableStamp = stringStamp.substring(0,4)+ "-" +
									stringStamp.substring(4,6)+"-" +
									stringStamp.substring(6,8)+" " +
									stringStamp.substring(8,10)+":" +
									stringStamp.substring(10,12);
				return readableStamp;
			},
	        setBreakPoint: function(choice){
	        	this.breakPointChoice = choice;
	        },
	        setResultAmount: function(value){
	        	this.resultAmount = value;
	        },
	        newContent: function(){
	        	document.getElementById('button_reload').className = 'btn btn-primary';
	        },
	        setSorting: function(sorting){
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
	        },
	        resetSorting: function(){
		    	this.sorting = ['-stats.totFail','result','name'];
		    	this.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
	        },
	    	getSuiteLogo: function(suiteName){
	    		return "assets/img/suites/" + suiteName + ".png";
	    	},
	    	getResLimit: function() {
	    		var reslimit = this.resultAmount;
	    		if (!(isNaN(reslimit)) && reslimit !== "") {
	    			reslimit = parseInt(reslimit);
	    		} else {
	    			reslimit = 50;
	    		}
	    		return reslimit;
	    	},
	    	//returns suite-, class- or method-name depending on state
	    	getCurrentPosName: function (){
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
	    	},
	        clearData: function(){
	        	CurrentSuite.currentClass = [];
	        	
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
	    };
	}
})();