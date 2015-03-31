angular.module('webLog')
.directive('modalDialogSysos', ['$rootScope', function($rootScope) {
  return {
    restrict: 'E',
    scope: {
      show: '='
    },
    replace: true, // Replace with the template below
    transclude: true, // we want to insert custom content inside the directive
    link: function(scope, element, attrs) {
      scope.dialogStyle = {};
      if (attrs.width)
        scope.dialogStyle.width = "100%";
      if (attrs.height)
        scope.dialogStyle.height = "100%";
      scope.hideModal = function() {
        scope.show = false;
        $rootScope.$broadcast("closeModal2");
      };
    },
    template: 	"<div class='ng-modal' ng-show='show'>" +
    				"<div class='ng-modal-overlay'></div>" +
    				"<div class='ng-modal-dialog' ng-style='dialogStyle'>" +
    					"<div class='ng-modal-close' ng-click='hideModal()'>X</div>" +
    					"<div class='ng-modal-dialog-content' ng-transclude></div>" +
    				"</div>" +
    			"</div>"
  };
}]);