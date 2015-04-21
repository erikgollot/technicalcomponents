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

					$scope.displaySafeHtml = function(html) {
						return $sce.trustAsHtml(html);
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
							kind : "MANUAL_APPLICATION_NUMERIC",
							clazz : "com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView"
						};
						$scope.kpis.push(akpi);
					}

					$scope.addNewEmptyEnumKpi = function() {
						akpi = {
							isActive : false,
							kind : "MANUAL_APPLICATION_ENUM",
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
						if (kpi.kind == "MANUAL_APPLICATION_NUMERIC") {
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
							// MANUAL_APPLICATION_ENUM
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
							if (kpi.kind == "MANUAL_APPLICATION_NUMERIC") {
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
							if (kpi.kind == "MANUAL_APPLICATION_ENUM") {
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
kpisControllers.filter('manual_application_kpi_filter', function() {
	return function(kpis) {
		var filtered = [];
		angular.forEach(kpis, function(kpi) {
			if (kpi.kind == "MANUAL_APPLICATION_ENUM" || kpi.kind == "MANUAL_APPLICATION_NUMERIC") {
				filtered.push(kpi);
			}
		});
		return filtered;
	};
});

kpisControllers.filter('computed_application_kpi_filter', function() {
	return function(kpis) {
		var filtered = [];
		angular.forEach(kpis, function(kpi) {
			if (kpi.kind.indexOf("COMPUTED_APPLICATION") == 0) {
				filtered.push(kpi);
			}
		});
		return filtered;
	};
});