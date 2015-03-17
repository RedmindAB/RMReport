angular.module('webLog').controller('ScreenshotCtrl', [ '$scope', '$state', '$http', 'ScreenshotMaster', 'CurrentSuite', function($scope, $state, $http, ScreenshotMaster, CurrentSuite) {
	
	$scope.ScreenshotMaster = ScreenshotMaster;
	$scope.modalShown = false;
	$scope.caseArraySize = []
	
	$scope.toggleModal = function() {
		$scope.modalShown = !$scope.modalShown;
	};
	
	$scope.$on("closeModal", function() {
		$scope.toggleModal();
	});
	
	$scope.getScreenByIndex = function(){
		var array = [];
		array.push($scope.getScreenshotsFromFileName(ScreenshotMaster.data[0].testcases[1].screenshots[0]));
		return array;
	}
	
	$scope.loadScreenshotsFromClass = function(){
		
	    $http.get('/api/screenshot/structure?timestamp='+CurrentSuite.currentTimeStamp+'&classid='+CurrentSuite.currentClass.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		ScreenshotMaster.data = data
	    		setCaseSizeByMethod();
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
	}
	
	$scope.getScreenshotsFromFileName = function(fileName){
		if (!fileName) {
			return 'img/placeholder.png';
		}
		return '/api/screenshot/byfilename?timestamp='+CurrentSuite.currentTimeStamp+'&filename='+fileName;
	}
	
	$scope.getCommentFromFileName = function(fileName){
		var path = fileName;
		var index = path.indexOf("-_-");
		
		if (index === -1) {
			return undefined;
		} else {
			var comment = path.substring()
			return comment.slice(0,index);
		}
	}
	
	function setCaseSizeByMethod(){
		var data = ScreenshotMaster.data;
		
		for (var i = 0; i < data.length; i++) {
			var screenshotLength = 0;
			for (var j = 0; j < data[i].testcases.length; j++) {
				if (data[i].testcases[j].screenshots.length > screenshotLength) {
					screenshotLength = data[i].testcases[j].screenshots.length;
				}
			}
			data[i].screenshotLength = screenshotLength;
		}
	}
	
	$scope.getNumber = function(num) {
	    return new Array(num);   
	}
	
	
	//slider specifics
    $scope.slides = [];

    $scope.setSlides= function(screenshots){
    	var screenArray = [];
    	for (var i = 0; i < screenshots.length; i++) {
			screenArray.push($scope.getScreenshotsFromFileName(screenshots[i]));
		}
    	
    	console.log("screenshots");
    	console.log(screenArray);
    	$scope.slides = screenArray;
    }
    
     $scope.direction = 'left';
     $scope.currentIndex = 0;

     $scope.setCurrentSlideIndex = function (index) {
         $scope.direction = (index > $scope.currentIndex) ? 'left' : 'right';
         $scope.currentIndex = index;
     };

     $scope.isCurrentSlideIndex = function (index) {
         return $scope.currentIndex === index;
     };

     $scope.prevSlide = function () {
         $scope.direction = 'left';
         $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
     };

     $scope.nextSlide = function () {
         $scope.direction = 'right';
         $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
     };
	
	
}]);