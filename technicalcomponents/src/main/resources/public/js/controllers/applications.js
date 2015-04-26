var applicationsControllers = angular
		.module('applicationsControllers', [ 'loggedUsers',
				'angularBootstrapNavTree', 'ui.bootstrap', 'xeditable' ]);

applicationsControllers.run(function(editableOptions) {
	editableOptions.theme = 'bs3';
});

applicationsControllers
		.controller(
				'applicationsController',
				function($scope, $rootScope, $http, $filter, $timeout) {

					// Chargement de toutes les applications....à revoir lors de
					// l'integration
					$scope.loadApps = function() {
						$scope.applications = [];
						$http.get("/service/applications").success(
								function(response) {
									$scope.applications = response;
									$scope.buildTree($scope.applications);
								});
					}
					$scope.applicationsTree = [];
					$scope.buildTree = function(apps) {
						$scope.applicationsTree = [];
						if (apps != null) {
							for (i = 0; i < apps.length; i++) {
								var node = {};
								var app = apps[i];
								node.label = app.name;
								node.obj = app;
								node.type = "APPLICATION";
								$scope.applicationsTree.push(node);
								if (app.versions != null
										&& app.versions.length > 0) {
									node.children = [];
									for (j = 0; j < app.versions.length; j++) {
										var nodeChild = {};
										nodeChild.label = app.versions[j].name;
										nodeChild.type = "APPLICATION_VERSION";
										nodeChild.isAppVersion = true;
										nodeChild.obj = app.versions[j];
										node.children.push(nodeChild);
									}
								}
							}
						}
					}

					$scope.searchComponents = null;
					$scope.searchComponentsFromFullPathNameExpression = "";
					$scope.searchComponentsFromFullPathName = function(
							searchComponentsFromFullPathNameExpression) {
						console.log(searchComponentsFromFullPathNameExpression);
						$http(
								{
									url : "/service/searchComponentsFromFullPathName",
									method : "GET",
									params : {
										regexp : searchComponentsFromFullPathNameExpression
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

					$scope.selectedApplicationVersion = null;
					$scope.selectedApplication = null;
					$scope.setCurrentSelectedApplicationVersion = function(node) {
						if (node.type == "APPLICATION_VERSION") {
							var application = node.obj;
							$scope.selectedApplicationVersion = application;
							$scope.selectedApplication = null;
							$scope.builtOn = [];
							$scope.initializeBuiltOn(application);
							$scope.canRunOn = [];
							$scope.initializeCanRunOn(application);
							$scope.loadExistingSnapshots();
						} else if (node.type == "APPLICATION") {
							$scope.selectedApplication = node.obj;
							$scope.selectedApplicationVersion = null;
						}
						$scope.currentSnapshot = null;
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

					$scope.resetFullSelection = function() {
						if ($scope.fullSelection != null) {
							for (i = 0; i < $scope.searchComponents.length; i++) {
								var c = $scope.searchComponents[i];
								c.selectedForAssociationWithApplication = false;
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
										+ $scope.selectedApplicationVersion.id)
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
												+ $scope.selectedApplicationVersion.id)
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

					$scope.createNewMeasurement = function(snapshot,user) {
						// Create a new measurement for current logged user...if
						// this
						// measurement does not already exist
						if (user==null) {
							user=$rootScope.currentUser;
						}
						if (snapshot.kpis != null) {
							// TODO check if not already exist

							$http({
								url : "/service/createMeasurement",
								method : "POST",
								params : {
									snapshotId : snapshot.id,
									userId : user.id
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

					$scope.allUsers = null;
					$scope.selectUserForNewMeasurementAndGo = function() {
						$scope.allUsers = $http
								.get("/service/allUsers")
								.success(
										function(all) {
											$scope.allUsers = all;
											$(
													'#addMeasurementForOtherUserDialog')
													.modal('show');

										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot retrieve all users\nReason is : '
																+ data.message
													});
										})

					}
					$scope.currentSelectedUser = null;
					$scope.setCurrentSelectedUser = function(user) {
						// We cannot set a user that already has a measurement
						if ($scope.alreadyExistMeasurementForUser(user)) {
							BootstrapDialog.show({
								title : 'Error',
								message : user.name+ ' already has made a measurement'
							});
						}else {
							$scope.currentSelectedUser = user;	
						}						
					}
					$scope.createNewMeasurementForOtherUserThanMe = function(
							snapshot) {
						if ($rootScope.currentUser.id == $scope.currentSelectedUser.id) {
							BootstrapDialog.show({
								title : 'Error',
								message : 'Not you, another user, please'
							});
						} else {
							$('#addMeasurementForOtherUserDialog')
									.modal('hide');
							$scope.createNewMeasurement($scope.currentSnapshot,$scope.currentSelectedUser);
						}
					}

					// Save entered value in measurement column
					$scope.saveOneValue = function(data, val) {
						if (val.kpi.minValue != null && data < val.kpi.minValue) {
							return "min value is " + val.kpi.minValue;
						}
						if (val.kpi.maxValue != null && data > val.kpi.maxValue) {
							return "max value is " + val.kpi.maxValue;
						}
						val.value = data;
					}
					$scope.saveMeasurement = function(data, measurement) {
						if ($scope.existSameMeasurementUser(measurement)) {
							BootstrapDialog.show({
								title : 'Error',
								message : 'No measurement for the same user'
							});
							return "ERROR";
						}
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

					$scope.existSameMeasurementUser = function(measurement) {
						for (i = 0; i < $scope.currentSnapshot.manualMeasurements.length; i++) {
							var other = $scope.currentSnapshot.manualMeasurements[i];
							if (other != measurement && other.who != null
									&& measurement.who != null
									&& measurement.who.name == other.who.name) {
								return true;
							}
						}
						return false;
					}

					$scope.alreadyExistMeasurementForLoggedUser = function() {
						return $scope.alreadyExistMeasurementForUser($rootScope.currentUser);						
					}
					
					$scope.alreadyExistMeasurementForUser = function(currentUser) {
						if ($scope.currentSnapshot == null) {
							return false;
						}
						if ($scope.currentSnapshot.manualMeasurements == null) {
							return false;
						}						

						for (i = 0; i < $scope.currentSnapshot.manualMeasurements.length; i++) {
							var other = $scope.currentSnapshot.manualMeasurements[i];
							if (other.who != null
									&& other.who.name == currentUser.name) {
								return true;
							}
						}
						return false;
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

						// We cannot create 2 snapshots for the same date
						if ($scope.existSnapshotWithSameDate(snapshot, theDate)) {
							BootstrapDialog
									.show({
										title : 'Error',
										message : 'Snapshot with this date already exists'
									});
							return "Wrong date";
						}

						frozenVal = false;
						if (data.frozen == null) {
							frozenVal = false;
						} else {
							frozenVal = true;
						}
						$http({
							url : "/service/updateSnapshot",
							method : "POST",
							params : {
								snapshotId : snapshot.id,
								frozen : frozenVal,
								forDate : theDate
							}
						})
								.success(
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

					$scope.existSnapshotWithSameDate = function(snapshot,
							theDate) {
						for (i = 0; i < $scope.currentSnapshots.length; i++) {
							var otherSnapshot = $scope.currentSnapshots[i];
							if (otherSnapshot != snapshot) {
								var otherDate = Date
										.parse(otherSnapshot.forDate);
								var toCompare = Date.parse(theDate);
								if (otherDate.getFullYear() == toCompare
										.getFullYear()
										&& otherDate.getDate() == toCompare
												.getDate()
										&& otherDate.getMonth() == toCompare
												.getMonth())
									return true;
							}
						}
						return false;
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

						$http
								.get(
										"/service/computeAutomaticApplicationKpis/"
												+ snapshot.id)
								.success(
										function(kpis) {
											if (kpis != null) {
												snapshot.computedKpis = [];
												for (i = 0; i < kpis.length; i++) {
													snapshot.computedKpis
															.push(kpis[i]);
												}
											} else {
												// Should not happen
												BootstrapDialog
														.show({
															title : 'Strange',
															message : 'No automatic KPIs were computed'
														});
											}
										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot compute automatic KPIs\nReason is : '
																+ data.message
													});
										});
					}
					// Charts
					$scope.refreshMeasurementsKiviaChart = function() {
						// Labels are kpi names
						var kpis = $scope
								.manualMeasurementsOfSnapshot($scope.currentSnapshot);

						// 20 colors for the 20 first measurements
						var colors = [ "rgba(220,220,200", "rgba(151,187,205",
								"rgba(217,204,255", "rgba(242,204,255",
								"rgba(255,212,82", "rgba(217,255,204",
								"rgba(255,153,179", "rgba(153,230,255",
								"rgba(110,110,207", "rgba(191,64,64",
								"rgba(64,191,191", "rgba(128,191,64",
								"rgba(128,64,191", "rgba(57,172,172",
								"rgba(172,57,57", "rgba(57,57,172",
								"rgba(172,57,115", "rgba(184,0,147",
								"rgba(0,184,37", ];
						// First, need to compute max values because Radar uses
						// percentages, not absolute values
						var maxValues = [];
						for (j = 0; j < $scope.currentSnapshot.manualMeasurements.length; j++) {
							var measurement = $scope.currentSnapshot.manualMeasurements[j];
							for (k = 0; k < measurement.values.length; k++) {
								if (measurement.values[k].value != null
										&& (maxValues[k] == null || maxValues[k] < measurement.values[k].value)) {
									maxValues[k] = measurement.values[k].value;
								}
							}
						}
						var datasetsD3 = [];

						for (j = 0; j < $scope.currentSnapshot.manualMeasurements.length; j++) {
							var measurement = $scope.currentSnapshot.manualMeasurements[j];
							var dataset = {};
							if (j < colors.length) {
								colorStr = colors[j];
							} else {
								colorStr = "rgba(230,230,230";
							}

							datasetD3 = [];

							for (k = 0; k < measurement.values.length; k++) {
								datasetD3.push({
									axis : measurement.values[k].kpi.shortName,
									value : measurement.values[k].value
											/ maxValues[k]
								})
							}
							datasetsD3.push(datasetD3);

						}

						// D3
						var w = 600, h = 600;

						var colorscale = d3.scale.category10();

						// Options for the Radar chart, other than default
						var mycfg = {
							w : w,
							h : h,
							maxValue : 0.8,
							levels : 6,
							ExtraWidthX : 300
						}
						RadarChart.draw("#chart", datasetsD3, mycfg);

					}

					// Application Kpi dashboard of $scope.selectedApplication
					$scope.dashboards = new Array();
					$scope.buildKpiHistoryDashboard = function() {
						$http
								.get(
										"/service/getApplicationDashboard/"
												+ $scope.selectedApplication.id)
								.success(
										function(dashboard) {
											if (dashboard.kpiHistories != null
													&& dashboard.kpiHistories.length > 0) {
												$scope.dashboards[$scope.selectedApplication.id] = new Object();
												$scope.dashboards[$scope.selectedApplication.id].dashboard = dashboard;
												$scope.dashboards[$scope.selectedApplication.id].num_graphs = dashboard.kpiHistories.length;
												// Graphs structured by kpi
												// category
												// First, create level one that
												// contains category names + the
												// default one when no category
												// is defined for a kpi
												$scope.dashboards[$scope.selectedApplication.id].categories = [];
												$scope.dashboards[$scope.selectedApplication.id].categories["default"] = {
													catName : "default",
													graphNames : new Array()
												};
												$scope.dashboards[$scope.selectedApplication.id].categoryNames = [ "default" ];
												for (k = 0; k < dashboard.kpiHistories.length; k++) {
													var hist = dashboard.kpiHistories[k];
													if (hist.kpiCategory != null
															&& hist.kpiCategory != "") {
														if ($scope.dashboards[$scope.selectedApplication.id].categories[hist.kpiCategory] == null) {
															$scope.dashboards[$scope.selectedApplication.id].categories[hist.kpiCategory] = {
																catName : hist.kpiCategory,
																graphNames : new Array()
															}
															$scope.dashboards[$scope.selectedApplication.id].categoryNames
																	.push(hist.kpiCategory);
														}
													}
												}
												// Now add graph names into
												// category
												for (k = 0; k < dashboard.kpiHistories.length; k++) {
													var hist = dashboard.kpiHistories[k];
													var names = {
														id : $scope.selectedApplication.id
																+ "_hist_" + k,
														name : dashboard.kpiHistories[k].kpiName,
														isComputed : dashboard.kpiHistories[k].computed
													}
													var catRank = hist.kpiCategory;
													if (hist.kpiCategory == null
															|| hist.kpiCategory == "") {
														catRank = "default";
													}
													$scope.dashboards[$scope.selectedApplication.id].categories[catRank].graphNames
															.push(names);
												}
												$timeout(
														$scope.refreshKpiDashboard,
														1000);
											}
										})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot delete measurement\nReason is : '
																+ data.message
													})
										});
					}
					// Dashboard graphs creation after loading dashboard data
					$scope.refreshKpiDashboard = function() {
						var dashboard = $scope.dashboards[$scope.selectedApplication.id];
						for (k = 0; k < dashboard.dashboard.kpiHistories.length; k++) {
							var history = dashboard.dashboard.kpiHistories[k];
							// graphnames are in...graphNames of category
							var catRank = history.kpiCategory;
							if (history.kpiCategory == null
									|| history.kpiCategory == "") {
								catRank = "default";
							}
							var graphNames = dashboard.categories[catRank].graphNames;
							for (i = 0; i < graphNames.length; i++) {
								var nameOfUIGraph = graphNames[i].id;
								var doc = document;
								var element = document
										.getElementById(nameOfUIGraph);
								var ctx = element.getContext("2d");
								// Build data
								var data = {};
								var dates = [];
								var points = [];
								// X-axis labels are the dates
								for (j = 0; j < history.values.length; j++) {
									dates.push(history.values[j].dateStr);
									points.push(history.values[j].value);
								}
								data.labels = dates;
								data.datasets = [ {
									label : history.kpiName,
									fillColor : "rgba(151,187,205,0.2)",
									strokeColor : "rgba(151,187,205,1)",
									pointColor : "rgba(151,187,205,1)",
									pointStrokeColor : "#fff",
									pointHighlightFill : "#fff",
									pointHighlightStroke : "rgba(151,187,205,1)",
									data : points
								} ];

								// build options
								var options = {

									// /Boolean - Whether grid lines are shown
									// across the chart
									scaleShowGridLines : true,

									scaleBeginAtZero : true,

									// String - Colour of the grid lines
									scaleGridLineColor : "rgba(0,0,0,.05)",

									// Number - Width of the grid lines
									scaleGridLineWidth : 1,

									// Boolean - Whether to show horizontal
									// lines
									// (except X axis)
									scaleShowHorizontalLines : true,

									// Boolean - Whether to show vertical lines
									// (except Y axis)
									scaleShowVerticalLines : true,

									// Boolean - Whether the line is curved
									// between
									// points
									bezierCurve : true,

									// Number - Tension of the bezier curve
									// between
									// points
									bezierCurveTension : 0.4,

									// Boolean - Whether to show a dot for each
									// point
									pointDot : true,

									// Number - Radius of each point dot in
									// pixels
									pointDotRadius : 4,

									// Number - Pixel width of point dot stroke
									pointDotStrokeWidth : 1,

									// Number - amount extra to add to the
									// radius to
									// cater for hit detection outside the drawn
									// point
									pointHitDetectionRadius : 20,

									// Boolean - Whether to show a stroke for
									// datasets
									datasetStroke : true,

									// Number - Pixel width of dataset stroke
									datasetStrokeWidth : 2,

									// Boolean - Whether to fill the dataset
									// with a
									// colour
									datasetFill : true,

								};
								// Set Line chart with data & options
								var chart = new Chart(ctx).Line(data, options);
							}
						}
					}

					// Init
					$scope.loadApps();
				});
