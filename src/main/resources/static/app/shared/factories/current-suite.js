(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('CurrentSuite', CurrentSuite);
	
	CurrentSuite.$inject = ['$rootScope'];
	
	function CurrentSuite ($rootScope){
		
		var service = {
				descTimestamps:				[],
		    	currentSuiteInfo: 			[],
		    	currentSuite: 				[],
		    	currentClass: 				[],
		    	currentClasses: 			[],
		    	currentMethod: 				[],
		    	allSuites: 					[],
		    	currentMethods: 			[],
		    	currentDriver: 				[],
		    	currentCases: 				[],
				currentSteps:				[],
		    	currentDriverRuns: 			[],
				currentTimestampArray: 		[],
				activeQueries: 				[],
				currentSpecObject: 			{},
				timestampRaw:				{},
				lastRunSize:				50,
				currentTimestamp: 			'',
				newLine: 					false
		};
		
		return service;
	}
})();