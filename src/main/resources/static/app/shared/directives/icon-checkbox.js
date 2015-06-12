(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('iconCheckbox', IconCheckBox);
	
	function IconCheckBox () {
	    return {
	        restrict: 'E',
	        controller: 'GraphCtrl',
	        controllerAs: 'graphCtrl',
	        scope: { content: '='},
	        template: 	"<span " +
	        				"ng-if='content.chosen' " +
	        				"class='glyphicon glyphicon-check' " +
	        				"ng-click='graphCtrl.setChosen(content);graphCtrl.newContent()' " +
	        				"style='cursor:pointer'>" +
	        			"</span>" +
						"<span " +
							"ng-if='!content.chosen' " +
							"class='glyphicon glyphicon-unchecked' " +
							"ng-click='graphCtrl.setChosen(content);graphCtrl.newContent()' " +
							"style='cursor:pointer'>" +
						"</span>",
	    };
	}
})();