(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('LiveTestsCtrl', LiveTestsCtrl);
	
	LiveTestsCtrl.$inject = ['$scope', '$http','$state', 'LiveTestsServices', 'CurrentSuite', 'LiveData', '$timeout'];
			
	function LiveTestsCtrl($scope, $http, $state, LiveTestsServices, CurrentSuite, LiveData, $timeout){
		
		var vm = this;
		var requestObj = {};
	
		vm.mockedPassedTests = ['test_chooseAnotherClass', 'test_Specifications_PlatformVersion', 'test_changeRunLimit_500', 'test_Specifications_CheckDeviceGetPlatform', 'test_ClickOnSuiteLink', 'test_ClearCheckBoxesVersion', 'test_Specifications_Version', 'test_gridGetJson', 'test_Specifications_VersionPlatform', 'test_RunTime'];
		vm.mockedNewTests = ['test_openSysos', 'test_opencloseSyso', 'test_chooseAnotherClass', 'test_chooseTimestamp', 'test_changeProject', 'test_isThumbnailPresent', 'test_isScreenShotPresent', 'test_isScreenShotSwitched', 'test_goToAdmin', 'test_goToDashboard'];
		vm.mockedStatistics = ['6.74', '144', '65', '0'];
		vm.LiveData = LiveData;

		updateInterval();
		getLiveTests();
		
		function updateInterval(){
		        var progress = setInterval(function() {
		        	getLiveSuite();
		        }, 1000);
		}
		
		function getLiveTests(){
			LiveTestsServices.getLiveSuite().then(function(request){
				LiveTestsServices.getLiveTests(request[0].data[0].UUID);
			});
		}
		
		function getLiveSuite(){
			LiveTestsServices.getLiveSuite().then(function(request){
				console.log(request[0]);
		    	LiveData.testData.push(request);
		    	for (var i = 0; i < request[0].data.length; i++) {
					if(request[0].data[i].status === 'running')
						console.log("Running is true");
						LiveTestsServices.getLiveHistory(request[0].data[i].UUID);
				}
		    	
		    	// Progress bar update
		    	console.log(request[0].data[0].status);
				console.log(request[0]);
				if(request[0].data[0].status === 'finished'){
					LiveData.percentage = "100%";
				}
			});
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
	}
})();