var uploadControllers = angular.module('uploadControllers', []);

uploadControllers.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

uploadControllers.service('fileUpload', [ '$http', function($http) {
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

uploadControllers.controller('uploadCtrl', [ '$scope', 'fileUpload',
		function($scope, fileUpload) {
			$scope.uploadFile = function() {
				var file = $scope.myFile;
				console.log('file is ' + JSON.stringify(file));
				var uploadUrl = "/service/storeFile";
				fileUpload.uploadFileToUrl(uploadUrl, file);
			};
		} ]);
