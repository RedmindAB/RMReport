angular
	.module('webLog', [
			           'ui.router',
			           'ui.bootstrap',
			           'highcharts-ng', 
			           'ngAnimate', 
			           'ngTouch',
			           'ncy-angular-breadcrumb'
			           ])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    //states
    $stateProvider
        .state('dashboard',{
            url: '/dashboard',
            templateUrl: 'app/components/dashboard/dashboard.html',
            controller: 'DashboardCtrl',
            controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	            label: 'Home'
	          }
        })
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
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/suite-info/classes.html',
	        controller: 'SuiteClassCtrl',
	        controllerAs: 'ctrl',
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
	    .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'app/components/suite-info/cases.html',
	        controller: 'SuiteCaseCtrl',
	        controllerAs: 'ctrl',
        	ncyBreadcrumb: {
        		parent: 'reports.methods',
        	    label: 'Cases'
        	  }
	    })
	    .state('screenshots',{
	        url:'/screenshots',
	        templateUrl: 'app/components/screenshots/screen-shots.html',
	        ncyBreadcrumb: {
	        	skip:true
        	  }
	    })
	    .state('screenshots.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/screenshots/screenshot-classes.html',
	        controller: 'ScreenshotClassCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	        	parent:'dashboard',
        	    label: 'Classes'
        	  }
	    })
	    .state('screenshots.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/screenshots/screenshot-methods.html',
	        controller: 'ScreenshotMethodCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	        	parent:'screenshots.classes',
        	    label: 'Methods'
        	  }
	    })
	    .state('grid',{
	        url:'/grid',
	        templateUrl: 'app/components/grid/grid.html',
	        controller: 'GridCtrl',
	        controllerAs: 'ctrl'
	    })
		.state('admin',{
		    url:'/admin',
		    templateUrl: 'app/components/admin/admin.html',
		    controller: 'AdminCtrl',
		    controllerAs: 'ctrl'
		})
		.state('live-tests',{
		    url:'/live-tests',
		    templateUrl: 'app/components/live-tests/live-tests.html',
		    controller: 'LiveTestsCtrl',
		    controllerAs: 'ctrl'
		});
    });