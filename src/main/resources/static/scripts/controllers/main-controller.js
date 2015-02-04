angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', '$timeout','$state', function($scope, $http, $location, $timeout, $state){
    	
    $scope.$state = $state;
    	
    $scope.currentPage = "Home";
    
    $scope.classes = {};
    $scope.methods = {};
    $scope.errorReport={};
    $scope.currentSuite = {};
    $scope.currentSuiteRun;
    $scope.mockSuites = []
    
    $scope.imagePaths = ['img/aftonbladet.png', 'img/aftonbladet_plus.png', 'img/aftonbladet_webb-tv.png'];
    
	for (var int = 0; int < 50; int++) {
		$scope.mockSuites.push("Suite Run " + int);
	};
    
    $scope.goToHome= function(){
    	$state.transitionTo('home');
    }
    
    $scope.goToProject = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.goToClasses = function(){
    	$state.transitionTo('reports.classes');
    }
    
    $scope.goToMethods = function(){
    	$state.transitionTo('reports.methods');
    }
    
    $scope.goToCases = function(){
    	$state.transitionTo('reports.cases');
    };
    
//    $scope.setCurrentSuiteRun = function(run){
//    	console.log("setting current suite run");
//    	$scope.currentSuiteRun = run;
//    	for(var testCase in $scope.currentSuiteRun.testcases){
//    		if ($scope.currentSuiteRun.testcases[testCase].failure) {
//    			$scope.currentSuiteRun.testcases[testCase].failure.message.replace(/at /g, '\nat ');
//			} else if($scope.currentSuiteRun.testcases[testCase].error) {
//				$scope.currentSuiteRun.testcases[testCase].error.message.replace(/at /g, '\nat ');
//			}
//    	}
//    	console.log($scope.currentSuiteRun);
//    }
    
    $scope.setCurrentSuite = function(suite){
		$scope.currentSuite = suite;
		console.log($scope.currentSuite.id);
	    $http.get('/api/class/getclasses?suiteid='+$scope.currentSuite.id)
	    .success(function(data, status, headers, config){ 
	    	if(data){
	    		$scope.classes = data;
	    	};
	    }).error(function(data, status, headers, config){
	    	console.log(data);
	    });
    }
    
    $scope.getPanel = function(passed){
    	if(passed)
    		return 'panel panel-success bg-success success';
    	else
    		return 'panel panel-danger bg-danger';
    };
    
    $scope.getBG = function(passed){
    	if(passed)
    		return 'bg-success';
    	else
    		return 'bg-danger';
    };
    
    $scope.getBgCo = function(passed){
    	if(passed)
    		return '#DFF0D8';
    	else
    		return '#F2DEDE';
    };
    $scope.getCo = function(passed){
    	if(passed)
    		return '#3C763D';
    	else
    		return '#A94442';
    };
    $scope.getLogo = function(passed){
    	if(passed == 1)
    		return "img/logo1.png";
    	else if(passed == 2)
    		return 'img/logo1.jpg';
    	else
    		return 'img/logo3.jpg';    
    };
    
    $scope.getPageHeader = function(){
    	switch($location.url()) {
        case '/home':
            return "Home";
            break;
            
        case '/project':
            return "Project View";
            break;
            
        case '/test-suite-runs':
            return "Suite Runs";
            break;
            
        case '/suite-run-classes':
            return "Test Classes";
            break;
            
        case '/test-case':
            return "Test Cases";
            break;
            
        default:
            return "Reports";
        	break;
    	}
    };
    
    $scope.labels2 = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    $scope.data2 = [300, 500, 100];
    
    $scope.getCurrentState= function(state){
    	return $state.includes(state);
    }
    
}]);
