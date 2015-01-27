angular.module('webLog', ['ui.router', 'chart.js','ui.bootstrap'])
    .config(function($urlRouterProvider, $stateProvider){
    	
    $urlRouterProvider.otherwise("/");
    
    $stateProvider
        .state('home',{
            url: '/',
            templateUrl: 'partials/home.html',
            controller: ''
        })
        .state('testCases',{
	        url:'/test-case',
	        templateUrl: 'partials/test-case.html',
	        controller: ''
        })     
        .state('testSuites',{
	        url:'/test-suites',
	        templateUrl: 'partials/test-suites.html',
	        controller: ''
        })          
    });


