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

					$scope.addNewEmptyNumericKpi = function() {
						akpi = {
							isActive : false,
							isInt : false,
							kind : "MANUAL_NUMERIC",
							clazz : "com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView"
						};
						$scope.kpis.push(akpi);
					}

					$scope.addNewEmptyEnumKpi = function() {
						akpi = {
							isActive : false,
							kind : "MANUAL_ENUM",
							clazz : "com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView"
						};
						$scope.kpis.push(akpi);
					}

					$scope.saveKpi = function(data, kpi) {
						message = {
							success : "created",
							error : "creation"
						};
						isCreate = true;
						angular.extend(data, {
							clazz : kpi.clazz,
							id : kpi.id,
							kind : kpi.kind
						});
						if (kpi.kind == "MANUAL_NUMERIC") {
							if (kpi.id == null) {
								url = '/service/createNumericKpi';
							} else {
								url = '/service/updateNumericKpi';
								message.success = "updated";
								message.error = "update";
								isCreate = false;
							}
							return $http
									.post(url, data)
									.success(
											function(response) {

												BootstrapDialog
														.show({
															title : 'Information',
															message : 'Key Performance Indicator '
																	+ data.name
																	+ " "
																	+ message.success
														});
												if (isCreate) {
													kpi.id = response.id;
												}
											})
									.error(
											function(data, status, headers,
													config) {
												BootstrapDialog
														.show({
															title : 'Error',
															message : 'Key Performance Indicator ('
																	+ +data.name
																	+ " "
																	+ message.error
																	+ " error"
																	+ '\n\n'
																	+ 'Reason : '
																	+ data.message
														});

											});

						} else {
							// MANUAL_ENUM
							if (kpi.id == null) {
								url = '/service/createEnumKpi';
							} else {
								url = '/service/updateEnumKpi';
								message.success = "updated";
								message.error = "update"
							}
							return $http
									.post('/service/createEnumKpi', data)
									.success(
											function(response) {

												BootstrapDialog
														.show({
															title : 'Information',
															message : 'Key Performance Indicator '
																	+ data.name
																	+ " "
																	+ message.success
														});
												if (isCreate) {
													kpi.id = response.id;
												}
											})
									.error(
											function(data, status, headers,
													config) {
												BootstrapDialog
														.show({
															title : 'Error',
															message : 'Key Performance Indicator ('
																	+ +kpi.name
																	+ " "
																	+ message.error
																	+ " error"
																	+ '\n\n'
																	+ 'Reason : '
																	+ data.message
														});
											});
						}
					};

					// remove numeric kpi from list
					$scope.removeNumericKpi = function(index) {
						// kpi is filtered so need to recalculate index
						var filtered = [];
						angular.forEach($scope.kpis, function(kpi) {
							if (kpi.kind == "MANUAL_NUMERIC") {
								filtered.push(kpi);
							}
						});
						toRemove = filtered[index];
						newIndex = 0;
						for (i = 0; i < $scope.kpis.length; i++) {
							if ($scope.kpis[i] == toRemove) {
								newIndex = i;
								break;
							}
						}
						// Now try to physically remove the kpi in database
						$scope.removeKpiOnServer(newIndex);
					};

					// remove numeric kpi from list
					$scope.removeEnumKpi = function(index) {
						// kpi is filtered so need to recalculate index
						var filtered = [];
						angular.forEach($scope.kpis, function(kpi) {
							if (kpi.kind == "MANUAL_ENUM") {
								filtered.push(kpi);
							}
						});
						toRemove = filtered[index];
						newIndex = 0;
						for (i = 0; i < $scope.kpis.length; i++) {
							if ($scope.kpis[i] == toRemove) {
								newIndex = i;
								break;
							}
						}
						// Now try to physically remove the kpi in database
						if (toRemove.id != null) {
							$scope.removeKpiOnServer(toRemove,newIndex);
						} else {
							$scope.kpis.splice(newIndex, 1);
						}
					};

					$scope.removeKpiOnServer = function(kpi,newIndex) {
						$http.post('/service/deleteKpi', kpi.id).success(
								function(response) {

									BootstrapDialog.show({
										title : 'Information',
										message : 'Key Performance Indicator '
												+ kpi.name + " removed"
									});
									$scope.kpis.splice(newIndex, 1);
								}).error(
								function(data, status, headers, config) {
									BootstrapDialog.show({
										title : 'Error',
										message : 'Key Performance Indicator ('
												+ +kpi.name
												+ " cannot be removed"
												+ " error" + '\n\n'
												+ 'Reason : ' + data.message
									});

								});
					}

					$scope.saveLiterals = function(data, editedEnumKpi, literal) {
						angular.extend(data, {
							id : literal.id
						});
						if (literal.id == null) {
							// Create
							return $http.post(
									'/service/addLiteral/' + editedEnumKpi.id,
									data).success(
									function(response) {

										BootstrapDialog.show({
											title : 'Information',
											message : 'Literal ' + data.name
													+ " created"
										});
										literal.id = response.id;
									}).error(
									function(data, status, headers, config) {
										BootstrapDialog.show({
											title : 'Error',
											message : 'Literal (' + data.name
													+ " cannot be created"
													+ " error" + '\n\n'
													+ 'Reason : '
													+ data.message
										});

									});
						} else {
							// Update
							return $http
									.post('/service/updateLiteral', data)
									.success(
											function(response) {

												BootstrapDialog.show({
													title : 'Information',
													message : 'Literal '
															+ data.name
															+ " updated"
												});
											})
									.error(
											function(data, status, headers,
													config) {
												BootstrapDialog
														.show({
															title : 'Error',
															message : 'Literal ('
																	+ +literal.name
																	+ " cannot be updated"
																	+ " error"
																	+ '\n\n'
																	+ 'Reason : '
																	+ data.message
														});

											});
						}
					};

					$scope.removeEnumLiteral = function(index) {
						if ($scope.editedEnumKpi.literals[index].id != null) {
							$http
									.post(
											'/service/deleteLiteral',
											$scope.editedEnumKpi.literals[index].id)
									.success(
											function(response) {

												BootstrapDialog
														.show({
															title : 'Information',
															message : 'Literal '
																	+ $scope.editedEnumKpi.literals[index].name
																	+ " updated"
														});
												$scope.editedEnumKpi.literals
														.splice(index, 1);
											})
									.error(
											function(data, status, headers,
													config) {
												BootstrapDialog
														.show({
															title : 'Error',
															message : 'Literal ('
																	+ +$scope.editedEnumKpi.literals[index].name
																	+ " cannot be updated"
																	+ " error"
																	+ '\n\n'
																	+ 'Reason : '
																	+ data.message
														});

											});
						} else {
							// Only remove in the ui list
							$scope.editedEnumKpi.literals.splice(index, 1);
						}

					}

					$scope.addLiteralIntoEditEnumKpi = function() {
						aLiteral = {};
						if ($scope.editedEnumKpi.literals == null) {
							$scope.editedEnumKpi.literals = [];
						}
						$scope.editedEnumKpi.literals.push(aLiteral);
					}

					$scope.editedEnumKpi = {};
					$scope.hasEditedEnumKpi = false;
					$scope.setEditedEnumKpi = function(kpi) {
						$scope.editedEnumKpi = kpi;
						$scope.hasEditedEnumKpi = true;
					}

				}); // End controller

// Specific filters
kpisControllers.filter('manual_kpi_filter', function() {
	return function(kpis) {
		var filtered = [];
		angular.forEach(kpis, function(kpi) {
			if (kpi.kind == "MANUAL_ENUM" || kpi.kind == "MANUAL_NUMERIC") {
				filtered.push(kpi);
			}
		});
		return filtered;
	};
});

kpisControllers.filter('computed_kpi_filter', function() {
	return function(kpis) {
		var filtered = [];
		angular.forEach(kpis, function(kpi) {
			if (kpi.kind.indexOf("COMPUTED_") == 0) {
				filtered.push(kpi);
			}
		});
		return filtered;
	};
});