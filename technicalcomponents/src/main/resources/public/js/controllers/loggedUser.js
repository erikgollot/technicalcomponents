var loggedUsers = angular.module('loggedUsers', []);

loggedUsers.factory('loggedUser', function() {
	var user = {};
	var loggedUserService = {};

	loggedUserService.setLoggedUser = function(u) {
		$rootScope.currentUser = u;
	};
	loggedUserService.getLoggedUser = function() {
		return $rootScope.currentUser;
	};

	return loggedUserService;
});