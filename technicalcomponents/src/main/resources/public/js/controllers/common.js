var ismCommons =angular.module('ismCommons',[]);

ismCommons.factory('basePath',function($window) {
	return $window.ism.contextPath;
});

ismCommons.factory('mkDate',function() {
	return function(timestamp) {
		return new Date(timestamp);
	};
});

