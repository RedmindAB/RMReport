angular.module('webLog').controller('ScreenshotCtrl', ['$window', '$scope', '$rootScope', '$state', '$http', 'ScreenshotMaster', 'CurrentSuite','RestLoader', function($window ,$scope, $rootScope,$state, $http, ScreenshotMaster, CurrentSuite, RestLoader) {
	
	$scope.ScreenshotMaster = ScreenshotMaster;
	$scope.modalShown = false;
	$scope.modalShown2 = false;
	$scope.caseArraySize = [];
	$scope.noScreenshotsExists = false;
	
	$scope.noScreenShotsExists = function(){
		return ScreenshotMaster.data.screenshotsExists !== undefined && ScreenshotMaster.data.screenshotsExists === false;
	}
	
	$scope.makeArray = function (number) {
		return new Array(number);
	}
	
	$scope.containsScreenshots = function(method){
		return method.screenshotLength > 0;
	}
	
	$scope.getMethodContentWidth = function(method){
		return (method.testcases.length * 232)+10;
	}
	
	$scope.toggleModal = function() {
		$scope.modalShown = !$scope.modalShown;
	};
	
	$scope.$on("closeModal", function() {
		$scope.toggleModal();
	});
	
	$scope.toggleModal2 = function() {
		$scope.modalShown2 = !$scope.modalShown2;
	};
	
	$scope.$on("closeModal2", function() {
		$scope.toggleModal2();
	});
	
	document.addEventListener('dragstart', function (e) { 
		e.preventDefault(); 
	});
	
	$scope.loadScreenshotsFromClass = function(classObj){
		RestLoader.loadScreenshotsFromClass(classObj);
	}
	
	$scope.getScreenshotsFromFileName = function(fileName, caseObj){
		if (!fileName) {
			return 'assets/img/logos/placeholder2.png';
		}
		return '/api/screenshot/byfilename?timestamp='+ScreenshotMaster.currentTimestamp+'&filename='+fileName;
	}
	
	$scope.getScreenshotsFromTimestamp = function(){
		if ($state.$current.name === 'screenshots.methods') {
			RestLoader.loadScreenshotsFromClass(CurrentSuite.currentClass)
		}
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
	// Ordering function for screenshots - in progress...
	$scope.getScreenshotOrder = function(fileName){
		var path = fileName;
		var index = path.indexOf("-_-");
		var order = path.split("-_-");
		var order2 = order[0].split("-");
		if(index === -1)
			return "";
		else
			return order2[1];
	}
	
//	function setCaseSizeByMethod(){
//		var data = ScreenshotMaster.data;
//		
//		for (var i = 0; i < data.length; i++) {
//			var screenshotLength = 0;
//			for (var j = 0; j < data[i].testcases.length; j++) {
//				if (data[i].testcases[j].screenshots.length > screenshotLength) {
//					screenshotLength = data[i].testcases[j].screenshots.length;
//				}
//			}
//			data[i].screenshotLength = screenshotLength;
//		}
//	}
	
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
     
     $scope.getConsolePrint = function(){
    	 RestLoader.getConsolePrint();
     }
}])
.animation('.slide-animation', function () {
        return {
            beforeAddClass: function (element, className, done) {
                var scope = element.scope();

                if (className == 'ng-hide') {
                    var finishPoint = element.parent().width();
                    if(scope.direction !== 'right') {
                        finishPoint = -finishPoint;
                    }
                    TweenMax.to(element, 0.5, {left: finishPoint, onComplete: done });
                }
                else {
                    done();
                }
            },
            removeClass: function (element, className, done) {
                var scope = element.scope();

                if (className == 'ng-hide') {
                    element.removeClass('ng-hide');

                    var startPoint = element.parent().width();
                    if(scope.direction === 'right') {
                        startPoint = -startPoint;
                    }

                    TweenMax.fromTo(element, 0.5, { left: startPoint }, {left: 0, onComplete: done });
                }
                else {
                    done();
                }
            }
        };
});