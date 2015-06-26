(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('LiveTestsServices', LiveTestsServices);
	
	LiveTestsServices.$inject = ['$http', '$q', 'LiveData' ,'CurrentSuite', 'Utilities'];
	
	function LiveTestsServices ($http, $q, LiveData, CurrentSuite, Utilities) {

		
		var progressBar = runProgressBar;
		var liveSuite = getLiveSuite;
		var liveTests = getLiveTests;
		var liveHistory = getLiveHistory;
		
		return {
			runProgressBar: progressBar,
			getLiveSuite: liveSuite,
			getLiveTests: liveTests,
			getLiveHistory: liveHistory
		};
		
		function getLiveSuite(){
		    var promises = [];

			var promise = $http.get('/api/live')
			.success(function(data, status, headers, config){ 
			}).error(function(data, status, headers, config){
			});
			promises.push(promise);
		    return $q.all(promises);
		}
		
		function ObjectLength( object ) {
		    var length = 0;
		    for( var key in object ) {
		        if( object.hasOwnProperty(key) ) {
		            ++length;
		        }
		    }
		    return length;
		};
		
		function getLiveTests(uuid){
			 var promises = [];

				var promise = $http.get('/api/live/' + uuid)
				.success(function(data, status, headers, config){ 
				}).error(function(data, status, headers, config){
				});
				promises.push(promise);
			    return $q.all(promises).then(function(request){
			    	LiveData.tests = request[0].data.tests;
			    	LiveData.historyid = 0;
			    	
			    	// Progress bar update
					console.log(request[0].data.totalTests);
					var amountOfTests = request[0].data.totalTests;
					var amountDone = 0;
					for (var i = 0; i < request[0].data.totalTests; i++) {
						if(request[0].data.tests[i].status === 'done'){
							amountDone += 1;
							console.log("amountDone: " + amountDone);
						}
					}
					LiveData.currentPercentage = (amountOfTests / amountDone) * 100 + "%";
					console.log("currentPercentage: " + LiveData.currentPercentage)
					/*if(request[0].data[0].status !== 'finished'){
						LiveData.percentage = "100%";
					}*/
			    });
		}
		
		function getLiveHistory(uuid){
		    var promises = [];

			var promise = $http.get('/api/live/' + uuid + '/' + LiveData.historyid)
			.success(function(data, status, headers, config){ 
			}).error(function(data, status, headers, config){
			});
			promises.push(promise);
		    return $q.all(promises).then(function(request){
		    	console.log(request[0].data);
		    	for (var i = 0; i < request[0].data[i].data.length; i++) {
		    		var id = request[0].data[i].data.id;
		    		LiveData.tests[id] = request[0].data[i].data;
		    		console.log("is running?");
		    		LiveData.historyid = request[0].data[i].data.historyid;
				}	
		    });
		}
		
		function runProgressBar(){
			setTimeout(function(){
			
			    $('.progress .progress-bar').each(function() {
			        var me = $(this);
			        var perc = me.attr("data-percentage");
			
			        var current_perc = 0;
			
			        var progress = setInterval(function() {
			            if (current_perc>=perc) {
			                clearInterval(progress);
			            } else {
			                current_perc +=1;
			                me.css('width', (current_perc)+'%');
			            }
			
			            me.text((current_perc)+'%');
			
			        }, 1000);
			
			    });
			
			},1000);
		}
	}
})();