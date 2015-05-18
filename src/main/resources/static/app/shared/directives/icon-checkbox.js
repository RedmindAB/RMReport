angular.module('webLog')
.directive('iconCheckbox', function() {
    return {
        restrict: 'E',
        scope: { content: '='},
        template: 	"<span ng-if='content.chosen' class='glyphicon glyphicon-check' ng-click='setChosen(content);newContent()'></span>" +
					"<span ng-if='!content.chosen' class='glyphicon glyphicon-unchecked' ng-click='setChosen(content);newContent()'></span>",
		controller: 'GraphCtrl'
    };
});
