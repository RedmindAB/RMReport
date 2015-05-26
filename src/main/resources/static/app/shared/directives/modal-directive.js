(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('modalDialog', modalDialog);
	
	modalDialog.$inject = ['$rootScope'];
	
	function modalDialog ($rootScope) {
	  return {
	    restrict: 'E',
	    scope: {
	      show: '=',
	      toggleModal: '&',
	    },
	    replace: true, // Replace with the template below
	    transclude: true, // we want to insert custom content inside the directive
	    link: function(scope, element, attrs) {
	      scope.dialogStyle = {};
	      if (attrs.width) {
	    	  scope.dialogStyle.width = "100%";
	      }
	      
	      if (attrs.height) {
	    	  scope.dialogStyle.height = "100%";
	      }
	    },
	    template: 	"<div class='ng-modal' ng-show='show'>" +
	    				"<div class='ng-modal-overlay' ng-click='toggleModal()'></div>" +
	    				"<div class='ng-modal-dialog' ng-style='dialogStyle'>" +
	    					"<div " +
	    						"id='close' " +
	    						"class='ng-modal-close' " +
	    						"ng-click='toggleModal()'>" +
	    						"X" +
	    					"</div>" +
	    					"<div class='ng-modal-dialog-content' ng-transclude></div>" +
	    				"</div>" +
	    			"</div>"
	  };
	};
})();