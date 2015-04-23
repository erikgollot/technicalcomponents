var applicationsControllers = angular.module('applicationsControllers', [
		'angularBootstrapNavTree', 'ui.bootstrap', 'xeditable' ]);

applicationsControllers.run(function(editableOptions) {
	editableOptions.theme = 'bs3';
});

applicationsControllers
		.controller(
				'applicationsController',
				function($scope, $http, $filter,$timeout) {

					// Chargement de toutes les applications....à revoir lors de
					// l'integration
					$scope.loadApps = function() {
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
					$scope.searchComponentsFromFullPathNameExpression="";
					$scope.searchComponentsFromFullPathName = function(searchComponentsFromFullPathNameExpression) {
						console
								.log(searchComponentsFromFullPathNameExpression);
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
						if (val.kpi.minValue != null && data < val.kpi.minValue) {
							return "min value is " + val.kpi.minValue;
						}
						if (val.kpi.maxValue != null && data > val.kpi.maxValue) {
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
					// Charts
					$scope.refreshMeasurementsKiviaChart = function() {
						var ctx = $("#measurementsKiviaChart").get(0)
								.getContext("2d");
						// Create data from manualMeasurements
						var data = {};

						// Labels are kpi names
						var kpis = $scope
								.manualMeasurementsOfSnapshot($scope.currentSnapshot);
						var labels = new Array();
						for (i = 0; i < kpis.length; i++) {
							labels.push(kpis[i].name);
						}
						// datasets are measurements (one per user)
						var datasets = new Array();
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
						for (j = 0; j < $scope.currentSnapshot.manualMeasurements.length; j++) {
							var measurement = $scope.currentSnapshot.manualMeasurements[j];
							var dataset = {};
							if (j < colors.length) {
								colorStr = colors[j];
							} else {
								colorStr = "rgba(230,230,230";
							}
							dataset.label = measurement.who.name;
							dataset.strokeColor = colorStr + ",1)";
							dataset.pointColor = colorStr + ",1)";
							dataset.fillColor = colorStr + ",0.2)";
							dataset.pointHighlightFill = "#fff";
							dataset.pointHighlightStroke = colorStr + ",1)";
							dataset.data = new Array();
							for (k = 0; k < measurement.values.length; k++) {
								if (measurement.values[k].value == null) {
									dataset.data.push(0);
								} else {
									dataset.data
											.push(measurement.values[k].value);
								}
							}
							datasets.push(dataset);
						}
						var data = {
							labels : labels,
							datasets : datasets
						};
						var options = {
							// Boolean - Whether to show lines for each scale
							// point
							scaleShowLine : true,

							// Boolean - Whether we show the angle lines out of
							// the radar
							angleShowLineOut : true,

							// Boolean - Whether to show labels on the scale
							scaleShowLabels : false,

							multiTooltipTemplate : "<%= datasetLabel %> - <%= value %>",

							// Boolean - Whether the scale should begin at zero
							scaleBeginAtZero : true,

							// String - Colour of the angle line
							angleLineColor : "rgba(0,0,0,.1)",

							// Number - Pixel width of the angle line
							angleLineWidth : 1,

							// String - Point label font declaration
							pointLabelFontFamily : "'Arial'",

							// String - Point label font weight
							pointLabelFontStyle : "normal",

							// Number - Point label font size in pixels
							pointLabelFontSize : 12,

							// String - Point label font colour
							pointLabelFontColor : "#666",

							// Boolean - Whether to show a dot for each point
							pointDot : true,

							// Number - Radius of each point dot in pixels
							pointDotRadius : 3,

							// Number - Pixel width of point dot stroke
							pointDotStrokeWidth : 1,

							// Number - amount extra to add to the radius to
							// cater for hit detection outside the drawn point
							pointHitDetectionRadius : 20,

							// Boolean - Whether to show a stroke for datasets
							datasetStroke : true,

							// Number - Pixel width of dataset stroke
							datasetStrokeWidth : 2,

							// Boolean - Whether to fill the dataset with a
							// colour
							datasetFill : true,

							// String - A legend template
							legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"

						}
						var myLineChart = new Chart(ctx).Radar(data, options);
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
												$scope.dashboards[$scope.selectedApplication.id].graphNames = [];
												for (k = 0; k < dashboard.kpiHistories.length; k++) {
													var names = {
														id : $scope.selectedApplication.id + "_hist_" + k,
														name : dashboard.kpiHistories[k].kpiName
													}
													$scope.dashboards[$scope.selectedApplication.id].graphNames
															.push(names);
												}
												$timeout($scope.refreshKpiDashboard,1300);
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
					// Dashboard graphs creation after loading dashobard data
					$scope.refreshKpiDashboard = function() {
						var dashboard = $scope.dashboards[$scope.selectedApplication.id];
						for (i = 0; i < dashboard.num_graphs; i++) {
							var history = dashboard.dashboard.kpiHistories[i];
							var ctx = document.getElementById(
									dashboard.graphNames[i].id)
									.getContext("2d");
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

								// String - Colour of the grid lines
								scaleGridLineColor : "rgba(0,0,0,.05)",

								// Number - Width of the grid lines
								scaleGridLineWidth : 1,

								// Boolean - Whether to show horizontal lines
								// (except X axis)
								scaleShowHorizontalLines : true,

								// Boolean - Whether to show vertical lines
								// (except Y axis)
								scaleShowVerticalLines : true,

								// Boolean - Whether the line is curved between
								// points
								bezierCurve : true,

								// Number - Tension of the bezier curve between
								// points
								bezierCurveTension : 0.4,

								// Boolean - Whether to show a dot for each
								// point
								pointDot : true,

								// Number - Radius of each point dot in pixels
								pointDotRadius : 4,

								// Number - Pixel width of point dot stroke
								pointDotStrokeWidth : 1,

								// Number - amount extra to add to the radius to
								// cater for hit detection outside the drawn
								// point
								pointHitDetectionRadius : 20,

								// Boolean - Whether to show a stroke for
								// datasets
								datasetStroke : true,

								// Number - Pixel width of dataset stroke
								datasetStrokeWidth : 2,

								// Boolean - Whether to fill the dataset with a
								// colour
								datasetFill : true,

							};
							// Set Line chart with data & options
							var chart = new Chart(ctx).Line(data, options);
						}
					}
					// Init
					$scope.loadApps();

				});
