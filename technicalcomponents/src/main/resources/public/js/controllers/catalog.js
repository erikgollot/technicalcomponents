var techMain = angular.module('techMain', [ 'ui.tree', 'ui.bootstrap',
		'angularBootstrapNavTree' ]);

techMain
		.controller(
				'techMainController',
				function($scope, $http) {
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();
					function Node(_label) {
						this.label = _label;
					}

					$scope.component_catalog = [];
					$scope.selectedCatalog = [];
					$scope.new_component = new Object();
					$scope.new_component.vendorInformations = new Object();
					$scope.new_component.localInformations = new Object();
					$scope.image = new Object();
					$scope.selectedCategoryId = -1;
					$scope.hasSelectedCategory = false;

					// Hard retrieve of first catalog...only one catalog for the
					// moment
					$http
							.get("/service/catalogs")
							.success(
									function(response) {
										$scope.catalogs = response;
										var allarray = new Array();
										for (i = 0; i < $scope.catalogs[0].categories.length; i++) {
											var p = new Object();
											var cat = $scope.catalogs[0].categories[i];
											p.label = cat.name;
											p.onSelect = $scope.on_select_category;
											p.category_id = cat.id;
											allarray.push(p);
											if (cat.categories != null) {
												p.children = new Array();
												for (j = 0; j < cat.categories.length; j++) {
													var subCat = new Object();
													subCat.label = cat.categories[j].name;
													subCat.onSelect = $scope.on_select_category;
													subCat.category_id = cat.categories[j].id;
													p.children.push(subCat);
												}
											}

										}
										$scope.component_catalog = allarray;
									});
					$scope.hasImages = false;

					$scope.refreshGallery = function() {
						$http.get("/componentGallery/all").success(
								function(response) {
									$scope.galleryImages = response;
									if ($scope.galleryImages.length > 0)
										$scope.hasImages = true;
								});
					}

					$scope.refreshGallery();

					$scope.convertDate = function(aDate) {
						var months = [ "Jan", "Feb", "Mar", "Apr", "May",
								"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
						var formatedDate = new Date(aDate);
						var formatedDateAsString = "";
						formatedDateAsString = formatedDateAsString + formatedDate.getDate() + "-"
								+ (formatedDate.getMonth()+1)+ "-"
								+ formatedDate.getFullYear();
						return formatedDateAsString;
					}

					// Handlers

					$scope.on_select_category = function(category) {
						$scope.selectedCategoryId = category.category_id;
						console.log("selected parent = "
								+ $scope.selectedCategoryId);
						$scope.hasSelectedCategory = true;
						$http.get(
								"/service/componentsOfCategory/"
										+ category.category_id).success(
								function(response) {
									$scope.allComponents = response;
								});
					}

					// Create new component on dialog save button click
					$scope.createNewComponent = function(newComp) {
						if (newComp.image == null) {
							newComp.image = new Object();
						}
						newComp.image.id = $scope.image.id;
						// Need to transform date in the right format. Even if
						// display format is
						// ok in datepicker, the displayed format is not set to
						// the field that
						// hold the date
						if (newComp.localInformations.availableDate != null) {
							newComp.localInformations.availableDate = $scope.convertDate(newComp.localInformations.availableDate);
						}
						if (newComp.localInformations.deprecatedDate != null) {
							newComp.localInformations.deprecatedDate = $scope.convertDate(newComp.localInformations.deprecatedDate);
						}
						if (newComp.localInformations.unavailableDate != null) {
							newComp.localInformations.unavailableDate = $scope.convertDate(newComp.localInformations.unavailableDate);
						}
						if (newComp.vendorInformations.availableDate != null) {
							newComp.vendorInformations.availableDate = $scope.convertDate(newComp.vendorInformations.availableDate);
						}
						if (newComp.vendorInformations.deprecatedDate != null) {
							newComp.vendorInformations.deprecatedDate = $scope.convertDate(newComp.vendorInformations.deprecatedDate);
						}
						if (newComp.vendorInformations.unavailableDate != null) {
							newComp.vendorInformations.unavailableDate = $scope.convertDate(newComp.vendorInformations.unavailableDate);
						}
						$http.post(
								"/service/createComponent/"
										+ $scope.selectedCategoryId, newComp)
								.success(function(response) {
									console.log("component created...youpi");
								})
								.error(function(data, status, headers, config) {
									BootstrapDialog.show({
										title : 'Error',
										message : 'Cannot create component'
									});
								});
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
						console.log("openSelectLogoDialog")
						$('#selectLogoDialog').modal('show');
					}

					$scope.selectedLogo = function(id) {
						$scope.image.id = id;
					}
					$scope.forgetSelectedLog = function() {
						$scope.image.id = -1;
					}

				});
