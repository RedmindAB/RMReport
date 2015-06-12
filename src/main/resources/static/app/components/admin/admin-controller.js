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
		
		vm.addPath 			= addPath;
		vm.isNewPath 		= isNewPath;
		vm.isPathChanged 	= isPathChanged;
		vm.isToBeRemoved 	= isToBeRemoved;
		vm.readErrorMessage = readErrorMessage;
		vm.removePath 		= removePath;
		vm.saveChanges 		= saveChanges;
		vm.toggleErrorModal = toggleErrorModal;
		
		loadRootConfig();
		
		/* 
		 * Adds parameter as a path to the config object which
		 * will be iterated through when calling the add service.
		 * 
		 * @param {String} Path to add
		 */
		function addPath(path){
			if(path.length > 0){
				if (!pathExists(path)) {
					vm.configCompare.reportPaths.push(path);
				} else {
					vm.errorMessages.push({index: -1, message: "Path already exists"});
				}
				vm.newPath = '';
			}
		}
		
		/*
		 * Checks if a path exists in vm.configCompare.removelist
		 * at the given index
		 * 
		 * @param {Integer} Index to check
		 * @return {Boolean} Returns true if exists
		 */
		function isToBeRemoved (index) {
			var path = vm.config.reportPaths[index];
			return vm.configCompare.removeList.indexOf(path) != -1;
		}
		
		/*
		 * Checks if the path in vm.configCompare at an index
		 * still is the same as the corresponding path in
		 * vm.config at the same index
		 * 
		 * @param {Integer} Index to check
		 * @return {Boolean} Returns true of they are not the same
		 */
		function isPathChanged (index) {
			return vm.configCompare.reportPaths[index] !== vm.config.reportPaths[index];
		}
		
		/*
		 * Checks if the index passed in is higher than the
		 * length of the path array in vm.config to see
		 * if the path is new since last loadAll()
		 * 
		 * @param {Integer} Index to check
		 * @return {Boolean} returns true if index is bigger
		 */
		function isNewPath (index) {
			return index >= vm.config.reportPaths.length;
		}
		
		//toggles the error message modal
		function toggleErrorModal() {
			vm.errorModalShown = !vm.errorModalShown;
		}

		/*
		 * Removes the index of a path from the config object
		 * wich will be passed as an argument to the removePaths call.
		 * If the index is a new, not yet added path, it will be removed
		 * instantly.
		 * 
		 * @param {Integer} indexof path to remove
		 */
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
		
		/*
		 * Loads the content from config.json and stores it
		 * first in vm.config for reference and copies it into
		 * vm.configCompare which will be used for alteration.
		 * vm.configCompare will be compared to vm.config at the
		 * time of saving to ensure the right data will be sent.
		 */
		function loadRootConfig(){
			
			AdminServices.loadRootConfig()
			.then(function(data){
				vm.config = data.data;
				angular.copy(vm.config,vm.configCompare);
				vm.configCompare.removeList = [];
			});
		}
		
		/*
		 * Runs changePaths(), removePaths, addPaths() and loadRootConfig()
		 * in chronological order and adds error messages to the
		 * error vm.errorMessages array if failure.
		 * If vm.errorMessages is NOT empty at the end it
		 * will toggle the error message modal.
		 */
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
				if (vm.errorMessages.length > 0) {
					vm.toggleErrorModal();
				}
			});
		}
		
		/*
		 * Iterates through all paths in vm.configCompare.reportPaths
		 * and checks if they are tagged to be changed. If they are
		 * NOT tagged to be removed and NOT tagged as a new path
		 * they will be added to an array which will be sent via
		 * REST to back end in AdminServices.changePaths to
		 * change given paths in config.json.
		 */
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
		
		/*
		 * Iterates through all paths in vm.configCompare.reportPaths
		 * and checks if they are tagged as a new path. If they are
		 * they are added to an array and sent via REST to back end
		 * in AdminServices.addPaths to add the new paths
		 * to config.json.
		 */
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
		
		/*
		 * passes the vm.configCompare.removeList to AdminServices.removePaths
		 * to send a RESTful call to remove all paths within the array
		 * from config.json
		 */
		function removePaths(){
			var request = vm.configCompare.removeList;
			return AdminServices.removePaths(request, addErrorMessage);
		}
		
		/*
		 * Checks if given path exists in vm.configCompare.reportPaths.
		 * 
		 * @param {String} Path to check
		 * @return {Boolean} returns true if path exists.
		 */
		function pathExists(path){
			for (var i = 0; i < vm.configCompare.reportPaths.length; i++) {
				if (vm.configCompare.reportPaths[i] === path) {
					return true;
				}
			}
			return false;
		}
		
		/*
		 * Iterates through vm.errorMessages and returns
		 * the message at given index.
		 * 
		 * @param {Integer} index to fetch
		 * @return {String} message at index
		 */
		function readErrorMessage(index){
			for (var i = 0; i < vm.errorMessages.length; i++) {
				if (vm.errorMessages[i].index === index) {
					return vm.errorMessages[i].message;
				}
			}
		}
		
		/*
		 * Pushes an error message to vm.errorMessages array
		 * with a pre-determined message depending on which
		 * string is passed in.
		 * 
		 * @param {Object} config object from REST response
		 * @param {String} text to fit into switch case
		 */
		function addErrorMessage(config, errorType){
			
			switch (errorType) {
			case "change":
				vm.errorMessages.push({
					message: "Could not change path:\n" + config.data.oldPath + "\nTo:\n" + config.data.newPath
				});
				break;
				
			case "create":
				for(var i = 0; i < config.length; i++){
					vm.errorMessages.push({
						message: config[i]
					});
				}
				break;
				
			case "delete":
				vm.errorMessages.push({
					message: "Could not remove path:\n" + config.data[0]
				});
				break;

			default:
				break;
			}
			
		}
		
		/*
		 * Return the index in vm.config.reportPaths of the given path
		 * 
		 * @param {String} path to find
		 * @return {Integer} index of path
		 */
		function getIndexFromPath(path){
			var index;
			for (var i = 0; i < vm.config.reportPaths.length; i++) {
				if (vm.config.reportPaths[i] === path) {
					return i;
				}
			}
		}
		
		/*
		 * Breaks out the old path from REST response message.
		 * Starts from after \"oldPath\" and ends before
		 * \"newPath\" and returns the string in between.
		 * 
		 *  @param {String} REST message to split
		 *  @return {String} string in between
		 */
		function getOldPath(path){
			var startIndex = "\"oldPath\":";
			var endIndex = ",\"newPath\":";
			var oldPath = path.split(startIndex)[1];
			oldPath = oldPath.substring(0, oldPath.indexOf(endIndex));
			oldPath = oldPath.substring(1, oldPath.length - 1);
			return oldPath;
		}
	}
})();