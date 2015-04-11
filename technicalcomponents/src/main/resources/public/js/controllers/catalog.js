var techMain = angular.module('techMain', ['ui.tree','angularBootstrapNavTree']);

techMain.controller('techMainController', function($scope, $http) {
	
	function Node(_label) {
		this.label = _label;
	}	
	
	$scope.component_catalog = [];
	$scope.new_component = new Object();
	$scope.new_component.vendor = new Object();
	$scope.new_component.local = new Object();
	
	$scope.on_select_category = function(category) {
		$scope.selectedCategoryId = category.category_id;
		$scope.hasSelectedCategory = true;
		$http.get("/service/componentsOfCategory/"+category.category_id).success(function(response) {
			$scope.allComponents = response;		
		});
	}
	$scope.hasSelectedCategory = false;
	$scope.createNewComponent = function() {
		console.log("create : "+$scope.new_component.vendor.name + " - " + $scope.new_component.local.name)
	}
	
	$http.get("/service/catalogs").success(function(response) {
		$scope.catalogs = response;
		var allarray = new Array();
		for (i=0;i<$scope.catalogs[0].categories.length;i++) {
			var p = new Object();
			var cat = $scope.catalogs[0].categories[i];
			p.label = cat.name;
			p.onSelect = $scope.on_select_category;
			p.category_id = cat.id;
			allarray.push(p);
			if (cat.categories!=null) {
				p.children = new Array();
				for (j=0;j<cat.categories.length;j++) {
					var subCat = new Object();
					subCat.label = cat.categories[j].name;
					subCat.onSelect = $scope.on_select_category;
					subCat.category_id = cat.categories[j].id;
					p.children.push(subCat);
				}
			}
			
		}
		$scope.component_catalog  = allarray;	
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
});

