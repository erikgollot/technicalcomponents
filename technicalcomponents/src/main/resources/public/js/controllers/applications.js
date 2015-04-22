var applicationsControllers = angular.module('applicationsControllers', [
		'ui.bootstrap', 'xeditable' ]);

applicationsControllers.run(function(editableOptions) {
	editableOptions.theme = 'bs3';
});

applicationsControllers
		.controller(
				'applicationsController',
				function($scope, $http, $filter) {

					// Chargement de toutes les applications....à revoir lors de
					// l'integration
					$scope.loadApps = function() {
						$http.get("/service/applications").success(
								function(response) {
									$scope.applications = response;
								});
					}
					$scope.searchComponents = null;
					$scope.searchComponentsFromFullPathName = function() {
						console
								.log($scope.searchComponentsFromFullPathNameExpression);
						$http(
								{
									url : "/service/searchComponentsFromFullPathName",
									method : "GET",
									params : {
										regexp : $scope.searchComponentsFromFullPathNameExpression
									}
								}).success(function(response) {
							$scope.searchComponents = response;
						}).error(function(data, status, headers, config) {
							BootstrapDialog.show({
								title : 'Error',
								message : data.message
							});
						});
					}

					$scope.setCurrentSelectedApplication = function(application) {
						$scope.selectedApplication = application;
						$scope.builtOn = [];
						$scope.initializeBuiltOn(application);
						$scope.canRunOn = [];
						$scope.initializeCanRunOn(application);
						$scope.loadExistingSnapshots();

					}
					$scope.initializeBuiltOn = function(application) {
						if (application.builtOn != null
								&& application.builtOn.components != null) {
							for (i = 0; i < application.builtOn.components.length; i++) {
								var c = application.builtOn.components[i];
								$scope.builtOn.push(c);
							}
						}
					}
					$scope.initializeCanRunOn = function(application) {
						if (application.canRunOn != null
								&& application.canRunOn.components != null) {
							for (i = 0; i < application.canRunOn.components.length; i++) {
								var c = application.canRunOn.components[i];
								$scope.canRunOn.push(c);
							}
						}
					}

					$scope.updateFullSelection = function() {
						// Pop all members
						if ($scope.fullSelection != null) {
							$scope.fullSelection.length = 0;
						} else {
							$scope.fullSelection = [];
						}
						for (i = 0; i < $scope.searchComponents.length; i++) {
							var c = $scope.searchComponents[i];
							if (c.selectedForAssociationWithApplication) {
								$scope.fullSelection.push(c);
							}
						}
					}
					$scope.hasSelection = function() {
						if ($scope.fullSelection != null
								&& $scope.fullSelection.length > 0) {
							return true;
						} else {
							return false;
						}
					}

					// Add selected components, without duplicate !!, into
					// builtOnComponents.
					// This is the save that will really set the builtOn list of
					// the application
					$scope.addToBuiltOn = function(application) {
						if ($scope.fullSelection != null
								&& $scope.fullSelection.length > 0) {
							for (i = 0; i < $scope.fullSelection.length; i++) {
								if (application.builtOn == null) {
									application.builtOn = new Object();
								}
								if (application.builtOn.components == null) {
									application.builtOn.components = [];
								}
								$scope.addIfNotExist($scope.fullSelection[i],
										application.builtOn.components);
							}
						}
					}

					$scope.addIfNotExist = function(c, components) {
						for (j = 0; j < components.length; j++) {
							if (components[j].id == c.id) {
								return;
							}
						}
						components.push(angular.copy(c));
						for (k = 0; k < components.length; k++) {
							components[k].selectedForAssociationWithApplication = false;
						}
					}

					$scope.removeFromBuiltOn = function(app) {
						var local = [];
						for (i = 0; i < app.builtOn.components.length; i++) {
							var c = app.builtOn.components[i];
							if (c.selectedForAssociationWithApplication) {
								local.push(i);
							}
						}
						for (j = 0; j < local.length; j++) {
							app.builtOn.components.splice(local[j], 1);
						}
					}

					$scope.addToCanRunOn = function(application) {
						if ($scope.fullSelection != null
								&& $scope.fullSelection.length > 0) {
							for (i = 0; i < $scope.fullSelection.length; i++) {
								if (application.canRunOn == null) {
									application.canRunOn = new Object();
								}
								if (application.canRunOn.components == null) {
									application.canRunOn.components = [];
								}
								$scope.addIfNotExist($scope.fullSelection[i],
										application.canRunOn.components);
							}
						}
					}

					$scope.removeFromCanRunOn = function(app) {
						var local = [];
						for (i = 0; i < app.canRunOn.components.length; i++) {
							var c = app.canRunOn.components[i];
							if (c.selectedForAssociationWithApplication) {
								local.push(i);
							}
						}
						for (j = 0; j < local.length; j++) {
							app.canRunOn.components.splice(local[j], 1);
						}
					}

					$scope.hasSelectionInBuiltOn = function(application) {
						if (application == null) {
							return false;
						} else {
							if (application.builtOn != null
									&& application.builtOn.components != null) {
								for (i = 0; i < application.builtOn.components.length; i++) {
									var c = application.builtOn.components[i];
									if (c.selectedForAssociationWithApplication)
										return true;
								}
							}
							return false;
						}
					}

					$scope.hasSelectionInCanRunOn = function(application) {
						if (application == null) {
							return false;
						} else {
							if (application.canRunOn != null
									&& application.canRunOn.components != null) {
								for (i = 0; i < application.canRunOn.components.length; i++) {
									var c = application.canRunOn.components[i];
									if (c.selectedForAssociationWithApplication)
										return true;
								}
							}
							return false;
						}
					}

					$scope.saveComponentsOfApplication = function(application) {
						console.log(application);
						$http.post(
								"/service/application/setTechnicalComponents",
								application).success(function(response) {
							BootstrapDialog.show({
								title : 'Information',
								message : "Components saved"
							});
						}).error(function(data, status, headers, config) {
							BootstrapDialog.show({
								title : 'Error',
								message : data.message
							});
						});
					}

					// Snapshot functions
					$scope.hasSnapshot = false;
					$scope.takeNewSnapshot = function() {
						$http.get(
								"/service/createSnapshot/"
										+ $scope.selectedApplication.id)
								.success(function(response) {
									if (response != null) {
										if ($scope.currentSnapshots == null) {
											$scope.currentSnapshots = [];
										}
										$scope.currentSnapshots.push(response);
									}
									$scope.hasSnapshot = true;
								})
								.error(function(data, status, headers, config) {
									BootstrapDialog.show({
										title : 'Error',
										message : data.message
									});
								});
					}
					$scope.loadExistingSnapshots = function() {
						$scope.hasSnapshot = false;
						$scope.currentSnapshots = null;
						$http
								.get(
										"/service/getSnapshots/"
												+ $scope.selectedApplication.id)
								.success(
										function(response) {
											if (response != null
													&& response.length > 0) {
												$scope.currentSnapshots = response;
												$scope.hasSnapshot = true;
											}
										})
								.error(function(data, status, headers, config) {
									BootstrapDialog.show({
										title : 'Error',
										message : data.message
									});
								});
					}

					$scope.showSnapshotMeasurements = function(snapshot) {
						$scope.currentSnapshot = snapshot;
					}

					$scope.createNewMeasurement = function(snapshot) {
						// Create a new measurement for current logged user...if
						// this
						// measurement does not already exist

						if (snapshot.kpis != null) {
							// TODO check if not already exist

							$http({
								url : "/service/createMeasurement",
								method : "POST",
								params : {
									snapshotId : snapshot.id,
									userId : 1
								}
							})
									.success(
											function(response) {
												// set UI specific values (min
												// max)
												var measurement = response;
												if (measurement.values != null) {
													for (i = 0; i < measurement.values.length; i++) {
														var value = measurement.values[i];

														if (value.kpi.minValue == null) {
															value.kpi.minValue = Number.MIN_SAFE_INTEGER;
														}
														if (value.kpi.maxValue == null) {
															value.kpi.maxValue = Number.MAX_SAFE_INTEGER;
														}
														if (value.kpi.kind
																.indexOf("MANUAL_APPLICATION_NUMERIC") == 0) {
															if (value.kpi.int) {
																value.kpi.step = "1";
															} else {
																value.kpi.step = "any";
															}
														}
													}
												}
												if (snapshot.manualMeasurements == null) {
													snapshot.manualMeasurements = new Array();
												}
												snapshot.manualMeasurements
														.push(measurement);
											})
									.error(
											function(data, status, headers,
													config) {
												BootstrapDialog
														.show({
															title : 'Error',
															message : 'Cannot create snapshot\nReason is : '
																	+ data.message
														});
											});
						}
					}
					
					// Save entered value in measurement column
					$scope.saveOneValue = function(data, val) {
						if (val.kpi.minValue !=null && data <val.kpi.minValue) {
							return "min value is " + val.kpi.minValue;
						}
						if (val.kpi.maxValue !=null && data > val.kpi.maxValue) {
							return "max value is " + val.kpi.maxValue;
						}						
						val.value = data;
					}
					$scope.saveMeasurement = function(data, measurement) {

						$http
								.post("/service/updateMeasurement", measurement)
								.success(
										function(response) {
											if (response != null
													&& response.length > 0) {
												measurement = response;
											}
										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot update measurement\nReason is : '
																+ data.message
													});
										});
					}
					$scope.convertDate = function(aDate) {
						var formatedDate = Date.parse(aDate);
						var formatedDateAsString = "";
						formatedDateAsString = formatedDate.getFullYear() + "-"
								+ (formatedDate.getMonth() + 1) + "-"
								+ formatedDate.getDate();
						return formatedDateAsString;
					}
					$scope.updateSnapshot = function(data, snapshot) {	
						theDate = $scope.convertDate(data.forDate);
						frozenVal = false;
						if (data.frozen == null) {
							frozenVal = false;
						}
						else {
							frozenVal = true;
						}
						$http({
							url : "/service/updateSnapshot",
							method : "POST",
							params : {
								snapshotId : snapshot.id,
								frozen : frozenVal,
								forDate: theDate
							}
						}).success(
								function(response) {
									if (response != null
											&& response.length > 0) {
										snapshot = response;
									}
								})
						.error(
								function(data, status, headers, config) {
									BootstrapDialog
											.show({
												title : 'Error',
												message : 'Cannot update snapshot\nReason is : '
														+ data.message
											});
								});
					}
					$scope.deleteSnapshot = function(idx) {
						snapshot = $scope.currentSnapshots[idx];
						BootstrapDialog.show({
							title : 'Delete snapshot',
							message : 'Please confirm suppression of snapshot',
							buttons : [ {
								label : 'Cancel',
								action : function(dialog) {
									dialog.close();
								}
							}, {
								label : 'Confirm',
								action : function(dialog) {
									dialog.close();
									$scope.deleteSnapshotOnServer(snapshot);
								}
							} ]
						});

					}
					$scope.deleteSnapshotOnServer = function(snapshot) {
						$http
								.post("/service/deleteSnapshot/" + snapshot.id)
								.success(
										function(response) {
											$scope.currentSnapshots = $scope.currentSnapshots
													.filter(function(el) {
														return el.id != snapshot.id;
													});

										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot delete snapshot\nReason is : '
																+ data.message
													});
										});
					}
					$scope.deleteMeasurement = function(idx) {
						measurement = $scope.currentSnapshot.manualMeasurements[idx]
						BootstrapDialog
								.show({
									title : 'Delete measurement',
									message : 'Please confirm suppression of measurement',
									buttons : [
											{
												label : 'Cancel',
												action : function(dialog) {
													dialog.close();
												}
											},
											{
												label : 'Confirm',
												action : function(dialog) {
													dialog.close();
													$scope
															.deleteMeasurementOnServer(measurement.id);
												}
											} ]
								});

					}
					$scope.deleteMeasurementOnServer = function(measurementId) {
						$http
								.post(
										"/service/deleteMeasurement/"
												+ measurementId)
								.success(
										function(response) {
											$scope.currentSnapshot.manualMeasurements = $scope.currentSnapshot.manualMeasurements
													.filter(function(el) {
														return el.id != measurementId;
													});

										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot delete measurement\nReason is : '
																+ data.message
													});
										});
					}
					// For display of an enum literal in table
					// For example : val is 1 and 1 means BAD, we want to
					// display BAD, not 1
					$scope.showValueEnum = function(kpi, val) {
						var selected = $filter('filter')(kpi.literals, {
							value : val
						});
						return (val && selected.length) ? selected[0].name
								: 'Not set';
					}

					$scope.manualMeasurementsOfSnapshot = function(snapshot) {
						if (snapshot != null && snapshot.kpis != null) {
							var kpis = new Array();
							for (i = 0; i < snapshot.kpis.length; i++) {
								var kpi = snapshot.kpis[i];
								if (kpi.kind != null
										&& kpi.kind
												.indexOf("MANUAL_APPLICATION_") == 0) {
									kpis.push(kpi);
								}
							}
							return kpis;
						}
					}

					$scope.computeAutomaticKpi = function(snapshot) {
						// TODO server call
						// For Test
						if (snapshot != null && snapshot.kpis != null) {
							snapshot.computedKpis = new Array();
							for (i = 0; i < snapshot.kpis.length; i++) {
								var kpi = snapshot.kpis[i];
								if (kpi.kind != null
										&& kpi.kind
												.indexOf("COMPUTED_APPLICATION_") == 0) {
									// On simule
									var val = new Object();
									val.value = 1.25;
									val.kpi = {
										name : kpi.name
									}
									snapshot.computedKpis.push(val);
								}
							}
						}
					}

					// Init
					$scope.loadApps();

				});
