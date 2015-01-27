angular.module('webLog', ["ui.router", "chart.js"])
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
        .state('graphview',{
        url:'/graphview',
        templateUrl: 'partials/graphview.html',
        controller: 'GraphViewCtrl'
        })     
        .state('testSuites',{
        url:'/test-suites',
        templateUrl: 'partials/test-suites.html',
        controller: 'TestCtrl'
        })          
    });


