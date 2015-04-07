var techMain = angular.module('techMain', []);
  

techMain.controller('techMainController',function ($scope, $http) {
$http.get("/service/technicalComponents").success(function(response) {	    	
	$scope.items = response;		 	
	})
});