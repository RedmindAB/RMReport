(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LiveData', LiveData);
	
	function LiveData () {
		var service = {
				testData:[],
				tests:[],
				historyid:0,
				percentage:"0%",
				currentPercentage: "0%",
				uuid:"",
				timeKeeper:{},
				getRunTime: getRunTime
		};
		
		function getRunTime(id){
			return timeKeeper[id];
		}
		
		return service;
		
	}
})();