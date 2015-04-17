var kpisControllers = angular.module('kpisControllers', [ 'angularFileUpload',
		'angularModalService' ]);

kpisControllers.controller('kpisController', function($scope, $http, $upload,$sce) {

	$scope.hasKpi = false;
	$http.get("/service/kpis").success(function(response) {
		$scope.kpis = response;
	});

	$scope.displaySafeHtml = function(html) {
		return $sce.trustAsHtml(html);
	}
	$scope.addNewConfigurationInList = function(conf) {
		if ($scope.configurations == null) {
			$scope.configurations = [];
		}
		$scope.configurations.push(conf);
	}
	$scope.createNewConfiguration = function (configurationName) {		
		$http({
			url : '/service/newKpiConfiguration/',
			method : 'POST',
			params : {
				name : configurationName
			}
		}).success(function(response) {
			
			BootstrapDialog.show({
				title : 'Information',
				message : 'New configuration created : ' + configurationName
			});
			$scope.addNewConfigurationInList(response);
		}).error(
				function(data, status, headers, config) {
					BootstrapDialog.show({
						title : 'Error',
						message : 'Cannot create configuration '
								+ configurationName + '\n\n' + 'Reason : '
								+ data.message
					});
				});
	}
});
