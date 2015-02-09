angular.module('webLog')
	.factory('CurrentSuite', function(){
		
		return {
		    	currentSuiteInfo: [],
		    	currentSuite: [],
		    	currentClass: [],
		    	currentMethod: [],
		    	currentDriver: []
		    	};
	});