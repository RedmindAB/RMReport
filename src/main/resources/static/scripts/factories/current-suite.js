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
		    	currentDrivers: [],
		    	currentDriverRuns: [],
				currentTimeStamp: '',
				currentTimeStampArray: []
		    	};
	});