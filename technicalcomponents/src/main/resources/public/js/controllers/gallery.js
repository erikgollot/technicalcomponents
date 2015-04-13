var galleryControllers = angular.module('galleryControllers', [
		'angularFileUpload', 'angularModalService' ]);

galleryControllers.controller('galleryController', function($scope, $http,
		$upload) {

	$scope.isGalleryAvailable = false;
	$http.get("/componentGallery/canStoreImage").success(function(response) {
		$scope.isGalleryAvailable = response;		
	});
	
	$scope.$watch('files', function() {
		$scope.upload($scope.files);
	});

	$scope.hasImages = false;

	$scope.refreshGallery = function() {
		$http.get("/componentGallery/all").success(function(response) {
			$scope.galleryImages = response;
			if ($scope.galleryImages.length > 0)
				$scope.hasImages = true;
		});
	}

	$scope.refreshGallery();

	$scope.removeImage = function(id) {

		BootstrapDialog.show({
			size : BootstrapDialog.SIZE_NORMAL,
			title : 'Confirm',
			message : 'Please confirm image suppress',
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
							$http.post("/componentGallery/removeImage/" + id)
									.success(function(response) {
										$scope.refreshGallery();
									});
							dialog.close();
						}
					} ]
		});
	}

	$scope.upload = function(files) {
		console.log("called, files = " + files);
		if (files && files.length) {
			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				console.log("file name " + file.name);
				$upload.upload({
					url : '/componentGallery/storeImage',
					file : file
				}).progress(
						function(evt) {
							var progressPercentage = parseInt(100.0
									* evt.loaded / evt.total);
							console.log('progress: ' + progressPercentage
									+ '% ' + evt.config.file.name);
						}).success(
						function(data, status, headers, config) {
							console.log('file ' + config.file.name
									+ 'uploaded. Response: ' + data);
							$scope.refreshGallery();
						});
			}
		}
	}
});

galleryControllers.service('uploadImageInGallery()', [ '$http',
		function($http) {
			this.uploadFileToUrl = function(uploadUrl, file, name, date) {
				var fd = new FormData();
				fd.append('file', file);
				$http.post(uploadUrl, fd, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function() {
					$scope.uploadStatus = response;
				}).error(function() {
					$scope.uploadStatus = "bouuu";
				});
			}
		} ]);