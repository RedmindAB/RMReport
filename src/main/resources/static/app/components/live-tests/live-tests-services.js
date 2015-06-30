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
			    	var data = request[0].data;
			    	LiveData.suite = data;
			    	LiveData.tests = data.tests;
			    	LiveData.historyid = 0;
			    	
			    	// Progress bar update
					var amountOfTests = request[0].data.totalTests;
					var amountDone = 0;
					for (var i = 0; i < request[0].data.totalTests; i++) {
						if(request[0].data.tests[i].status === 'done'){
							amountDone += 1;
							console.log("amountDone: " + amountDone);
						}
					}
					LiveData.currentPercentage = (amountOfTests / amountDone) * 100 + "%";
					/*if(request[0].data[0].status !== 'finished'){
						LiveData.percentage = "100%";
					}*/
			    });
		}
		
		function getLiveHistory(uuid){
		    var promises = [];

		    
			$http.get('/api/live/' + uuid + '/' + LiveData.historyid)
			.then(complete)
			.catch(error);
			
			function complete(request){
				var i;
		    	for (i = 0; i < request.data.length; i++) {
		    		var id = request.data[i].data.id;
		    		LiveData.tests[id-1] = request.data[i].data;
				}
		    	if (request.data.pop().type == "suiteFinish") {
					LiveData.uuid = "";
				}
		    	LiveData.historyid = request.data.pop().historyid;
			}
			
			function error(error){
				console.error(error);
			}
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