(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('LiveTestsCtrl', LiveTestsCtrl);
	
	LiveTestsCtrl.$inject = ['$scope', '$http','$state', 'LiveTestsServices', 'CurrentSuite', 'LiveData', '$timeout'];
			
	function LiveTestsCtrl($scope, $http, $state, LiveTestsServices, CurrentSuite, LiveData, $timeout){
		
		var vm = this;
		var requestObj = {};
	
		vm.mockedPassedTests 	= ['test_chooseAnotherClass', 'test_Specifications_PlatformVersion', 'test_changeRunLimit_500', 'test_Specifications_CheckDeviceGetPlatform', 'test_ClickOnSuiteLink', 'test_ClearCheckBoxesVersion', 'test_Specifications_Version', 'test_gridGetJson', 'test_Specifications_VersionPlatform', 'test_RunTime'];
		vm.mockedNewTests 		= ['test_openSysos', 'test_opencloseSyso', 'test_chooseAnotherClass', 'test_chooseTimestamp', 'test_changeProject', 'test_isThumbnailPresent', 'test_isScreenShotPresent', 'test_isScreenShotSwitched', 'test_goToAdmin', 'test_goToDashboard'];
		vm.mockedStatistics 	= ['6.74', '144', '65', '0'];
		vm.LiveData 			= LiveData;
		vm.setRowColor 			= setRowColor;
		vm.getPercentage 		= getPercentage;
		vm.getTotalDone			= getTotalDone;

		getLiveTests();
		updateInterval();
		
		function updateInterval(){
		        var progress = setInterval(function() {
		        	if(LiveData.uuid.length > 0){
		        		getLiveSuite();
		        	}
		        	else{
		        		getLiveTests();
		        	}
		        }, 1000);
		}
		
		function getLiveTests(){
			LiveTestsServices.getLiveSuite().then(function(request){
				for (var i = 0; i < request[0].data.length; i++) {
					if(request[0].data[i].status === 'running'){
						LiveTestsServices.getLiveTests(request[0].data[i].UUID);
						LiveData.uuid = request[0].data[i].UUID;
					}
				}
				
			});
		}
		
		function getLiveSuite(){
			LiveTestsServices.getLiveHistory(LiveData.uuid);
		}
		
	    $scope.counter = 0;
	    $scope.onTimeout = function(){
	        $scope.counter++;
	        mytimeout = $timeout($scope.onTimeout,1000);
	    }
	    var mytimeout = $timeout($scope.onTimeout,1000);
	    
	    $scope.stop = function(){
	        $timeout.cancel(mytimeout);
	    }
	    
	    function setRowColor(test){
	    	if(test.status==='running'){
	    		return "test-row-running";
	    	}
	    	else if (test.result==='passed'){
	    		return "test-row-passed";
	    	}
	    	else if (test.result==='failure'){
	    		return "test-row-failure";
	    	}
	    	else if (test.result==='skipped'){
	    		return "test-row-skipped";
	    	}
	    }
	    
	    function getPercentage(result){
	    	if (LiveData.suite) {
		    	var totalTests = LiveData.suite.totalTests;
		    	var total = 0;
		    	for (var i = 0; i < LiveData.tests.length; i++) {
					if (LiveData.tests[i].result===result) {
						total++;
					}
				}
		    	var res = (total/totalTests)*100;
		    	if (isNaN(res)) return 0+'%'
	    	return res+'%';
	    	}
	    }
	    
	    function getTotalDone(){
	    	var totalDone = 0;
	    	var tests = LiveData.tests;
	    	var testsLength = tests.length;
	    	for (var i = 0; i < testsLength; i++) {
				if (tests[i].status === 'done'){
					totalDone++;
				}
			}
	    	return totalDone;
	    }
	}
})();