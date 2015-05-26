angular
	.module('webLog', [
			           'ui.router',
			           'ui.bootstrap',
			           'highcharts-ng', 
			           'ngAnimate', 
			           'ngTouch'
			           ])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    //states
    $stateProvider
        .state('home',{
            url: '/home',
            templateUrl: 'app/components/dashboard/home.html',
            controller: 'DashboardCtrl'
        })
        .state('reports',{
	        url:'/reports',
	        templateUrl: 'app/components/graph/reports.html',
	        controller: 'GraphCtrl'
        })
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/suite-info/classes.html',
	        controller: 'SuiteInfoCtrl'
        })
        .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/suite-info/methods.html',
	        controller: 'SuiteInfoCtrl'
        })
        .state('reports.drivers',{
	        url:'/drivers',
	        templateUrl: 'app/components/suite-info/drivers.html',
	        controller: 'SuiteInfoCtrl'
        })
	    .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'app/components/suite-info/cases.html',
	        controller: 'SuiteInfoCtrl'
	    })
	    .state('screenshots',{
	        url:'/screenshots',
	        templateUrl: 'app/components/screenshots/screen-shots.html',
	        controller: 'ScreenshotCtrl'
	    })
	    .state('screenshots.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/screenshots/screenshot-classes.html',
	        controller: ''
	    })
	    .state('screenshots.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/screenshots/screenshot-methods.html',
	        controller: ''
	    })
	    .state('grid',{
	        url:'/grid',
	        templateUrl: 'app/components/grid/grid.html',
	        controller: ''
	    })
		.state('admin',{
		    url:'/admin',
		    templateUrl: 'app/components/admin/admin.html',
		    controller: 'AdminCtrl',
		    controllerAs: 'ctrl'
		});
    });