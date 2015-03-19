angular.module('webLog')
	.factory('Utilities', function(){
	    return { 
	    	searchField: '',
	    	resultAmount: '50',
	    	timeStamps: '',
	    	graphView: 'Pass/Fail',
	    	runValues: ["10", "20", "50", "100", "500"],
	    	sorting: ['result','stats.totFail', 'name'],
	    	caseSorting: ['result','osname', 'devicename', 'osversion', 'browsername']
	    };
	});