angular.module('webLog')
	.factory('Utilities', ['$state', 'CurrentSuite', 'ScreenshotMaster', function($state, CurrentSuite, ScreenshotMaster){
	    return { 
	    	chosen: [],
	    	descTimestamps: [],
	    	searchField: '',
	    	resultAmount: '50',
	    	timeStamps: '',
	    	graphView: 'Pass/Fail',
	    	runValues: ["10", "20", "50", "100", "500"],
	        setResultAmount: function(value){
	        	this.resultAmount = value;
	        },
	        newContent: function(){
	        	document.getElementById('button_reload').className = 'btn btn-primary';
	        },
	        chartVariants: ["Pass/Fail", "Total Pass", "Total Fail", "Run Time"],
	        breakPoints: ["None", "Browser", "Version", "Device", "Platform"],
	        breakPointChoice: "None",
	        setBreakPoint: function(choice){
	        	this.breakPointChoice = choice;
	        },
	    	sorting: ['-stats.totFail','result','name'],
	    	caseSorting: ['result','osname', 'devicename', 'osversion', 'browsername'],
	        setSorting: function(sorting){
	        	switch (sorting) {
	    		case 'pass/fail':
	    			this.sorting = ['-stats.totFail','result','name'];
	    			this.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
	    			break;
	    		case 'time':
	    			this.sorting = '-time';
	    			this.caseSorting = '-timetorun';

	    		default:
	    			break;
	    		}
	        },
	        resetSorting: function(){
		    	this.sorting = ['-stats.totFail','result','name'];
		    	this.caseSorting = ['result','osname', 'devicename', 'osversion', 'browsername'];
	        },
	        breakPoints: ["None", "Browser", "Version", "Device", "Platform"],
	        breakPointChoice: "None",
	    	colors: ['#2ecc71', '#e74c3c', '#3498db', '#8e44ad', '#2c3e50', '#f1c40f', '#7f8c8d', '#e67e22', '#c0392b', '#1abc9c', '#9b59b6', '#34495e', '#16a085', '#f39c12', '#27ae60', '#d35400'],
	    	getSuiteLogo: function(suiteName){
	    		return "assets/img/suites/" + suiteName + ".png";
	    	},
	    	getResLimit: function() {
	    		var reslimit = this.resultAmount;
	    		if (!(isNaN(reslimit)) && !(reslimit === "")) {
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
	    			break;
	    		case 'reports.methods':
	    			var className = CurrentSuite.currentClass.name.substring(CurrentSuite.currentClass.name.lastIndexOf('.') +1);
	    			return className;
	    			break;
	    		case 'reports.cases':
	    			return CurrentSuite.currentMethod.name;
	    			break;
	    		case 'home':
	    			return 'Aggregation';
	    			break;
	    		default:
	    			return 'Aggregation';
	    			break;
	    		}
	    	},
	        clearData: function(timestamp){
	        	CurrentSuite.currentClass = [];
	        	CurrentSuite.currentTimeStamp = timestamp;
	        	
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
	        	CurrentSuite.currentSpecObject = [];
	        	CurrentSuite.currentSuite = [];
	        	CurrentSuite.currentSuiteInfo = [];
	        	CurrentSuite.currentTimeStamp = '';
	        	CurrentSuite.currentTimeStampArray = [];
	        	CurrentSuite.lastRunSize = 50;
	        	CurrentSuite.newLine = false;
	        	CurrentSuite.currentSpecObject = [];
	        	this.searchField = '';
	        	this.resultAmount = '50';
	        	this.timeStamps = '';
	        	this.graphView = 'Pass/Fail';
	        	this.breakPointChoice = 'None';
	        }
	    };
	}]);