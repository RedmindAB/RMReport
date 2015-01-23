angular.module('webLog', ['ui.router'])
    .config(function($urlRouterProvider, $stateProvider){

    $stateProvider
        .state('home',{
            url: '/',
            templateUrl: 'partials/test.html',
            controller: 'HelloCtrl'
        })
        .state('error',{url:'/error',
        templateUrl: 'partials/error.html',
        controller: 'HelloCtrl'
        });

        $urlRouterProvider.otherwise('/');
    });


