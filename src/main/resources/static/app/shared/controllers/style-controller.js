angular.module('webLog')
	.controller('StyleCtrl', ['$scope', '$state', 'Utilities', function($scope, $state, Utilities){
		
		$scope.Utilities = Utilities;
		
	    $scope.getCurrentState= function(state){
	    	return $state.includes(state);
	    }
	    
	    $scope.stopPropagation = function($event){
	    	$event.stopPropagation();
	    }
	    
	    $scope.setActive = function(){
	    	$event.stopPropagation();
	    }
	    
	    $scope.removePackagePath = function(classPath){
	    	var lastDot = classPath.lastIndexOf(".");
	    	var className = classPath.substring(lastDot+1);
	    	return className;
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
	    		return '#8D6E3F';
	    	else
	    		return '#3C763D';
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
	    
	    $scope.getNavColor = function(state){
	    	if ($state.includes(state)) {
	    		return '#DC062A';
			} else {
				return '#5F5E5D';
			}
	    }
	    
	    $scope.getLogo = function(suiteName){
	    	var path = 'img/suites/' + suiteName + '.png';
	    	
	    	
	    	
	    	return path;
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