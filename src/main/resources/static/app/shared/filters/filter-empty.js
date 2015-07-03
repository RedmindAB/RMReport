(function(){
	'use strict';
	
	angular
		.module('webLog')
		.filter('FilterEmpty', FilterEmpty);
	
	function FilterEmpty(){
		function filter(array, key){
			var present = array.filter(function(item){
				return item[key];
			});
			var empty = array.filter(function(item){
				return !item[key];
			});
			return present.concat(empty);
		}
		return filter;
	}
})();