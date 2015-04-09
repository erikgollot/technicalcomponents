var techMain = angular.module('techMain', ['ui.tree']);

techMain.controller('techMainController', function($scope, $http) {
	$http.get("/service/technicalComponents").success(function(response) {
		$scope.allComponents = response;
	});

	$http.get("/service/catalogs").success(function(response) {
		$scope.catalogs = response;
	});

	$scope.selectedCatalog = [];

	$scope.saved = false;
	$scope.createCatalog = function(catalog) {
		$http.post("/service/create", catalog).success(
				function(data, status, headers, config) {
					$scope.saved = true;
					$scope.reset();
				}).error(function(data, status, headers, config) {
			$scope.saved = false;			
		});
	};

	$scope.selectCatalog = function(choice) {
		$scope.selectedCatalog = choice;		
	}

	$scope.catalogTreeOptions = {
		accept : function(sourceNodeScope, destNodesScope, destIndex) {
			console.log ("accept"); 
			return true;
		},
		beforeDrap : function(sourceNodeScope) {
			console.log ("before drag"); 
			return true;
		},
		dropped : function(e) {
			console.log("dropped");
			console.log("move source "+e.source.nodeScope.$modelValue.id);
			console.log("to destination "+e.dest.nodesScope.$modelValue.id);
			//$http.post('/service/moveCategory/' +e.dest.nodesScope.$modelValue.id + '/'+ e.source.nodeScope.$modelValue.id).success(
				//	function(data, status, headers, config) {
					//	
					//}).error(function(data, status, headers, config) {
							
		//	});  
		},
		
	};	
});

