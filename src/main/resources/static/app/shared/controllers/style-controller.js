(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('StyleCtrl', StyleCtrl);
			
	StyleCtrl.$inject = ['$scope', '$state', 'Utilities', 'CurrentSuite'];
	
	function StyleCtrl ($scope, $state, Utilities, CurrentSuite){
		
		var myData = null;
		$scope.Utilities = Utilities;
		
	    $scope.getCurrentState= function(state){
	    	return $state.includes(state);
	    };
	    
	    $scope.stopPropagation = function($event){
	    	$event.stopPropagation();
	    };
	    
	    $scope.setActive = function(){
	    	$event.stopPropagation();
	    };
	    
	    $scope.removePackagePath = function(classPath){
	    	var lastDot = classPath.lastIndexOf(".");
	    	var className = classPath.substring(lastDot+1);
	    	return className;
	    };
	    
	    $scope.showClassLink = function(page){
	    	switch (page) {
			case "classes":
				return $scope.getCurrentState('reports.classes') || 
				$scope.getCurrentState('reports.methods') || 
				$scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				
			case "methods":
				return $scope.getCurrentState('reports.methods')  || 
				$scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				
			case "drivers":
				return $scope.getCurrentState('reports.drivers') || 
				$scope.getCurrentState('reports.cases');
				
			case "cases":
				return $scope.getCurrentState('reports.cases');

			default:
				return false;
			}
	    };
	    
	    $scope.getButton = function(result){
	    	if(result === "failure" || result === "error")
	    		return 'btn btn-failure2';
	    	else if(result === "skipped")
	    		return 'btn btn-warning2';
	    	else
	    		return 'btn btn-success2';
	    };
	    
	    $scope.getPanel = function(result){
	    	if(result === "failure" || result === "error")
	    		return 'panel panel-danger bg-danger';
	    	else if(result === "skipped")
	    		return 'panel panel-warning bg-success warning';
	    	else
	    		return 'panel panel-success bg-success success';
	    };
	    
	    $scope.getBG = function(result){
	    	if(result === "failure" || result === "error")
	    		return 'bg-danger';
	    	else if(result === "skipped")
	    		return 'bg-warning';
	    	else
	    		return 'bg-success';
	    };
	    
	    $scope.getBorder = function(){
	    	return '#A94442';
	    };
	    
	    $scope.getBgCo = function(result){
	    	if(result === "failure" || result === "error")
	    		return '#F2DEDE';
	    	else if(result === "skipped")
	    		return '#FEF7E4';
	    	else
	    		return '#DFF0D8';
	    };
	    $scope.getCo = function(result){
	    	if(result === "failure" || result === "error")
	    		return '#A94442';
	    	else if(result === "skipped")
	    		return '#8D6E3F';
	    	else
	    		return '#3C763D';
	    };
	    
	    $scope.formatDecimals = function(numberWithWayToManyDecimals){
	    	var numberWithDecentAmountOfDecimals = numberWithWayToManyDecimals.toFixed(2);
	    	return numberWithDecentAmountOfDecimals;
	    };
	    
	    $scope.checkPassed = function(passed){
	    	return passed === "passed";
	    };
	    
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
	    };
	    
	    $scope.getLogo = function(suiteName){
	    	var path = 'img/suites/' + suiteName + '.png';
	    	
	    	
	    	
	    	return path;
	    };
	    
		$scope.getVersion = function(){
			$.ajax({
			    url: "", /* https://api.github.com/repos/owner/repo/git/refs/tags */ 
			    dataType: "json",
			    success: function (data)
			    {
				        if(data !== null){
				        	$("#result").html(data[0]["object"]["sha"]);
				        	myData = (data[0].ref).replace("refs/tags/", '');
				        }
				        else
				        	myData = "unknown";
				        count++;
			    },
				error: function (data){
					myData = "unknown";
				}
			});
			return myData;
		};
	}
})();