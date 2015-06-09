(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('GridData', gridData);
	
	function gridData(){
		return {
			data:{}
		};
	}
})();