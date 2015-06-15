(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('LiveTestsCtrl', LiveTestsCtrl);
	
	LiveTestsCtrl.$inject = ['$scope', '$http','$state', 'LiveTestsServices', 'CurrentSuite'];
			
	function LiveTestsCtrl($scope, $http, $state, LiveTestsServices, CurrentSuite){
		
		var vm = this;
		var requestObj = {};
	
		vm.mockedPassedTests = ['test_chooseAnotherClass', 'test_Specifications_PlatformVersion', 'test_changeRunLimit_500', 'test_Specifications_CheckDeviceGetPlatform', 'test_ClickOnSuiteLink', 'test_ClearCheckBoxesVersion', 'test_Specifications_Version', 'test_gridGetJson', 'test_Specifications_VersionPlatform', 'test_RunTime'];
		vm.mockedNewTests = ['test_openSysos', 'test_opencloseSyso', 'test_chooseAnotherClass', 'test_chooseTimestamp', 'test_changeProject', 'test_isThumbnailPresent', 'test_isScreenShotPresent', 'test_isScreenShotSwitched', 'test_goToAdmin', 'test_goToDashboard'];
		vm.statistics = ['6.74', '144', '65', '0'];
	
		runProgressBar();
		
		function runProgressBar(){
			LiveTestsServices.runProgressBar();
		}
	}
})();