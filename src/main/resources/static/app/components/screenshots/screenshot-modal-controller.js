(function(){
	'use strict';	
	
	angular
		.module('webLog')
		.controller('ScreenshotModalCtrl', ScreenshotModalCtrl);
		
	ScreenshotModalCtrl.$inject = ['ScreenshotMaster', 'ScreenshotServices', 'CurrentSuite'];
	
	function ScreenshotModalCtrl (ScreenshotMaster, ScreenshotServices, CurrentSuite) {
		
		var vm = this;
		
		vm.currentIndex = 0;
	    vm.slides = [];
	    vm.direction = 'left';
	    vm.currentMethod = "unknown";
	    vm.currentSlideInfo = "unknown";
	    
		
		vm.setCurrentMethod 		= setCurrentMethod;
		vm.setSlides				= setSlides;
		vm.getCurrentSlideInfo 		= getCurrentSlideInfo;
		vm.setCurrentSlideIndex 	= setCurrentSlideIndex;
		vm.isCurrentSlideIndex 		= isCurrentSlideIndex;
		vm.prevSlide 				= prevSlide;
		vm.nextSlide 				= nextSlide;
		vm.isLastIndex 				= isLastIndex;
		
		
		init();
		
		
		document.addEventListener('dragstart', function (e) { 
			e.preventDefault(); 
		});
		
		function init(){
			loadConsolePrints();
		}

	    function setCurrentMethod(method){
	    	vm.currentMethod = method;
	    }
	    
	    function setSlides(cases, index, parentIndex){
	    	var screenArray = [];
	    	for (var i = 0; i < cases.length; i++) {
				screenArray.push(ScreenshotMaster.getScreenshotsFromFileName(cases[i].screenshots[parentIndex]));
			}
	    	vm.slides = screenArray;
	    	setCurrentSlideIndex(index);
	    }
	    
	    function getCurrentSlideInfo(){
	    	var info = '';
	    	if (vm.currentMethod !== 'unknown') {
	    		 info = vm.currentMethod.testcases[vm.currentIndex].device + " - " + vm.currentMethod.testcases[vm.currentIndex].browser;
	    	}
	    	return info;
	    }
	     
	    function setCurrentSlideIndex(index) {
	    	vm.direction = (index > vm.currentIndex) ? 'left' : 'right';
	    	vm.currentIndex = index;
	    }
	
	    function isCurrentSlideIndex(index) {
	    	return vm.currentIndex === index;
	    }
	
	    function prevSlide() {
	    	vm.direction = 'left';
	    	vm.currentIndex = (vm.currentIndex <vm.slides.length - 1) ? ++vm.currentIndex : 0;
	    }
	
	    function nextSlide() {
	    	vm.direction = 'right';
	    	vm.currentIndex = (vm.currentIndex > 0) ? --vm.currentIndex : vm.slides.length - 1;
	    }
	    
		function loadConsolePrints(){
			return ScreenshotServices.getConsolePrints(CurrentSuite.currentSuiteInfo.id, CurrentSuite.currentTimestamp)
				.then(function(data){
					ScreenshotMaster.consolePrint = data;
				});
		}
	    
	    function isLastIndex(index,length){
	    	return index+1 === length;
	    }
	}
})();