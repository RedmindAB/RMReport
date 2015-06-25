(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LiveData', LiveData);
	
	function LiveData () {
		return {
			testData:[],
			tests:[],
			historyid:0
		};
	}
})();