(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
		
		var directive = {
				controller: controller,	//.1
		        controllerAs: 'ctrl',
		        scope: { 				//.2
		        	iterationObject: '=',
		        	loadFunction: '&',
		        	loadFunctionTwo: '&',
		        	toState: '=', 
		        	type: '@',
		        	showCheckBox: '=',
		        	hideOn: '='
		        },
		        link: function(scope, element, attrs) { //.3
		            scope.predicate = 'stats.totFail';
		            scope.reverseSort = true;

		            scope.sortBy = function(sorting){
		            	scope.predicate = sorting;
		            	scope.reverseSort = !scope.reverseSort;
		            };
		        },
		        templateUrl: 'app/shared/directives/suite-info-partial/suite-info-partial.html'//.4
		};
		
		function controller(Utilities){ //.5
			
			var vm = this;
			
			vm.Utilities = Utilities;
			
		}
		
	    return directive;
	}
})();

/*
 * Broken down and explained below ---------------------------------------------------------------------------------
 */

//basic setup is explained in the controller-example

(function(){
	'use strict';
	
	angular
		.module('webLog')
		.directive('suiteInfoPartial', SuiteInfoPartial);
	
	function SuiteInfoPartial () {
		
		/*
		 * The directive is built in somewhat the same manner as the factory.
		 * we create a service object in which we store all the functions
		 * and values we want to be a part of the directive.
		 * any functions are generally declared outside of it.
		 * 
		 * at the bottom we return the directive object. 
		 * 
		 * more info on this in the factory-example.
		 */
		var directive = {
				/*.1
				 * 
				 * 'controller:' defined a controller to be used for a directive.
				 * this can be an outside controller, created in its own file.
				 * Or, as in this case, a simpler controller made in the same file below.
				 * We want to use the controllerAs syntax here as well for consistency.
				 * 
				 * More on this in the controller example.
				 * 
				 * Generally you want to create a controller if you want access to injection.
				 * In this directive we use it to gain access to the Utilities factory.
				 */
				controller: controller,
		        controllerAs: 'ctrl',
		        
		        /*.2
		         * 
		         * By default a directive inherits its 'contexts' scope.
		         * This means that if the directive is created multiple times
		         * in a div, they all share that divs scope. A consequence of this
		         * is the following example:
		         * 
		         * Lets say that we in the directive create a var counter. 
		         * Every time an instance of this directive is used in the HTML the counter is increased
		         * by one and rendered to the HTML. You might think that each element in turn
		         * would display an increasing number such as: elementOne: 1, elementTwo:2, elementThree: 3.
		         * 
		         * What actually would be rendered is elementOne: 3, elementTwo: 3, elementThree: 3.
		         * This is because they all share the same scope and will share the same value of the counter variable.
		         * 
		         * If we want to fight this and make them independent we create a 'scope:' value in the directive.
		         * This way each time this directive is used in the HTML it gains its own scope and the 
		         * values would be elementOne: 1, elementTwo: 2, elementThree: 3.
		         * 
		         * 'scope:' is also where we make it possible to pass values into the directive from
		         * outside. You will notice that the scope value has a number of values inside it.
		         * One example is "type: '='". what this means is that IF you on the element
		         * adds "type='Object'" the 'type' value is then usable within the directive as well.
		         * 
		         * More on how this looks in the HTML-example.
		         * 
		         * One thing that is important to note here is that camelCase in the java script
		         * gets translated into '-' separated names in the HTML.
		         * loadFunctionTwo becomes "load-function-two='functionToBeUsed()'"
		         * 
		         * You can however choose another name to be used in the HTML by adding it after the '='
		         * such as "loadFuntion: '=method'". That would make it "method='functionToBeUsed()'"
		         * which will then be stored in the value loadFunctionTwo.
		         * 
		         * The '=','@','&' is a description of what is passed in and there is a few alternatives. 
		         * Note that whichever of these you use the syntax in HTML will always be "name='inputValue'".
		         * 
		         * '=' creates a two way binding. If it's changed outside, it's also changed in here and vice versa.
		         * '@' is a one way binding. If it's changed outside it's NOT changed in here and vice versa.
		         * '&' tells the directive that this is a function and will bind it to the parent function.
		         * 
		         */
		        scope: { 
		        	iterationObject: '=',
		        	loadFunction: '&',
		        	loadFunctionTwo: '&',
		        	toState: '=', 
		        	type: '@',
		        	showCheckBox: '=',
		        	hideOn: '='
		        },
		        
		        /*.3
		         * 
		         * This is where we can add additional values to the directive which
		         * dosen't need to be passed in from the outside.
		         * The link function has access to the directives scope,
		         * the actual element it sits on and the attrs.
		         * attrs is as you might have guessed short for attributes and
		         * refers to the values inside of 'scope:{}' right above this.
		         * 
		         * Here you can allso instantiate new functions on the scope of the
		         * directive. anything created in here with the scope.name syntax
		         * is usable in the template explained below.
		         */
		        link: function(scope, element, attrs) {
		        	
		        	console.log(attrs.type); //logs the the type value from scope:{}
		        	
		            scope.predicate = 'stats.totFail';
		            scope.reverseSort = true;

		            scope.sortBy = function(sorting){
		            	scope.predicate = sorting;
		            	scope.reverseSort = !scope.reverseSort;
		            };
		        },
		        
		        /* .4
		         * 
		         * Nothing special here. Path to the HTML file we want to use
		         * for this directive. If you only want to create a minor directive
		         * you can also create the mark up in a string:
		         * templateUrl: "<div><p>awesome content</p></div>"
		         */
		        templateUrl: 'app/shared/directives/suite-info-partial/suite-info-partial.html'
		};
		
		/*
		 * The small controller assigned further up in the 'controller:' value.
		 * Only used here to gain access to the Utilities factory.
		 */
		function controller(Utilities){//.5
			
			var vm = this;
			
			vm.Utilities = Utilities;
			
		}
		
		//and here we return the directive so it's usable.
	    return directive;
	}
})();