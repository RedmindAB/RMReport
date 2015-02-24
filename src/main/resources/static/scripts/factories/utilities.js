angular.module('webLog')
	.factory('Utilities', function(){
	    return { 
	    	searchField: '',
	    	amountField: '50',
	    	timeStamps: '',
	    	graphView: "Pass/Fail",
	    	runValues: ["10", "20", "50", "100", "500"]
	    };
	});