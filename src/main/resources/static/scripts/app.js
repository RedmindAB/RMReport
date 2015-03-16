angular.module('webLog', ['ui.router','ui.bootstrap',"highcharts-ng", 'ngAnimate', 'ngTouch'])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    $stateProvider
        .state('home',{
            url: '/home',
            templateUrl: 'partials/home.html',
            controller: 'NavCtrl'
        })
        .state('reports',{
	        url:'/reports',
	        templateUrl: 'partials/reports.html',
	        controller: 'NavCtrl'
        })
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'partials/sub-partials/classes.html',
	        controller: ''
        })
        .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'partials/sub-partials/methods.html',
	        controller: ''
        })
        .state('reports.drivers',{
	        url:'/drivers',
	        templateUrl: 'partials/sub-partials/drivers.html',
	        controller: ''
        })
	    .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'partials/sub-partials/cases.html',
	        controller: ''
	    })
	    .state('screenshots',{
	        url:'/screenshots',
	        templateUrl: 'partials/screen-shots.html',
	        controller: 'ScreenshotCtrl'
	    })
	    .state('screenshots.classes',{
	        url:'/classes',
	        templateUrl: 'partials/screenshot-partials/screenshot-classes.html',
	        controller: 'NavCtrl'
	    })
	    .state('screenshots.methods',{
	        url:'/methods',
	        templateUrl: 'partials/screenshot-partials/screenshot-methods.html',
	        controller: 'NavCtrl'
	    });
    });


