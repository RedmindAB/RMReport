(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LocalStorage' ,LocalStorage);
	
	LocalStorage.$inject = ['$rootScope', '$window', '$location', 'CurrentSuite'];
	
	function LocalStorage($rootScope, $window, $location, CurrentSuite){
		
		var vm = this;
		
		vm.CurrentSuite = CurrentSuite;
		
		var service = {
				saveState: 		saveState,
				restoreState: 	restoreState,
				getStorage:		getStorage
		};
		
		$rootScope.$on('saveState', service.saveState);
		
		$rootScope.$on('restoreState', service.restoreState);
		
		function getStorage(){
			return JSON.parse($window.localStorage.getItem('CurrentSuite'));
		}
		
		function saveState(){
			$window.localStorage.setItem('CurrentSuite', JSON.stringify(vm.CurrentSuite));
		}
		
		function restoreState(){
			var checkSuite = getStorage();
			if (checkSuite !== undefined && checkSuite !== null) {
				for(var prop in checkSuite){
					CurrentSuite[prop] = checkSuite[prop];
				}
			} else {
				console.log("else");
				$location.path("/");
			}
		}
		
		return service;
	}
})();