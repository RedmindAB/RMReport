angular.module('webLog')
	.controller('SuitesCtrl',[ '$scope', '$location','$state','$http', function($scope, $location, $state, $http){
		
		$scope.currentClass=[];
		$scope.mockMethods = [];
	    $scope.searchText = "";
		
		$scope.dataStuff = 100;

		$scope.resetFilterField = function(){
	    	$scope.searchText="";
	    };
		
	    $scope.getCases = function(method){
	    	console.log("ey");
	    	mockMethods(method.name);
	    }
	    
	    $scope.getMethods = function(suiteClass){
	        $http.get('/api/method/getmethods?classid='+suiteClass.id)
	        .success(function(data, status, headers, config){ 
	        	if(data){
	        		$scope.currentClass = data;
	        	};
	        }).error(function(data, status, headers, config){
	        	console.log(data);
	        });
	    };
	    
		function mockMethods(name) {
			var mock = $scope.mockMethods;
			mock.push(name+"(OSX Firefox)");
			mock.push(name+"(OSX Chrome)");
			mock.push(name+"(OSX Safari)");
			mock.push(name+"(Windows Firefox)");
			mock.push(name+"(Windows Chrome)");
			mock.push(name+"(Windows IExplorer)");
		}
		
		$scope.data = [
		               {x: 0, 120124: 56, 120314: 87, 120718: 99, 121224: 45, hej: 8},
		               {x: 1, 120124: 23, 120314: 75, 120718: 78, 121224: 13, hej: 5},
		               {x: 2, 120124: 15, 120314: 63, 120718: 45, 121224: 23, hej: 7},
		               {x: 3, 120124: 16, 120314: 78, 120718: 38, 121224: 23, hej: 3},
		               {x: 4, 120124: 23, 120314: 45, 120718: 26, 121224: 33, hej: 12},
		               {x: 5, 120124: 3, 120314: 34, 120718: 14, 121224: 20, hej: 15}
		             ];


		$scope.options = {
				  axes: {
				    x: {key: 'x', labelFunction: function(value) {return value;}, type: 'linear', ticks: 5},
				    y: {type: 'linear', ticks: 5},
				  },
				  series: [
				    {y: '120124', type: 'area', color: 'steelblue', thickness: '2px', drawDots: false, label: 'android'},
				    {y: '120314', type: 'area',color: 'brown', thickness: '2px',visible: true, drawDots: false,label: 'OSX'},
				    {y: '120718', type: 'area',color: 'lightsteelblue', thickness: '2px', visible: true, drawDots: false,label: 'windows'},
				    {y: '121224', type: 'area',color: 'red', thickness: '2px', visible: true, drawDots: false,label: 'iPhone'},
				    {y: 'hej', type: 'area',color: 'green', thickness: '2px', visible: true, drawDots: false,label: 'Linux'}
				  ],
				  lineMode: 'cardinal',
				  tension: 0.7,
				  tooltip: {mode: 'scrubber', interpolate: 'true', formatter: function(x, y, series) {return series.label+" - " + y;}},
				  drawLegend: true,
				  drawDots: true,
				  interpolate: '',
				  columnsHGap: 5
				}
		
	}]);