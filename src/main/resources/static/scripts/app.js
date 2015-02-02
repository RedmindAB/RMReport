angular.module('webLog', ['ui.router', 'chart.js','ui.bootstrap','n3-line-chart'])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    $stateProvider
        .state('home',{
            url: '/home',
            templateUrl: 'partials/home.html',
            controller: 'HomeCtrl'
        })
        .state('reports',{
	        url:'/reports',
	        templateUrl: 'partials/reports.html',
	        controller: 'SuitesCtrl'
        })
        .state('reports.classes',{
	        url:'/classes',
	        templateUrl: 'partials/classes.html',
	        controller: 'SuitesCtrl'
        })
        .state('reports.methods',{
	        url:'/methods',
	        templateUrl: 'partials/methods.html',
	        controller: 'SuitesCtrl'
        });

    
    
    });


