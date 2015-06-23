(function(){
	'use strict';
	
	angular
		.module('weblog')
		.service('ScreenshotServices', ScreenshotServices);
	
	ScreenshotServices.$inject = ['$http'];
	
	function ScreenshotServices($http){
		
		var vm = this;
		
		vm.checkContainsScreens = checkContainsScreens;
		
		function setContainsScreensClasses(){
			
		}
	}
})();