(function () {
    angular.element(document).ready(function () {
        var app = angular.module('webLog', []);

        app.controller("Ctrl", function ($scope, repo, poollingFactory) {
            $scope.calledServer;
            poollingFactory.callFnOnInterval(function () {
                $scope.calledServer = repo.callServer(5);
            });
        });

        app.factory("repo", function () {

            function callServer(id) {
                return "call server with id " + id + " at: " + (new Date());
            }

            return {
                callServer: callServer
            };
        });

        app.factory("poollingFactory", function ($timeout) {

            var timeIntervalInSec = 1;
            
            function callFnOnInterval(fn, timeInterval) {

                var promise = $timeout(fn, 1000 * timeIntervalInSec);
                
                return promise.then(function(){
                    callFnOnInterval(fn, timeInterval);
                });
            };

            return {
                callFnOnInterval: callFnOnInterval
            };
        });

    });


}());