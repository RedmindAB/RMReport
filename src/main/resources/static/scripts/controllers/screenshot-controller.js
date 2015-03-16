angular.module('webLog').controller('ScreenshotCtrl', [ '$scope', '$state', '$http', 'ScreenshotMaster', 'CurrentSuite', function($scope, $state, $http, ScreenshotMaster, CurrentSuite) {
	
	$scope.ScreenshotMaster = ScreenshotMaster;
	$scope.modalShown = false;
	
	$scope.toggleModal = function() {
		$scope.modalShown = !$scope.modalShown;
	};
	
	$scope.$on("closeModal", function() {
		$scope.toggleModal();
	});
	
	$scope.loadScreenshotsFromClass = function(){
		
	    $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimeStamp+'&classid='+CurrentSuite.currentClass.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		ScreenshotMaster.data = data
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getScreenshotsFromFileName = function(fileName){
		return '/api/screenshot/byfilename?timestamp='+CurrentSuite.currentTimeStamp+'&filename='+fileName;
	}
	
}]);