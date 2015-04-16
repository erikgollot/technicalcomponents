var kpisControllers = angular.module('kpisControllers', [ 'angularFileUpload',
		'angularModalService' ]);

kpisControllers.controller('kpisController', function($scope, $http, $upload) {

	$scope.hasKpi = false;
	$http.get("/service/kpis").success(function(response) {
		$scope.kpis = response;
	});

});
