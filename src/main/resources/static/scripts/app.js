angular.module('webLog', ['ui.router'])
    .config(function($urlRouterProvider, $stateProvider){

    $stateProvider
        .state('home',{
            url: '/',
            templateUrl: 'partials/home.html',
            controller: 'HomeCtrl'
        })
        .state('error',{
        url:'/error',
        templateUrl: 'partials/error.html',
        controller: 'ErrorCtrl'
        })
        .state('graphview',{
        url:'/graphview',
        templateUrl: 'partials/graphview.html',
        controller: 'ErrorCtrl'
        })         
        .state('logs',{
        url:'/logs',
        templateUrl: 'partials/logs.html',
        controller: 'LogsCtrl'
        });

        $urlRouterProvider.otherwise('/');
    });


