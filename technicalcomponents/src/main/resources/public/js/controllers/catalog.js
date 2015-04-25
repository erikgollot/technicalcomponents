var techMain = angular.module('techMain', [ 'ui.tree', 'ui.bootstrap',
		'angularBootstrapNavTree' ,'ngAnimate']);

techMain
		.controller(
				'techMainController',
				function($scope, $http, $route) {
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();
					function Node(_label) {
						this.label = _label;
					}

					$scope.component_catalog = [];
					$scope.selectedCatalog = [];
					$scope.image = new Object();
					$scope.selectedCategoryNode = null;
					$scope.hasSelectedCategory = false;
					$scope.hasSelectedLeafCategory = false;

					// Hard retrieve of first catalog...only one catalog for the
					// moment
					$scope.loadCatalog = function() {
						$http
								.get("/service/catalogs")
								.success(
										function(response) {
											$scope.catalogs = response;
											var allarray = new Array();
											for (i = 0; i < $scope.catalogs[0].categories.length; i++) {
												var cat = $scope.catalogs[0].categories[i];
												var nodeTree = $scope
														.newNodeTree(cat);
												$scope.addCategoriesToTree(
														nodeTree, cat);
												allarray.push(nodeTree);
											}
											$scope.component_catalog = allarray;
										});
					}
					$scope.addCategoriesToTree = function(treeNode, cat) {
						if (cat.categories != null && cat.categories.length > 0) {
							treeNode.children = new Array();
							treeNode.isLeafCategory = false;
							for (j = 0; j < cat.categories.length; j++) {
								var modelSubCat = cat.categories[j];
								var subCat = $scope.newNodeTree(modelSubCat);
								treeNode.children.push(subCat);
								$scope.addCategoriesToTree(subCat, modelSubCat)
							}
						}
					}
					$scope.newNodeTree = function(cat) {
						var treeNode = new Object();
						treeNode.label = cat.name;
						treeNode.onSelect = $scope.on_select_category;
						treeNode.category_id = cat.id;
						treeNode.isLeafCategory = true;
						return treeNode;
					}
					$scope.hasImages = false;

					$scope.refreshGallery = function() {
						$http.get("/componentGallery/all").success(
								function(response) {
									$scope.galleryImages = response;
									if ($scope.galleryImages.length > 0)
										$scope.hasImages = true;
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

					// Handlers
					$scope.loadComponentsOfSelectedCategory = function(
							category_id) {
						$http
								.get(
										"/service/componentsOfCategory/"
												+ category_id)
								.success(
										function(response) {
											$scope.allComponentOfSelectedCategory = response;
										});
					}

					$scope.on_select_category = function(category) {
						if (category.isLeafCategory) {
							$scope.hasSelectedLeafCategory = true;
						} else {
							$scope.hasSelectedLeafCategory = false;
						}
						$scope.hasSelectedCategory = true;
						$scope.selectedCategoryNode = category;
						$scope
								.loadComponentsOfSelectedCategory(category.category_id);
					}
					// Change category name. Return string error if necessary to
					// avoid change in ui
					$scope.changeCategoryName = function(newName) {
						return $http(
								{
									url : "/service/changeCategoryName/"
											+ $scope.selectedCategoryNode.category_id,
									method : "POST",
									params : {
										newName : newName
									}
								})
								.success(function(response) {
								})
								.error(
										function(data, status, headers, config) {
											BootstrapDialog
													.show({
														title : 'Error',
														message : 'Cannot update category\nReason is : '
																+ data.message
													});
										});

					}

					// Create new component on dialog save button click
					$scope.createOrUpdateComponent = function(theComp) {
						var newComp = angular.copy(theComp);

						if ($scope.image.id != null) {
							newComp.image = angular.copy($scope.image);
							// newComp.image.id = $scope.image.id;

						}
						// Need to transform date in the right format. Even if
						// display format is
						// ok in datepicker, the displayed format is not set to
						// the field that
						// hold the date

						if (newComp.localInformations.availableDate != null) {
							newComp.localInformations.availableDate = $scope
									.convertDate(newComp.localInformations.availableDate);
						}
						if (newComp.localInformations.deprecatedDate != null) {
							newComp.localInformations.deprecatedDate = $scope
									.convertDate(newComp.localInformations.deprecatedDate);
						}
						if (newComp.localInformations.unavailableDate != null) {
							newComp.localInformations.unavailableDate = $scope
									.convertDate(newComp.localInformations.unavailableDate);
						}
						if (newComp.vendorInformations.availableDate != null) {
							newComp.vendorInformations.availableDate = $scope
									.convertDate(newComp.vendorInformations.availableDate);
						}
						if (newComp.vendorInformations.deprecatedDate != null) {
							newComp.vendorInformations.deprecatedDate = $scope
									.convertDate(newComp.vendorInformations.deprecatedDate);
						}
						if (newComp.vendorInformations.unavailableDate != null) {
							newComp.vendorInformations.unavailableDate = $scope
									.convertDate(newComp.vendorInformations.unavailableDate);
						}
						// It's a new one
						if (newComp.id == null) {
							$http
									.post(
											"/service/createComponent/"
													+ $scope.selectedCategoryNode.category_id,
											newComp)
									.success(
											function(response) {
												if (newComp.image != null) {
													$http
															.post(
																	"/service/setImageComponent/"
																			+ response.id
																			+ "/"
																			+ newComp.image.id)
															.success(
																	function(
																			response2) {
																		$scope
																				.loadComponentsOfSelectedCategory($scope.selectedCategoryNode.category_id);
																	})
															.error(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		BootstrapDialog
																				.show({
																					title : 'Error',
																					message : data.message
																				});
																	});

												} else {
													$scope
															.loadComponentsOfSelectedCategory($scope.selectedCategoryNode.category_id);
												}
											}).error(
											function(data, status, headers,
													config) {
												BootstrapDialog.show({
													title : 'Error',
													message : data.message
												});
											});
						} else {
							// Update existing one
							$http
									.post("/service/updateComponent", newComp)
									.success(
											function(response) {
												if (newComp.image != null) {
													$http
															.post(
																	"/service/setImageComponent/"
																			+ newComp.id
																			+ "/"
																			+ newComp.image.id)
															.success(
																	function(
																			response2) {
																		$scope
																				.loadComponentsOfSelectedCategory($scope.selectedCategoryNode.category_id);
																	})
															.error(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		BootstrapDialog
																				.show({
																					title : 'Error',
																					message : data.message
																				});
																	});

												} else {
													$scope
															.loadComponentsOfSelectedCategory($scope.selectedCategoryNode.category_id);
												}
											}).error(
											function(data, status, headers,
													config) {
												BootstrapDialog.show({
													title : 'Error',
													message : data.message
												});
											});

						}
						$scope.component_under_edition = null;
					}

					$scope.saved = false;
					$scope.createCatalog = function(catalog) {
						$http.post("/service/create", catalog).success(
								function(data, status, headers, config) {
									$scope.saved = true;
									$scope.reset();
								}).error(
								function(data, status, headers, config) {
									$scope.saved = false;
								});
					};

					$scope.selectCatalog = function(choice) {
						$scope.selectedCatalog = choice;
					}

					// Vendor date handlers
					$scope.vendorFieldsOption = new Object();
					$scope.vendorFieldsOption.availabilityDateOpened = false;

					$scope.openVendorAvailabiltyDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.vendorFieldsOption.availabilityDateOpened = true;
					};

					$scope.vendorFieldsOption.deprecatedDateOpened = false;

					$scope.openVendorDeprecatedDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.vendorFieldsOption.deprecatedDateOpened = true;
					};

					$scope.vendorFieldsOption.unavailabilityDateOpened = false;

					$scope.openVendorUnavailabilityDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.vendorFieldsOption.unavailabilityDateOpened = true;
					};

					// Local date handlers
					$scope.localFieldsOption = new Object();
					$scope.localFieldsOption.availabilityDateOpened = false;

					$scope.openLocalAvailabiltyDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.localFieldsOption.availabilityDateOpened = true;
					};

					$scope.localFieldsOption.deprecatedDateOpened = false;

					$scope.openLocalDeprecatedDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.localFieldsOption.deprecatedDateOpened = true;
					};

					$scope.localFieldsOption.unavailabilityDateOpened = false;

					$scope.openLocalUnavailabilityDate = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.localFieldsOption.unavailabilityDateOpened = true;
					};

					// Logo dialog handlers
					$scope.openSelectLogoDialog = function() {
						$('#selectLogoDialog').modal('show');
					}

					$scope.selectedLogo = function(id) {
						$scope.image.id = id;
					}
					$scope.forgetSelectedLogo = function() {
						$scope.image.id = -1;
					}

					$scope.searchWarningComponents = function() {
						$http
								.get(
										"/service/searchTechnicalComponentsWithStatus/WARNING")
								.success(function(response) {
									$scope.warningComponents = response;
								});

					}
					$scope.searchHotComponents = function() {
						$http
								.get(
										"/service/searchTechnicalComponentsWithStatus/HOT")
								.success(function(response) {
									$scope.hotComponents = response;
								});

					}
					$scope.searchAvailableComponents = function() {
						$http
								.get(
										"/service/searchTechnicalComponentsWithStatus/AVAILABLE")
								.success(function(response) {
									$scope.availableComponents = response;
								});

					}

					$scope.setEditedComponent = function(component) {
						$scope.component_under_edition = angular
								.copy(component);
					}

					$scope.empty_component_under_edition = {};
					$scope.resetEditedComponent = function(component) {
						$scope.component_under_edition = angular
								.copy($scope.empty_component_under_edition);

					}

					$scope.deleteComponent = function(component) {
						BootstrapDialog
								.show({
									size : BootstrapDialog.SIZE_NORMAL,
									title : 'Confirm',
									message : 'Please confirm the suppresion of the [<b>'
											+ component.localInformations.name
											+ '</b>] component',
									buttons : [
											{
												label : 'Cancel',
												action : function(dialog) {
													dialog.close();
												}
											},
											{
												label : 'Delete',
												action : function(dialog) {
													$http
															.post(
																	"/service/deleteComponent/"
																			+ component.id)
															.success(
																	function(
																			response) {
																		$scope
																				.loadComponentsOfSelectedCategory($scope.selectedCategoryNode.category_id);
																	})
															.error(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		BootstrapDialog
																				.show({
																					title : 'Error',
																					message : data.message
																				});
																	});
													dialog.close();
												}
											} ]
								});

					}

					$scope.createOrUpdateCategory = function(catName) {
						// selectedCategoryNode = current selected node of tree
						// catName has to be created on the server and then
						// added to selectedCategoryNode
						$http
								.post(
										"/service/addCategory/"
												+ $scope.selectedCategoryNode.category_id,
										catName)
								.success(
										function(response) {
											var newCat = response;
											var node = $scope
													.newNodeTree(newCat);
											$scope.selectedCategoryNode.isLeafCategory = false;
											$scope.selectedCategoryNode.children
													.push(node);
										})
								.error(function(data, status, headers, config) {
									BootstrapDialog.show({
										title : 'Error',
										message : data.message
									});
								});
					}
					$scope.searchParent = function(childNode) {
						for (var i = 0; i < $scope.component_catalog.length; i++) {
							var aRoot = $scope.component_catalog[i];
							if (aRoot.children != null
									&& aRoot.children.length > 0) {
								var found = $scope.searchInChildren(aRoot,
										childNode);
								if (found != null) {
									return found;
								}
							}
						}
						// should not
						return null;
					}
					$scope.searchInChildren = function(aRoot, childNode) {
						for (var i = 0; i < aRoot.children.length; i++) {
							var aPossibleMe = aRoot.children[i];
							if (aPossibleMe == childNode) {
								return aRoot;
							}
							var found = $scope.searchInChildren(aPossibleMe,
									childNode);
							if (found != null) {
								return found;
							}
						}
						return null;
					}

					$scope.deleteCategory = function() {
						BootstrapDialog
								.show({
									size : BootstrapDialog.SIZE_NORMAL,
									title : 'Confirm',
									message : 'Please confirm suppresion of the [<b>'
											+ $scope.selectedCategoryNode.label
											+ '</b>] category\n It will delete also all contained components',
									buttons : [
											{
												label : 'Close',
												action : function(dialog) {
													dialog.close();
												}
											},
											{
												label : 'Delete',
												action : function(dialog) {
													$http
															.post(
																	"/service/deleteCategory/"
																			+ $scope.selectedCategoryNode.category_id)
															.success(
																	function(
																			response) {
																		var parentNode = $scope
																				.searchParent($scope.selectedCategoryNode);
																		if (parentNode != null) {
																			parentNode.children
																					.pop($scope.selectedCategoryNode);
																			if (parent.children.length > 0) {
																				parent.isLeafCategory = true;
																			}
																		} else {
																			// Node
																			// was
																			// at
																			// top
																			// level
																			$scope.component_catalog
																					.pop($scope.selectedCategoryNode);
																		}
																		$scope.selectedCategoryNode = null;
																		$scope.allComponentOfSelectedCategory = null;
																	})
															.error(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		BootstrapDialog
																				.show({
																					title : 'Error',
																					message : data.message
																				});
																	});
													dialog.close();
												}
											} ]
								});
					}

					// Initial loading
					// -----------------------
					$scope.loadCatalog();
					$scope.refreshGallery();
					$scope.resetEditedComponent();
				});
