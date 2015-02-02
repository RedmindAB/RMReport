angular.module('webLog', ['ui.router', 'chart.js','ui.bootstrap'])
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
        .state('reports.cases',{
	        url:'/cases',
	        templateUrl: 'partials/cases.html',
	        controller: 'SuitesCtrl'
        })
        .state('testCases',{
	        url:'/test-case',
	        templateUrl: 'partials/test-case.html',
	        controller: 'SuitesCtrl'
        })     
        .state('suiteRunClasses',{
	        url:'/suite-run-classes',
	        templateUrl: 'partials/suite-run-classes.html',
	        controller: 'SuitesCtrl'
        })
        .state('project',{
	        url:'/project',
	        templateUrl: 'partials/project.html',
	        controller: 'ProjectCtrl'
        })
        .state('suiteRuns',{
	        url:'/test-suite-runs',
	        templateUrl: 'partials/test-suite-runs.html',
	        controller: 'SuitesCtrl'
        });
    
    });


