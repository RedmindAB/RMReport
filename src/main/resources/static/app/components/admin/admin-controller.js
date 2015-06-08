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
		vm.errorModalShown = false;
		
		vm.addPath = addPath;
		vm.isNewPath = isNewPath;
		vm.isPathChanged = isPathChanged;
		vm.isToBeRemoved = isToBeRemoved;
		vm.readErrorMessage = readErrorMessage;
		vm.removePath = removePath;
		vm.saveChanges = saveChanges;
		vm.toggleErrorModal = toggleErrorModal;
		
	
		loadRootConfig();
		
		function toggleErrorModal() {
			vm.errorModalShown = !vm.errorModalShown;
			console.log(vm.errorModalShown);
		};
		
		function addPath(path){
			if (!pathExists(path)) {
				vm.configCompare.reportPaths.push(path);
			} else {
				vm.errorMessages.push({index: -1, message: "Path already exists"});
			}
			vm.newPath = '';
		}
		
		function removePath(index){
			var path = vm.config.reportPaths[index];
			if (vm.isNewPath(index)) {
				vm.configCompare.reportPaths.splice(index,1);
			} else {
				if (vm.configCompare.removeList.indexOf(path) === -1) {
					vm.configCompare.removeList.push(vm.config.reportPaths[index]);
				} else {
					var i = vm.configCompare.removeList.indexOf(path);
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
			changePaths()
			.finally(function(){
				return removePaths();
			})
			.finally(function(){
				return addPaths();
			})
			.finally(function(){
				loadRootConfig();
				console.log(vm.errorMessages.length);
				if (vm.errorMessages.length > 0) {
					vm.toggleErrorModal();
				}
			});
		}
		
		function changePaths(){
			var request = [];
			for (var i = 0; i < vm.configCompare.reportPaths.length; i++) {
				if (vm.isPathChanged(i) && !vm.isToBeRemoved(i) && !vm.isNewPath(i)) {
					request.push({
						oldPath: vm.config.reportPaths[i], 
						newPath: vm.configCompare.reportPaths[i]
					});
				}
			}
			return AdminServices.changePaths(request, addErrorMessage);
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
				return AdminServices.addPaths(request, addErrorMessage);
			}
		}
		
		function removePaths(){
			var request = vm.configCompare.removeList;
			return AdminServices.removePaths(request);
		}
		
		function isToBeRemoved (index) {
			var path = vm.config.reportPaths[index];
			return vm.configCompare.removeList.indexOf(path) != -1;
		}
		
		function isPathChanged (index) {
			return vm.configCompare.reportPaths[index] !== vm.config.reportPaths[index];
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
		
		function addErrorMessage(config, errorType){
			
			switch (errorType) {
			case "change":
				vm.errorMessages.push({
					message: "Could not change path:\n" + config.data.oldPath + "\nTo:\n" + config.data.newPath
				});
				break;
				
			case "create":
				vm.errorMessages.push({
					message: "Could not create path:\n" + config.data[0]
				});
				break;
				
			case "delete":
				vm.errorMessages.push({
					message: "Could not remove path:\n" + config.data.oldPath + "\nTo:\n" + config.data.newPath
				});
				break;

			default:
				break;
			}
			
		}
		
		function getIndexFromPath(path){
			var index;
			for (var i = 0; i < vm.config.reportPaths.length; i++) {
				if (vm.config.reportPaths[i] === path) {
					return i;
				}
			}
		}
		
		function getOldPath(path){
			var startIndex = "\"oldPath\":";
			var endIndex = ",\"newPath\":";
			var oldPath = path.split(startIndex)[1];
			oldPath = oldPath.substring(0, oldPath.indexOf(endIndex));
			oldPath = oldPath.substring(1, oldPath.length - 1);
			return oldPath;
		}
		
		function getIndexFromError(msg){
			var i = msg.lastIndexOf("/");
			var index = parseInt(msg.substring(i+1));
			return index;
		}
	}
})();