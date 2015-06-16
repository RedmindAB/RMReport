(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
	    return {
	        restrict: 'E',
	        controller: 'SuiteInfoCtrl',
	        controllerAs: 'ctrl',
	        scope: { iterationObject: '=', toState: '='},
	        template: 	"<div>" +
					       "<accordion id='class-group' close-others='true'>" +
					        "<div" +
					        	"id='class-name'" + 
					        	"class='class-method-container'" +
					        	"style='margin-top:10px'" + 
					        	"ng-repeat='object in iterationObject| filter: Utilities.searchField | orderBy:Utilities.sorting:reverse track by $index'" +
					            "ng-class='style.getPanel(class.result)'" +
					            "ng-style='{'background-color': style.getBgCo(class.result)}'>" +
					            "<table width='100%'>" +
					                "<tr>" +
					                    "<td class='checkbox-holder'>" +
					                        "<icon-checkbox content='class' id='checkbox{{$index}}' ></icon-checkbox>" +				                    
					                    "</td>" +
					                    "<td " +
					                    	"id='class-{{$index}}'"+ 
					                    	"ng-click='ctrl.getMethods(object); setState(toState);'" +
					                    	"ng-style='{'color' : style.getColor(object.result)}'" +
					                    	"class='icon-checkbox-guard'>" +
					                        "<p "+
					                        	"id='class-name-{{$index}}'" +
					                        	"class='accordion-name-container text-left'>"+
					                        	"{{object.name}}"+
					                        "</p>" +
					                        "<p " +
					                        	"id='class-runtime-{{$index}}'" +
					                        	"class='accordion-name-container text-right'>" +
					                        	"{{style.formatDecimals(object.time)}}" +
					                        "</p>" +
					                        "<p " +
					                        	"id='class-passfail-{{$index}}'" +
					                        	"class='accordion-name-container text-right'" +
					                        	"style='margin-right:10px'>" +
					                        	"Skipped: {{object.stats.skipped}} Failed: {{object.stats.totFail}}" +
					                        "</p>" +
					                    "</td>" +
					                "</tr>" +
					            "</table>" +
					        "</div>" +
					    "</accordion>" +
					"/div>"
	    };
	}
})();