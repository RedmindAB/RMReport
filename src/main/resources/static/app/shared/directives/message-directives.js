angular.module('webLog')
    .directive('messageList', function(){
      return {
        restrict: 'E',
        scope: false,
        template: 
	      "<div id='messages'>" + 
		      "<div class='modal-dialog' style='width:100%' align='center'>" +
		      	"<div class='modal-content' ng-repeat='platform in platforms' style='display:inline-block;margin-right:10px'>" + 
		      		"<div ui-if='errorMessage' id='errorMessageDiv' class='modal-header'>{{platform}}</div>" + 
		      		"<div ui-if='errorMessage' id='errorMessageDiv' class='modal-body'>Overtime Data</div>" +
		      	"</div>" +
		      "</div>" +
	      "</div>"
      }
    });