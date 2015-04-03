
techApp.controller('techMainCtrl',function($scope,$http) {
	 $http.get("/service/technicalComponents")
	    .success(function(response) {
	    	
	    	$scope.items = response;
	 });	
});