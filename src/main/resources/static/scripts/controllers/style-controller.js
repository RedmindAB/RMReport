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
	    
	    $scope.getButton = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return 'btn btn-failure2';
	    	else if(passed === "skipped")
	    		return 'btn btn-warning2';
	    	else
	    		return 'btn btn-success2';
	    }
	    
	    $scope.getPanel = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return 'panel panel-danger bg-danger';
	    	else if(passed === "skipped")
	    		return 'panel panel-warning bg-success warning';
	    	else
	    		return 'panel panel-success bg-success success';
	    };
	    
	    $scope.getBG = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return 'bg-danger';
	    	else if(passed === "skipped")
	    		return 'bg-warning';
	    	else
	    		return 'bg-success';
	    };
	    
	    $scope.getBorder = function(){
	    	return '#A94442';
	    }
	    
	    $scope.getBgCo = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return '#F2DEDE';
	    	else if(passed === "skipped")
	    		return '#FEF7E4';
	    	else
	    		return '#DFF0D8';
	    };
	    $scope.getCo = function(passed){
	    	if(passed === "failure" || passed === "error")
	    		return '#A94442';
	    	else if(passed === "skipped")
	    		return '#FEF7E4';
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
	    
	    $scope.showButton = false;
	    $scope.toggleButton = function() {
	        $scope.showButton = !$scope.showButton;
	    };
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