angular.module('webLog').controller('ScreenshotCtrl', ['$window', '$scope', '$rootScope', '$state', '$http', 'ScreenshotMaster', 'CurrentSuite', function($window ,$scope, $rootScope,$state, $http, ScreenshotMaster, CurrentSuite) {
	
	$scope.ScreenshotMaster = ScreenshotMaster;
	$scope.modalShown = false;
	$scope.caseArraySize = []
	
	$scope.getMethodContentWidth = function(method){
		return method.testcases.length * 247;
	}
	
	$scope.toggleModal = function() {
		$scope.modalShown = !$scope.modalShown;
	};
	
	$scope.$on("closeModal", function() {
		$scope.toggleModal();
	});
	
	$rootScope.$on("wrongScreenData", function(){
		$scope.loadScreenshotsFromClass();
	});
	
	document.addEventListener('dragstart', function (e) { e.preventDefault(); });
	
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
	    		ScreenshotMaster.currentClass = CurrentSuite.currentClass.id
	    		setCaseSizeByMethod();
	    	};
	    }).error(function(data, status, headers, config){
	    	console.error(data);
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
	
	$scope.getFailError = function(obj){
		var tot = obj.error + obj.failure;
		return tot;
	}
	
	$scope.getNumber = function(num) {
	    return new Array(num);   
	}
	
	
	//slider specifics
    $scope.slides = [];
    $scope.direction = 'left';
    $scope.currentIndex = 0;
    $scope.currentMethod = "unknown";
    $scope.currentSlideInfo = "unknown";
    	
    $scope.setCurrentMethod = function(method){
    	$scope.currentMethod = method;
    }
    
    $scope.setSlides= function(cases, index, parentIndex){
    	var screenArray = [];
    	for (var i = 0; i < cases.length; i++) {
			screenArray.push($scope.getScreenshotsFromFileName(cases[i].screenshots[index]));
		}
    	
    	$scope.slides = screenArray;
    	 $scope.setCurrentSlideIndex(parentIndex);
    }
    
     $scope.getCurrentSlideInfo = function(){
    	 if ($scope.currentMethod !== 'unknown') {
    		 var info = $scope.currentMethod.testcases[$scope.currentIndex].device + " - " + $scope.currentMethod.testcases[$scope.currentIndex].browser;
    	 }
    	 return info;
     }
     
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