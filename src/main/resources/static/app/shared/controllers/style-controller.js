(function(){
	'use strict';
	
	angular
		.module('webLog')
		.controller('StyleCtrl', StyleCtrl);
			
	StyleCtrl.$inject = ['$state'];
	
	function StyleCtrl ($state){
		
		var vm = this;
		
		var myData = null;
		var showButton = false;
		
		vm.stopPropagation 		= stopPropagation;
		vm.removePackagePath 	= removePackagePath;
		vm.showClassLink 		= showClassLink;
		vm.getBorderColor 		= getBorderColor;
		vm.getButton 			= getButton;
		vm.getPanel 			= getPanel;
		vm.getBgCo 				= getBgCo;
		vm.getColor 			= getColor;
		vm.formatDecimals 		= formatDecimals;
		vm.toggleButton			= toggleButton;
		vm.getNavColor 			= getNavColor;
		
		
	    function getCurrentState(state){
	    	return $state.includes(state);
	    }
	    
	    function stopPropagation($event){
	    	$event.stopPropagation();
	    }
	    
	    function removePackagePath(classPath){
	    	if(classPath !== undefined){
		    	var lastDot = classPath.lastIndexOf(".");
		    	var className = classPath.substring(lastDot+1);
		    	return className;
	    	}
	    }
	    
	    function showClassLink(page){
	    	switch (page) {
			case "classes":
				return getCurrentState('reports.classes') || 
				getCurrentState('reports.methods') || 
				getCurrentState('reports.drivers') || 
				getCurrentState('reports.cases');
				
			case "methods":
				return getCurrentState('reports.methods')  || 
				getCurrentState('reports.drivers') || 
				getCurrentState('reports.cases');
				
			case "drivers":
				return getCurrentState('reports.drivers') || 
				getCurrentState('reports.cases');
				
			case "cases":
				return getCurrentState('reports.cases');

			default:
				return false;
			}
	    }
	    
	    function getButton(result){
	    	if(result === "failure" || result === "error"){
	    		return 'btn btn-failure2';
	    	}
	    	else if(result === "skipped"){
	    		return 'btn btn-warning2';
	    	}
	    	else{
	    		return 'btn btn-success2';
	    	}
	    }
	    
	    function getPanel(result){
	    	if(result === "failure" || result === "error"){
	    		return 'panel panel-danger bg-danger';
	    	}
	    	else if(result === "skipped"){
	    		return 'panel panel-warning bg-success warning';
	    	}
	    	else{
	    		return 'panel panel-success bg-success success';
	    	}
	    }
	    
	    function getBgCo(result){
	    	if(result === "failure" || result === "error"){
	    		return '#F2DEDE';
	    	}
	    	else if(result === "skipped"){
	    		return '#FEF7E4';
	    	}
	    	else{
	    		return '#DFF0D8';
	    	}
	    }
	    
	    function getColor(result){
	    	if(result === "failure" || result === "error"){
	    		return '#A94442';
	    	}
	    	else if(result === "skipped"){
	    		return '#8D6E3F';
	    	}
	    	else{
	    		return '#3C763D';
	    	}
	    }
	    
	    function getBorderColor(result){
	    	if(result === "failure" || result === "error"){
	    		return '#ebccd1';
	    	}
	    	else if(result === "skipped"){
	    		return '#faebcc';
	    	}
	    	else{
	    		return '#d6e9c6';
	    	}
	    }
	    
	    function formatDecimals(numberWithWayToManyDecimals){
	    	var numberWithDecentAmountOfDecimals = numberWithWayToManyDecimals.toFixed(2);
	    	return numberWithDecentAmountOfDecimals;
	    }
	    
	    function toggleButton() {
	        showButton = !showButton;
	    }
	    
	    function getNavColor(state){
	    	if ($state.includes(state)) {
	    		return '#DC062A';
			} else {
				return '#5F5E5D';
			}
	    }
	}
})();