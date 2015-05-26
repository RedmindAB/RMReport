(function () {
	'use strict';

	angular
		.module('webLog')
		.controller('AdminCtrl', AdminCtrl);
	
	AdminCtrl.$inject = ['$http','$state', 'AdminServices'];
			
	function AdminCtrl($http, $state, AdminServices){
		
		var vm = this;
		var requestObj = {};
		
		vm.config = {};
		vm.configCompare = {};
		vm.errorMessages = [];
		vm.newPath = '';
		
		vm.addPath = addPath;
		vm.isNewPath = isNewPath;
		vm.isPathChanged = isPathChanged;
		vm.isToBeRemoved = isToBeRemoved;
		vm.readErrorMessage = readErrorMessage;
		vm.removePath = removePath;
		vm.saveChanges = saveChanges;
		
	
		loadRootConfig();
		
		
		function addPath(path){
			if (!pathExists(path)) {
				vm.configCompare.reportPaths.push(path);
			} else {
				vm.errorMessages.push({index: -1, message: "Path already exists"});
			}
			vm.newPath = '';
		}
		
		function removePath(index){
			if (vm.isNewPath(index)) {
				vm.configCompare.reportPaths.splice(index,1);
			} else {
				if (vm.configCompare.removeList.indexOf(index) === -1) {
					vm.configCompare.removeList.push(index);
				} else {
					var i = vm.configCompare.removeList.indexOf(index);
					vm.configCompare.removeList.splice(i, 1);
				}
			}
		}
		
		function loadRootConfig(){
			$http.get('/api/admin/config')
			.success(function(data, status, headers, config){ 
				if(data){
					vm.config = data;
					angular.copy(vm.config,vm.configCompare);
					vm.configCompare.removeList = [];
				}
			}).error(function(data, status, headers, config){
				console.error(data);
			});
		}
		
		function saveChanges(){
			vm.errorMessages = [];
			addPaths();
			changePaths();
			removePaths();
			loadRootConfig();
		}
		
		function changePaths(){
			for (var i = 0; i < vm.configCompare.reportPaths.length; i++) {
				if (vm.isPathChanged(i) && !vm.isToBeRemoved(i)) {
					var request = vm.configCompare.reportPaths[i];
					AdminServices.changePaths(request, vm.errorMessages);
				}
			}
		}
		
		function addPaths(){
			var request = [];
			for (var i = 0; i < vm.configCompare.reportPaths.length; i++) {
				if (vm.isNewPath(i)) {
					request.push(vm.configCompare.reportPaths[i]);
					vm.configCompare.reportPaths.splice(i,1);
					i--;
				}
			}
			if (request.length > 0) {
				AdminServices.addPaths(request, vm.errorMessages);
			}
		}
		
		function removePaths(){
			var request = vm.configCompare.removeList;
			AdminServices.removePaths(request);
		}
		
		function isToBeRemoved (index) {
			return vm.configCompare.removeList.indexOf(index) != -1;
		}
		
		function isPathChanged (index) {
			return vm.configCompare.reportPaths[index] !== vm.config.reportPaths[index] || index >= vm.config.reportPaths.length;
		}
		
		function isNewPath (index) {
			return index >= vm.config.reportPaths.length;
		}
		
		function pathExists(path){
			for (var i = 0; i < vm.configCompare.reportPaths.length; i++) {
				if (vm.configCompare.reportPaths[i] === path) {
					return true;
				}
			}
			return false;
		}
		
		function readErrorMessage(index){
			for (var i = 0; i < vm.errorMessages.length; i++) {
				if (vm.errorMessages[i].index === index) {
					return vm.errorMessages[i].message;
				}
			}
		}
		
		function getIndexFromError(msg){
			var i = msg.lastIndexOf("/");
			var index = parseInt(msg.substring(i+1));
			return index;
		}
	}
})();