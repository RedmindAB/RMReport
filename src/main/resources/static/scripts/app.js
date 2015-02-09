angular.module('webLog', ['ui.router','ui.bootstrap','n3-line-chart',"highcharts-ng"])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    $stateProvider
        .state('home',{
            url: '/home',
            templateUrl: 'partials/home.html',
            controller: ''
        })
        .state('reports',{
	        url:'/reports',
	        templateUrl: 'partials/reports.html',
	        controller: ''
        })
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'partials/classes.html',
	        controller: ''
        })
        .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'partials/methods.html',
	        controller: ''
        })
        .state('reports.drivers',{
	        url:'/drivers',
	        templateUrl: 'partials/drivers.html',
	        controller: ''
        })
	    .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'partials/cases.html',
	        controller: ''
	    });
    });


