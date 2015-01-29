angular.module('webLog', ['ui.router', 'chart.js','ui.bootstrap'])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/home");
    
    $stateProvider
        .state('home',{
            url: '/home',
            templateUrl: 'partials/home.html',
            controller: 'HomeCtrl'
        })
        .state('testCases',{
	        url:'/test-case',
	        templateUrl: 'partials/test-case.html',
	        controller: 'TestCaseCtrl'
        })     
        .state('testSuites',{
	        url:'/test-suites',
	        templateUrl: 'partials/test-suites.html',
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
        })
    });


