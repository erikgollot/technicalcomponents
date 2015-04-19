var kpisControllers = angular.module('kpisControllers', [ 'angularFileUpload',
		'xeditable', 'angularModalService' ]);

kpisControllers.run(function(editableOptions) {
	editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2',
	// 'default'
});

kpisControllers
		.controller(
				'kpisController',
				function($scope, $http, $upload, $sce) {

					// Pour essai
					$scope.setActive = function(val) {
						console.log("here ", val);
						$scope.user.is_active = val;
					};
					$scope.user = {
						is_active : true,
						name : "Erik"
					}

					$scope.numericKpis = [ {
						name : "kpi1",
						description : "kpi1 description",
						isActive : true,
						isInteger : true,
						minValue : null,
						maxValue : 200
					}, {
						name : "kpi2",
						description : "kpi2 description",
						isActive : true,
						isInteger : false,
						minValue : -52,
						maxValue : 100
					}, {
						name : "kpi3",
						description : "kpi3 description",
						isActive : true,
						isInteger : false,
						minValue : 0,
						maxValue : 100
					} ];

					$scope.hasKpi = false;
					$scope.kpiUsages = [];
					$scope.newKpi = {};
					$scope.type = "Numeric";

					$http
							.get("/service/kpis")
							.success(
									function(response) {
										$scope.kpis = response;
										// Copy for usages selection
										if ($scope.kpis != null) {
											for (i = 0; i < $scope.kpis.length; i++) {
												var usage = new Object();
												usage.usageId = null;
												usage.kpiId = $scope.kpis[i].id;
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
					$scope.addNewOrUpdateConfigurationInList = function(conf) {
						if ($scope.configurations == null) {
							$scope.configurations = [];
						}
						// Search if it's an existing one
						found = false;
						for (i = 0; i < $scope.configurations.length; i++) {
							if ($scope.configurations[i].id == conf.id) {
								$scope.configurations
										.pop($scope.configurations[i]);
								$scope.configurations.push(conf);
								found = true;
								break;
							}
						}
						// It' a new one, just add it
						if (!found) {
							$scope.configurations.push(conf);
						}
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
													.addNewOrUpdateConfigurationInList(response);
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
						$scope.currentEditedConfiguration = angular
								.copy(config);
						$scope.resetKpiSelection(config);
						$scope.resetUsageIds(config);
					}

					$scope.resetKpiSelection = function(config) {
						if ($scope.kpiUsages != null) {
							for (i = 0; i < $scope.kpiUsages.length; i++) {
								$scope.kpiUsages[i].isSelected = false;
								$scope.kpiUsages[i].usageId = null;
							}
							if (config.usages != null) {
								for (j = 0; j < config.usages.length; j++) {
									$scope.resetOneKpiSelection(
											config.usages[j].id,
											config.usages[j].kpi.id);
								}
							}
						}
					}
					$scope.resetOneKpiSelection = function(usageId,
							kpiIdToSelect) {
						for (i = 0; i < $scope.kpiUsages.length; i++) {
							if ($scope.kpiUsages[i].kpiId == kpiIdToSelect) {
								$scope.kpiUsages[i].isSelected = true;
								$scope.kpiUsages[i].usageId = usageId;
								return;
							}
						}
					}

					$scope.resetUsageIds = function(config) {
						if (config.usages != null) {
							for (i = 0; i < config.usages.length; i++) {
								$scope.setUsageId(config.usages[i].usageId,
										config.usages[i].kpi.id);
							}
						}
					}
					$scope.setUsageId = function(usageId, kpiId) {
						if ($scope.kpiUsages != null) {
							for (i = 0; i < $scope.kpiUsages.length; i++) {
								if ($scope.kpiUsages[i].kpiId == kpiId) {
									$scope.kpiUsages[i].usageId = usageId;
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
									usage.usageId = $scope.kpiUsages[i].usageId;
									usage.kpi = new Object();
									usage.kpi.id = $scope.kpiUsages[i].kpiId;
									usage.kpi.name = $scope.kpiUsages[i].kpi_name;
									usage.kpi.kind = $scope.kpiUsages[i].kind;
									usage.kpi.clazz = $scope.kpiUsages[i].kpi_clazz;
									usage.kpi.description = $scope.kpiUsages[i].kpi_description;
									config.usages.push(usage);
								}
							}
						}
						$http
								.post('/service/updateKpiConfiguration/',
										config)
								.success(
										function(response) {

											BootstrapDialog
													.show({
														title : 'Information',
														message : 'Configuration updated : '
																+ config.name
													});

											$scope
													.addNewOrUpdateConfigurationInList(response);
											$scope
													.defineConfigurationDetails(response);

										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot update configuration '
																+ config.name
																+ '\n\n'
																+ 'Reason : '
																+ data.message
													});
										});
					}

					$scope.createNewKpi = function(newKpi) {
						console.log(newKpi);
					}
					$scope.changeType = function() {

					}
					
					$scope.addNewEmptyKpi=function() {
						akpi = {isActive : false,isInteger : false};											
						$scope.numericKpis.push(akpi);
					} 

				});
