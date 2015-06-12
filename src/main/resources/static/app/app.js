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
	        ncyBreadcrumb: {
	        	skip:true
	          }
        })
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/suite-info/classes.html',
	        controller: 'SuiteInfoCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	        	parent:'dashboard',
	            label: 'Classes'
	          }
        })
        .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/suite-info/methods.html',
	        controller: 'SuiteInfoCtrl',
	        controllerAs: 'ctrl',
	        ncyBreadcrumb: {
	        	parent:'reports.classes',
        	    label: 'Methods'
        	  }
        })
        .state('reports.drivers',{
	        url:'/drivers',
	        templateUrl: 'app/components/suite-info/drivers.html',
	        controller: 'SuiteInfoCtrl'
        })
	    .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'app/components/suite-info/cases.html',
	        controller: 'SuiteInfoCtrl',
	        controllerAs: 'ctrl',
        	ncyBreadcrumb: {
        		parent: 'reports.methods',
        	    label: 'Cases'
        	  }
	    })
	    .state('screenshots',{
	        url:'/screenshots',
	        templateUrl: 'app/components/screenshots/screen-shots.html',
	        controller: 'ScreenshotCtrl',
	        ncyBreadcrumb: {
	        	skip:true
        	  }
	    })
	    .state('screenshots.classes',{
	        url:'/classes',
	        templateUrl: 'app/components/screenshots/screenshot-classes.html',
	        controller: '',
	        ncyBreadcrumb: {
	        	parent:'dashboard',
        	    label: 'Classes'
        	  }
	    })
	    .state('screenshots.methods',{
	        url:'/methods',
	        templateUrl: 'app/components/screenshots/screenshot-methods.html',
	        controller: '',
	        ncyBreadcrumb: {
	        	parent:'screenshots.classes',
        	    label: 'Methods'
        	  }
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