angular
	.module('webLog', ['ui.router', 'ncy-angular-breadcrumb'])//.1
	
    .run(function(CurrentSuite, LocalStorage, $rootScope){ //.2
    	$rootScope.$broadcast("restoreState");
    })
    
    .config(function($urlRouterProvider, $stateProvider){//.3
    	
    $urlRouterProvider.otherwise("/home");//.4
    
		.state('home',{//.5
		    url:'/home',
		    templateUrl: 'app/components/home/home.html',
		    controller: 'HomeCtrl',
		    controllerAs: 'ctrl'
		})
        .state('reports',{
	        url:'/reports',
	        templateUrl: 'app/components/graph/reports.html',
	        controller: 'GraphCtrl',
	        controllerAs: 'ctrl',
	        cache: false,
	        ncyBreadcrumb: {
	        	skip:true
	          }
        })
        .state('reports.classes',{//.6
	        url:'/classes',
	        templateUrl: 'app/components/suite-info/classes.html',
	        controller: 'SuiteClassCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: { //.7
	        	parent:'dashboard',
	            label: 'Classes'
	          }
        })
		.state('live-tests',{
		    url:'/live-tests',
		    templateUrl: 'app/components/live-tests/live-tests.html',
		    controller: 'LiveTestsCtrl',
		    controllerAs: 'ctrl'
		});
    });

/*
 * Broken down and explained below ---------------------------------------------------------------------------------
 */

/*
 * Quick side note, to change between state, instead of going to a specific
 * URL as you would in a regular website, we will instead use
 * 
 * $state.transitionTo('nameOfState');
 * 
 * just make sure $state is injected into the controller or factory
 * or where ever you're trying to call it from.
 */

/*.1
 * 
 * If you have read the controller example you might remember
 * me talking about not using [] in there. Here we WILL
 * use it since this is the root of our module.
 * using:
 * 
 * angular.module('moduleName', ['outsideLibrary'])
 * 
 * tells angular so create a NEW module, and not a reference.
 * in the [] we pass in outside libraries, or in fact, outside modules.
 * this is where you will pass in your own external modules if you
 * decide to create multiple within the same project, this is seldom needed though.
 */
angular
	.module('webLog', ['ui.router','ncy-angular-breadcrumb'])
	
	/*.2
	 * 
	 * this .run() function will run before ANYTHING else is set up.
	 * it will wait for, and create, only what is passed in as a dependency.
	 * In this case it will setup only CurrentSuite.js, LocalStorage.js and $rootScope before running.
	 * This is a great spot if you need things to be setup before anything is ready for use.
	 * We use it in this app to load in data from local storage if any exists.
	 * LocalStorage listens for the event "restoreState" to be yells out and
	 * loads the data from local storage if it hears it.
	 */
	.run(function(CurrentSuite, LocalStorage, $rootScope){
		$rootScope.$broadcast("restoreState");
	})
	
	/*.3
	 * 
	 * The .config() is used to configure mostly the navigation for our site.
	 * There is generally two ways to handle this, by using URL paths, and by using states.
	 * Here we are using the state version.
	 * 
	 * We have injected the necessary dependencies to handle the states
	 * and will use $urlRouteProvider to set it all up
	 * by passing it values to different .state().
	 */
	.config(function($urlRouterProvider, $stateProvider){
		
	/*.4
	 * 
	 * states are loaded in <ui-view> tags and angular
	 * will switch out the content in the <ui-view> tag
	 * whenever a new state is loaded. This apps
	 * base <ui-view> is found in index.html
	 */
	$urlRouterProvider.otherwise("/home");
	
		/*.5
		 * 
		 * A state needs a name, in this case it is 'home'.
		 * This name will be used as a reference when we want to move
		 * around the app via $state.transitionTo() function.
		 * 
		 * In the below state the function would look like this:
		 * $state.transitionTo('home');
		 * and the app would move to the URL associated with the
		 * state name and load in the associated controller.
		 * 
		 * The 'url:' is what will be displayed in the URL field when
		 * the user is on said state. in this case '/home' will be displayed.
		 * 
		 * Each state also needs a templateUrl which points towards
		 * the HTML file to be displayed while the state is active
		 * in the <ui-view> tag.
		 * 
		 * The controller takes in a string which should be the same
		 * name as the stringified name used in the controllers
		 * js file. this makes sure the controller is created every
		 * time we enter this state and destroyed every time we leave it.
		 * 
		 * In other words, its init() method will run every time you enter.
		 * 
		 * controllerAs gives us the chance to set an alias for the controller.
		 * This is also required since I'm more or less forcing you to use the
		 * var vm = this; syntax. The reason behind this is simply readability.
		 * As you can see we call all controllers 'ctrl'. Thanks to this, when
		 * looking at each states HTML. if a function or variable is used with ctrl.functionName
		 * you know its the states OWN controller. if it's anything else or simply
		 * nothing in front of the variable or function, its from somewhere else.
		 */
		.state('home',{
		    url:'/home',
		    templateUrl: 'app/components/home/home.html',
		    controller: 'HomeCtrl',
		    controllerAs: 'ctrl'
		})
	    .state('reports',{
	        url:'/reports',
	        templateUrl: 'app/components/graph/reports.html',
	        controller: 'GraphCtrl',
	        controllerAs: 'ctrl',
	        cache: false,
	        ncyBreadcrumb: {
	        	skip:true
	          }
	    })
	    /*.6
	     * 
	     * A state can also have child states. If you look at 
	     * 
	     * app/components/graph/reports.html
	     * 
	     * you will find that it has its own <ui-view> tag at the bottom.
	     * 
	     * this is because when we enter the 'reports' state
	     * it will by default load its 'child state' reports.classes
	     * into its own <ui-view> tag. this tag will then in return
	     * swap its content between the different child states of
	     * reports while keeping the content in 'reports' where it is
	     * as long as we don't leave the reports state.
	     * 
	     * This is a child state which means that the url we set here, '/classes',
	     * will appear after its parent url.
	     * In this case the URL displayed would be: '/reports/classes'.
	     */
	    .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/suite-info/classes.html',
	        controller: 'SuiteClassCtrl',
	        controllerAs: 'ctrl',
	        
	        /*.7
	         * 
	         * The breadcrumbs are fetched via an outside module, ncy-angular-breadcrumb.
	         * 
	         * Add this attribute if you want this state to show up in a breadcrumb,
	         * an example of this in action can be found in the reports sub-states
	         * and screenshots sub-states. 
	         * 'parent:' value sets which state
	         * should precede this state in the breadcrumb list.
	         * 'label:' sets the name to be displayed for this state.
	         * 
	         * if we look at the 'reports' root state we will see
	         * that we added a 'skipped' value which is set to true.
	         * This is because the root state dosen't have its own state.
	         * It's a combination of its three sub-states and therefore
	         * shouldn't have its own link in the breadcrumb list.
	         * There for, we skip it.
	         */
	        ncyBreadcrumb: {
	        	parent:'dashboard',
	            label: 'Classes'
	          }
	    })
	    .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/suite-info/methods.html',
	        controller: 'SuiteMethodCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	        	parent:'reports.classes',
        	    label: 'Methods'
        	  }
        })
		.state('live-tests',{
		    url:'/live-tests',
		    templateUrl: 'app/components/live-tests/live-tests.html',
		    controller: 'LiveTestsCtrl',
		    controllerAs: 'ctrl'
		});
	});