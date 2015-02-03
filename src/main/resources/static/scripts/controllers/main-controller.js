angular.module('webLog')
    .controller('MainCtrl',['$scope', '$http','$location', '$timeout','$state', function($scope, $http, $location, $timeout, $state){
    	
    $scope.$state = $state;
    	
    $scope.currentPage = "Home";
    
    $scope.classes = {};
    $scope.methods = {};
    $scope.navLinks = ["home",
                       "classes",
                       "methods"];
    
    $scope.errorReport={};
    $scope.suites = {};
    $scope.currentSuite = {};
    $scope.currentSuiteRun;
    $scope.mockSuites = []
    
	for (var int = 0; int < 50; int++) {
		$scope.mockSuites.push("Suite Run " + int);
	};
    
    $http.get('/api/log/getloglist')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.suites = data;
    		getTestSuite();
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    $http.get('/api/class/getclasses?suiteid=1')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.classes = data;
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    $http.get('/api/method/getmethods?classid=1')
    .success(function(data, status, headers, config){ 
    	if(data){
    		$scope.methods = data;
    	};
    }).error(function(data, status, headers, config){
    	console.log(data);
    });
    
    $scope.setCurrentSuiteRun = function(run){
    	console.log("setting current suite run");
    	$scope.currentSuiteRun = run;
    	for(var testCase in $scope.currentSuiteRun.testcases){
    		if ($scope.currentSuiteRun.testcases[testCase].failure) {
    			$scope.currentSuiteRun.testcases[testCase].failure.message.replace(/at /g, '\nat ');
			} else if($scope.currentSuiteRun.testcases[testCase].error) {
				$scope.currentSuiteRun.testcases[testCase].error.message.replace(/at /g, '\nat ');
			}
    	}
    	console.log($scope.currentSuiteRun);
    }
    
    $scope.setCurrentClass = function(testClass){
    	console.log($scope.currentSuiterun);
//    	var run = $scope.currentSuiterun;
//    	for(var testCase in run){
//    		console.log(run.testcases[testCase]);
//    		if (run.testcases[testCase].className === testclass) {
//				currentClass["testCase"+testCase] = run.testcases[testCase];
//			}
//    	}
//    	console.log($scope.currentClass);
    }

    function getTestSuite(){
    	$scope.currentSuite = $scope.suites;
    }
    
    var getTestSuiteRuns = function(testName){
        var suiteToReturn = {};
        for(var suite in $scope.suites){
        	var suiteCol = $scope.suites;
            if(suiteCol[suite].name === testName){
            	$scope.currentTestSuite = suiteCol[suite];
            }
        }
    };
    
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
    
    $scope.getCurrentState= function(){
    	console.log($state.current.name);
    	return $state.current.name;
    }
    
}]);
