var kpisControllers = angular.module('kpisControllers', [ 'angularFileUpload',
		'angularModalService' ]);

kpisControllers
		.controller(
				'kpisController',
				function($scope, $http, $upload, $sce) {

					$scope.hasKpi = false;
					$scope.kpiUsages = [];
					$http
							.get("/service/kpis")
							.success(
									function(response) {
										$scope.kpis = response;
										// Copy for usages selection
										if ($scope.kpis != null) {
											for (i = 0; i < $scope.kpis.length; i++) {
												var usage = new Object();
												usage.id = null;
												usage.kpi_id = $scope.kpis[i].id;
												usage.kpi_name = $scope.kpis[i].name;
												usage.kind = $scope.kpis[i].kind;
												usage.kpi_description = $scope.kpis[i].description;
												usage.kpi_clazz = $scope.kpis[i].clazz;
												usage.isSelected = false;
												$scope.kpiUsages.push(usage);
											}
										}
									});

					$http.get("/service/kpiconfigurations").success(
							function(response) {
								$scope.configurations = response;
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
					$scope.createNewConfiguration = function(configurationName,
							configurationDescription) {
						$http({
							url : '/service/newKpiConfiguration/',
							method : 'POST',
							params : {
								name : configurationName,
								description : configurationDescription
							}
						})
								.success(
										function(response) {

											BootstrapDialog
													.show({
														title : 'Information',
														message : 'New configuration created : '
																+ configurationName
													});
											$scope
													.addNewConfigurationInList(response);
										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot create configuration '
																+ configurationName
																+ '\n\n'
																+ 'Reason : '
																+ data.message
													});
										});
					}

					$scope.defineConfigurationDetails = function(config) {
						$scope.originalCurrentEditedConfiguration = config;
						$scope.currentEditedConfiguration = angular
								.copy(config);
						$scope.resetKpiSelection(config);
						$scope.resetUsageIds(config);
					}

					$scope.resetKpiSelection = function(config) {
						if ($scope.kpiUsages != null) {
							for (i = 0; i < $scope.kpiUsages.length; i++) {
								$scope.kpiUsages[i].isSelected = false;
								$scope.kpiUsages[i].id = null;
							}
							if (config.usages != null) {
								for (j = 0; j < config.usages.length; j++) {
									resetOneKpiSelection(config.usages[j].kpi.id);
								}
							}
						}
					}
					$scope.resetOneKpiSelection = function(id) {
						for (i = 0; i < $scope.kpiUsages.length; i++) {
							if ($scope.kpiUsages[i].kpi_id == id) {
								$scope.kpiUsages[i].isSelected = true;
								$scope.kpiUsages[i].id = id;
								return;
							}
						}
					}

					$scope.resetUsageIds = function(config) {
						if (config.usages != null) {
							for (i = 0; i < config.usages.length; i++) {
								$scope.setUsageId(config.usages[i].id,
										config.usages[i].kpi.id);
							}
						}
					}
					$scope.setUsageId = function(usageId, kpiId) {
						if ($scope.kpiUsages != null) {
							for (i = 0; i < $scope.kpiUsages.length; i++) {
								if ($scope.kpiUsages[i].kpi_id = kpiId) {
									$scope.kpiUsages[i].id = usageId;
								}
							}
						}
					}

					$scope.updateConfiguration = function(config) {
						// Copy selected Kpis
						config.usages = [];
						if ($scope.kpiUsages != null) {
							for (i = 0; i < $scope.kpiUsages.length; i++) {
								if ($scope.kpiUsages[i].isSelected) {
									var usage = new Object();
									usage.id = $scope.kpiUsages[i].id;
									usage.kpi = new Object();
									usage.kpi.id = $scope.kpiUsages[i].kpi_id;
									usage.kpi.name = $scope.kpiUsages[i].kpi_name;
									usage.kpi.kind = $scope.kpiUsages[i].kind;
									usage.kpi.clazz = $scope.kpiUsages[i].kpi_clazz;
									usage.kpi.description = $scope.kpiUsages[i].kpi_description;
									config.usages.push(usage);
								}
							}
						}
						$http
								.post('/service/updateKpiConfiguration/', config)
								.success(
										function(response) {

											BootstrapDialog
													.show({
														title : 'Information',
														message : 'Configuration updated : '
																+ configurationName
													});
											$scope
													.addNewConfigurationInList(response);
										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot update configuration '
																+ configurationName
																+ '\n\n'
																+ 'Reason : '
																+ data.message
													});
										});
					}
				});
