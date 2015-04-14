var applicationsControllers = angular.module('applicationsControllers', []);

applicationsControllers.controller('applicationsController', function($scope,$http) {
	$scope.loadApps = function() {
		$http.get("/service/applications").success(function(response) {
			$scope.applications = response;
		});
	}
	
	
	// Init
	$scope.loadApps();
});
