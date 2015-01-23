angular.module('webLog')
    .controller('ErrorCtrl', ['$scope',function($scope){
		$scope.message = "Error Reports";
		$scope.list = {
			name: "Mattias",
			age: 20,
			skills: ["java", "javascript", "html"]
		};
    }]);