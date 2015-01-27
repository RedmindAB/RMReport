angular.module('webLog', ['ui.router', 'chart.js','ui.bootstrap'])
    .config(function($urlRouterProvider, $stateProvider){

    $stateProvider
        .state('home',{
            url: '/',
            templateUrl: 'partials/home.html',
            controller: ''
        })
        .state('error',{
        url:'/error',
        templateUrl: 'partials/error.html',
        controller: 'ErrorCtrl'
        })
        .state('testCases',{
        url:'/test-case',
        templateUrl: 'partials/test-case.html',
        controller: 'TestCtrl'
        })     
        .state('testSuites',{
        url:'/test-suites',
        templateUrl: 'partials/test-suites.html',
        controller: 'TestCtrl'
        })          
    });


