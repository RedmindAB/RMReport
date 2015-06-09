angular.module('webLog')
    .directive('messageList', function(){
      return {
        restrict: 'E',
        scope: false,
        template: 
	      "<div id='messages'>" + 
		      "<div class='modal-dialog' style='width:100%' align='center'>" +
		      	"<div class='modal-content' ng-repeat='platform in ctrl.platforms' style='display:inline-block;margin-right:10px'>" + 
		      		"<div id='errorMessageDiv' class='modal-header'>{{platform}}</div>" + 
		      		"<div id='errorMessageDiv' class='modal-body'>" +
		      			"<div ng-repeat='platform in ctrl.DeviceData.devices'>"+
		      			"<div ng-repeat='device in platform'>Total Tests: {{device.total}}<br>Total Fail: {{device.totalFail}}</div></div>" + 
		      			"<div ng-if='!ctrl.DeviceData.devices.length'><span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Loading...</div>" +
		      		"</div>" + 
		      	"</div>" +
		      "</div>" +
	      "</div>"
      };
    });