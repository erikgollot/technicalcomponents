var applicationsControllers = angular.module('applicationsControllers', []);

applicationsControllers.controller('applicationsController', function($scope,
		$http) {
	$scope.loadApps = function() {
		$http.get("/service/applications").success(function(response) {
			$scope.applications = response;
		});
	}
	$scope.searchComponents = null;
	$scope.searchComponentsFromFullPathName = function() {
		console.log($scope.searchComponentsFromFullPathNameExpression);
		$http({
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
		if ($scope.fullSelection != null && $scope.fullSelection.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Add selected components, without duplicate !!, into builtOnComponents.
	// This is the save that will really set the builtOn list of the application
	$scope.addToBuiltOn = function(application) {
		if ($scope.fullSelection != null && $scope.fullSelection.length > 0) {
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
		if ($scope.fullSelection != null && $scope.fullSelection.length > 0) {
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
		$http.post("/service/application/setTechnicalComponents",application).success(function(response) {
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

	// Init
	$scope.loadApps();

});
