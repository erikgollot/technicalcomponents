var storageControllers = angular.module('storageControllers', []);

storageControllers.controller('storageController', function($scope, $http,
		$upload) {
	$scope.loadStorages = function() {
		$http.get("/service/storages").success(
				function(response) {
					$scope.storages = response;
					for (i = 0; i < $scope.storages.length; i++) {
						$scope.storages[i].availableDiskSpaceInGo = Math
								.round($scope.storages[i].availableDiskSpace
										/ (1024 * 1024 * 1024));
					}
				});
	}

	$scope.loadStorages();

	
	$scope.openEditStorageDialog = function(storage) {	
		$scope.editedStorage = storage;
		$('#storageEditDialog').modal('show');
	}
	
	$scope.saveEditedStorage = function(storage) {		
		$('#storageEditDialog').modal('hide');
	}
	
	$scope.createNewStorage= function(newStorage) {
		var lastIndex = 0;
		if ($scope.storages !=null && $scope.storages.length > 0) {
			lastIndex = $scope.storages.length;
		}
		lastIndex++;
		newStorage.orderInSet = lastIndex;
		if ($scope.storages == null) {
			$scope.storages = new Array();
		}
		// Now try to store the new storage
		$http.post(
				"/service/createNewStore/",newStorage)
				.success(function(response) {
					console.log("storage created");
					BootstrapDialog.show({
						title : 'Information',
						message : 'Storage created for ' + newStorage.rootDir
					});
					$scope.storages.push(response);
				})
				.error(function(data, status, headers, config) {
					BootstrapDialog.show({
						title : 'Error',
						message : 'Cannot create storage for ' + newStorage.rootDir+'\n\n'+'Reason : '+data.message
					});
				});
	}
	
	$scope.deleteStorage = function(storage) {
		$http.post(
				"/service/deleteStorage/"+storage.id)
				.success(function(response) {					
					BootstrapDialog.show({
						title : 'Information',
						message : 'Storage deleted ' + storage.rootDir
					});
					$scope.storages.pop(storage);
				})
				.error(function(data, status, headers, config) {
					BootstrapDialog.show({
						title : 'Error',
						message : 'Cannot delete storage '+storage.rootDir
					});
				});
	}
});