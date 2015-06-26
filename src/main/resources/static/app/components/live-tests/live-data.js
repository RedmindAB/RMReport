(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LiveData', LiveData);
	
	function LiveData () {
		return {
			testData:[],
			tests:[],
			historyid:0,
			percentage:"0%",
			currentPercentage: "0%"
		};
	}
})();