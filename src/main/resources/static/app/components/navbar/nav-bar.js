angular
	.module('webLog')
	.directive('navbar', navbar);

function navbar (){
  return {
	templateUrl: 'app/components/navbar/nav-bar.html',
	controller:'NavBarCtrl'
  };
};