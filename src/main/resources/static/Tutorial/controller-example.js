(function(){ //.1
	
	'use strict'; //.2
	
	angular//.3
		.module('webLog')
		.controller('ScreenshotClassCtrl', ScreenshotClassCtrl);
	
	ScreenshotClassCtrl.$inject = ['CurrentSuite', 'RestLoader','ScreenshotMaster', 'SuiteInfoHandler'];//.4
	
	function ScreenshotClassCtrl(CurrentSuite, RestLoader, ScreenshotMaster, SuiteInfoHandler){//.5
		
		var vm = this;//.6
		
		vm.setCurrentClass = setCurrentClass;//.7
		
		init();//.8
		
		function init(){
			getSuiteSkeletonByTimestamp(CurrentSuite.currentTimestamp);
		}
		
		function getSuiteSkeletonByTimestamp(timestamp){
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
		
		function setCurrentClass(classObj){//.9
			CurrentSuite.currentClass = classObj;
		}
	}
})();

/*
 * Broken down and explained below ---------------------------------------------------------------------------------
 */

/* .1
 * 
 * Always wrap angular files in a IIFE (Immediately Invoked Function Expression).
 * We do this to force the file to set it self up right away when the compiler finds
 * the file and wont wait until all files are in order. 
 * Why do we do it? It basically just makes sense...
 * Doing it or not doing it wont affect anything but performance.
 * 
 * In other words, no point in NOT doing it.
 * 
 * IIFE example:
 * 
 * 			(function(){
 * 				//controller, factory or whatever in here.
 * 			})();
 */

(function(){
	
	/*.2
	 * 
	 * 'use strict' forces you to write cleaner code, it will stop Java script
	 * from compiling if you for example use unspecified variables.
	 * 
	 * An example of this is that it forces you to use "var x = 0;"
	 * instead of just "x = 0;" which java script in general will
	 * allow you to do but is bad practice because of obvious reasons.
	 * 'use strict' will stop a lot of other bad-practice-behavior as well
	 * but I wont go through them here, they are logical and you will
	 * find out the hard way any way. The console will tell you whats wrong.
	 */
	'use strict';
	
	/*.3
	 * 
	 * Always setup your Angular files with the following structure:
	 * 
	 * 	this is just a prefix necessary when setting up a angular file... just do it...
	 * 
	 * 	angular
	 * 	
	 * 		Name of the module, same as we defined in app.js.
	 * 		Notice that we don't put an [] in here. a .module('name',[])
	 * 		tells angular to create a NEW module and should generally only be used once in app.js.
	 * 
	 * 		The rest of the times it's used to define which module we want to reference the controller to.
	 * 		There is exceptions to this where you would want to create multiple modules in one project
	 * 		but the circumstances for those are for quite advanced angular coding and nothing we 
	 * 		will neither need nor touch on here.
	 *
	 * 		.module('ModuleName') //in this projects case it should be webLog
	 * 
	 * 		the .controller() function tells angular what kind of file this is. There is loads
	 * 		of different sorts you can create such as .directive .service and so forth
	 * 		but I wont go into detail on each of them. You will generally only make controllers, 
	 * 		directives and factories, they will cover 99% of your needs. For anything else there is google.
	 * 
	 * 		.controller takes two arguments, the first is the name you will use
	 * 		when referencing this file in the rest of angular when you inject it,
	 * 		the other is the name of the function which will be used as, in this case, the controller.
	 * 		if you scroll down you will see that we create a function with the corresponding name 
	 * 		which will be passed in here.		
	 * 
	 * 		.controller('NameToUseInApp', NameToUseInApp) //or .factory() or .service() or whatever you're creating.
	 */
	angular
		.module('webLog')
		.controller('ScreenshotClassCtrl', ScreenshotClassCtrl);
	
	/*.4
	 * 
	 * this is where we load in the dependencies we want to use in our controller.
	 * Put them inside a string so angular knows what to look after in case of minification of the
	 * java script. A controller can basically load in anything as a dependency except another
	 * controller. Well, you can, but it's tedious and generally shouldn't be done.
	 * 
	 * Make sure you use the same name here, in this case 'ScreenshotClassCtrl' as we did
	 * in the .controller() function.
	 */
	
	ScreenshotClassCtrl.$inject = ['CurrentSuite', 'RestLoader','ScreenshotMaster', 'SuiteInfoHandler'];
	
	/*.5
	 * 
	 * This is the function we will use as out actual controller. If you look at the name of the
	 * function you will notice that it's the same as the one we used up in .controller().
	 * In this function we will also put the non-stringified name of the dependencies
	 * we want to load. Make sure they are in the exact same order as in $inject so angular
	 * knows which one is which.
	 */
	function ScreenshotClassCtrl(CurrentSuite, RestLoader, ScreenshotMaster, SuiteInfoHandler){
		
		/*.6
		 * 
		 * var vm = this is necessary to make sure that the controllerAs statement from  app.js works.
		 * this var can be named whatever but vm (View Model) is a nice convention to keep.
		 * all functions and variable which are vm.name created, example: "vm.setCurrentClass"
		 * is reachable with CtrlName.VarOrMethodName out in the HTML as long as you are
		 * on a page where the controller has access.
		 */
		var vm = this;
		
		/*.7
		 * 
		 * all functions should be created down below and then referenced up here
		 * for simple readability reasons. Any developer should at a glance up here
		 * be able to see which functions are available in this file.
		 */
		vm.anotherFunctionForExample = 	anotherFunctionForExample
		vm.setCurrentClass = 			setCurrentClass;
		
		/*.8
		 * 
		 * a init method for the controller, this is where you would put all the code
		 * to get the data necessary for this controllers view. in  this case
		 * we are loading the basic data from the database so we have the info
		 * needed to display content in the view.
		 * 
		 * Since we run he function here in the actual js file outside of
		 * any functions it will run every time this file is instantiated.
		 * This means that every time this controllers state is reached
		 * its init() will run, more on this in the app-example.
		 */
		init();
		
		function init(){
			getSuiteSkeletonByTimestamp(CurrentSuite.currentTimestamp);
		}
		
		function getSuiteSkeletonByTimestamp(timestamp){
			SuiteInfoHandler.loadTimestamp(timestamp);
		}
		
		/*.9
		 * 
		 * this is where the vm.setCurrentClass gets its function from.
		 */
		function anotherFunctionForExample(){
			console.log("rawr");
		}
		
		/*.9
		 * 
		 * this is where the vm.setCurrentClass gets its function from.
		 */
		function setCurrentClass(classObj){
			CurrentSuite.currentClass = classObj;
		}
		
		
	}
})();