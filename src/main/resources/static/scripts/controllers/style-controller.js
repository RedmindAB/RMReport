angular.module('webLog')
	.controller('StyleCtrl', ['$scope', '$state', function($scope, $state){
		   
	    $scope.getCurrentState= function(state){
	    	return $state.includes(state);
	    }
	    
	    $scope.stopPropagation = function($event){
	    	$event.stopPropagation();
	    }
	    
	    $scope.setActive = function(){
	    	$event.stopPropagation();
	    }
	    
	    $scope.showClassLink = function(page){
	    	switch (page) {
			case "classes":
				return $scope.getCurrentState('reports.classes') || 
				$scope.getCurrentState('reports.methods') || 
				$scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				break;
			case "methods":
				return $scope.getCurrentState('reports.methods')  || 
				$scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				break;
			case "drivers":
				return $scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				break;
			case "cases":
				return $scope.getCurrentState('reports.cases');
				break;

			default:
				return false;
				break;
			}
	    }
	    
	    $scope.getPanel = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return 'panel panel-danger bg-danger';
	    	else
	    		return 'panel panel-success bg-success success';
	    };
	    
	    $scope.getBG = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return 'bg-danger';
	    	else
	    		return 'bg-success';
	    };
	    
	    $scope.getBgCo = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return '#F2DEDE';
	    	else
	    		return '#DFF0D8';
	    };
	    $scope.getCo = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return '#A94442';
	    	else
	    		return '#3C763D';
	    };
	    $scope.getLogo = function(passed){
	    	if(passed == 1)
	    		return "img/logo1.png";
	    	else if(passed == 2)
	    		return 'img/logo1.jpg';
	    	else
	    		return 'img/logo3.jpg';    
	    };
	    
	    $scope.formatDecimals = function(numberWithWayToManyDecimals){
	    	var numberWithDecentAmountOfDecimals = numberWithWayToManyDecimals.toFixed(2);
	    	return numberWithDecentAmountOfDecimals;
	    }
	    
	    $scope.checkPassed = function(passed){
	    	return passed === "passed";
	    }
}])