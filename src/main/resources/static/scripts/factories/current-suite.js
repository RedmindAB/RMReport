angular.module('webLog')
	.factory('CurrentSuite', function(){
		
		return {
		    	currentSuiteInfo: [],
		    	currentSuite: [],
		    	currentClass: [],
		    	currentClasses: [],
		    	currentMethod: [],
		    	currentMethods: [],
		    	currentDriver: [],
		    	currentCases: [],
		    	currentDriverRuns: [],
				currentTimeStamp: '',
				currentTimeStampArray: []
		    	};
	});