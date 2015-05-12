angular.module('webLog')
	.directive('navbar',function(){
		  return {
			    templateUrl: 'app/components/navbar/nav-bar.html',
			    controller:'NavBarCtrl'
			  };
	});