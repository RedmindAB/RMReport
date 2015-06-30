(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('SuiteInfoHandler', SuiteInfoHandler);
	
	SuiteInfoHandler.$inject = ['SuiteInfoServices','CurrentSuite','Utilities'];
	
	function SuiteInfoHandler(SuiteInfoServices, CurrentSuite, Utilities){
		
		return {
			setUpSuiteSkeleton: 	setUpSuiteSkeleton,
			loadTimestamp: 			loadTimestamp,
			setPassFailAllMethods: 	setPassFailAllMethods,
			setUpSpecs:				setUpSpecs,
			methodStatsToInt:		methodStatsToInt
		};
		
		function setUpSpecs(){
			SuiteInfoServices.getSpecsInfo(CurrentSuite.currentSuiteInfo.id, Utilities.getResLimit())
			.then(function(data){
				CurrentSuite.setUpSpecObject(data);
			});
		}
		
		function setUpSuiteSkeleton(timestamp, suiteID, firstLoad){
			SuiteInfoServices.getSuiteSkeletonByTimestamp(timestamp, suiteID)
				.then(setUp);
			
			function setUp(data){
				CurrentSuite.currentSuite = data;
	    		setResult(CurrentSuite.currentSuite);
	    		setPassFailAllClasses(timestamp, CurrentSuite.currentSuite);
	    		if (CurrentSuite.currentClass !== undefined) {
	    			CurrentSuite.currentMethods = CurrentSuite.getMethodsByClassId(CurrentSuite.currentClass.id);
	    			if (CurrentSuite.currentMethods !== undefined) {
	    				setPassFailAllMethods(timestamp, CurrentSuite.currentClass, CurrentSuite.currentMethods);
					}
				}
	    		if (firstLoad) {
	    			setUpSpecs();
				}
			}
		}
		
		function loadTimestamp(timestamp, firstLoad){
			CurrentSuite.currentTimestamp = timestamp;
			setUpSuiteSkeleton(timestamp, CurrentSuite.currentSuiteInfo.id, firstLoad);
		}
		
		function setPassFailAllClasses(timestamp, classObj){
			var stats, classLength, i;
			
			classLength = classObj.length;
			
			for (i = 0; i < classLength; i++) {
				setStats(i);
			}
			
			function setStats(i){
				SuiteInfoServices.getPassFailByClass(timestamp, classObj[i].id)
				.then(function(data){
					stats = {
							passed:		0,
							failure: 	0,
							skipped: 	0,
							total:		0,
							totFail:	0
					};
					stats.passed = parseInt(data.passed);
					stats.failure = parseInt(data.failure);
					stats.error = parseInt(data.error);
					stats.skipped = parseInt(data.skipped);
					stats.total = parseInt(data.total);
					stats.totFail = getTotFail(data);
					
					classObj[i].stats = stats;
				});
			}
		}
			
		function setPassFailAllMethods(timestamp, classObj, methods){
			var methodsLength, i;
			methodsLength = methods.length;
			
			for (i = 0; i < methodsLength; i++) {
				setStats(i);
			}
			
			function setStats(i){
				SuiteInfoServices.getPassFailByMethod(timestamp, classObj.id, methods[i].id)
				.then(function(data){
					methods[i].stats = methodStatsToInt(data);
					methods[i].stats.totFail = getTotFail(data);
				});
			}
		}
		
		function setResult(obj){
			var i, objLength;
			
			objLength = obj.length;
			for (i = 0; i < objLength; i++) {
				if (obj[i].failure >= 1) {
					obj[i].result = "failure";
				} else if(obj[i].failure === 0 && obj[i].passed === 0) {
					obj[i].result = "skipped";
				} else {
					obj[i].result = "passed";
				}
			}
		}
		
		function getTotFail(passFailData){
			var totFail = parseInt(passFailData.error) + parseInt(passFailData.failure);
			return totFail;
		}
		
		function methodStatsToInt(methodStats){
			var stats = {
					error: 0,
					failure: 0,
					passed: 0,
					total: 0,
					skipped: 0,
			};
			
			stats.error = parseInt(methodStats.error);
			stats.failure = parseInt(methodStats.failure);
			stats.passed = parseInt(methodStats.passed);
			stats.total = parseInt(methodStats.total);
			stats.skipped = parseInt(methodStats.skipped);
			
			return stats;
		}
		
	}
})();