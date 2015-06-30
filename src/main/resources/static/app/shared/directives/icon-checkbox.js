(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('iconCheckbox', IconCheckBox);
	
	function IconCheckBox () {
		
		var directive = {
				restrict: 'E',
		        scope: { content: '='},
		        link: link,
		        template: 	"<span " +
		        				"ng-if='content.chosen' " +
		        				"class='glyphicon glyphicon-check' " +
		        				"ng-click='setChosen(content);'" +
		        				"style='cursor:pointer'>" +
		        			"</span>" +
							"<span " +
								"ng-if='!content.chosen' " +
								"class='glyphicon glyphicon-unchecked' " +
								"ng-click='setChosen(content);' " +
								"style='cursor:pointer'>" +
							"</span>"
		};
		
		return directive;
		
		function link(scope, element, attrs){
			scope.setChosen = function(value){
		    	if(scope.content.chosen){
		    		delete scope.content.chosen;
		    	}
		    	else{
		    		scope.content.chosen = true;
		    	}
			}
		}
	}
})();