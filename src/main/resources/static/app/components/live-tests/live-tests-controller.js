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

		runProgressBar();
		updateInterval();
		
		function updateInterval(){
		        var progress = setInterval(function() {
		        	getLiveSuite();
		        }, 1000);
		}
		
		function getLiveSuite(){
			LiveTestsServices.getLiveSuite().then(function(request){
				console.log(request);
		    	LiveData.testData.push(request);
		    	for (var i = 0; i < request[0].data.length; i++) {
					if(request[0].data[i].status === 'running')
						LiveTestsServices.getLiveHistory(request[0].data[i].UUID);
				}
			});
		}
		
		function runProgressBar(){
			LiveTestsServices.runProgressBar();
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