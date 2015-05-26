angular
	.module('webLog')
	.directive('iconCheckbox', IconCheckBox);

function IconCheckBox () {
    return {
        restrict: 'E',
        scope: { content: '='},
        template: 	"<span " +
        				"ng-if='content.chosen' " +
        				"class='glyphicon glyphicon-check' " +
        				"ng-click='setChosen(content);newContent()' " +
        				"style='cursor:pointer'>" +
        			"</span>" +
					"<span " +
						"ng-if='!content.chosen' " +
						"class='glyphicon glyphicon-unchecked' " +
						"ng-click='setChosen(content);newContent()' " +
						"style='cursor:pointer'>" +
					"</span>",
		controller: 'GraphCtrl'
    };
};

