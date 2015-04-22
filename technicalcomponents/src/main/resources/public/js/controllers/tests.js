var testsControllers = angular.module('testsControllers' , ["xeditable","ui.bootstrap"]);

testsControllers.run(function(editableOptions) {
	editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2',
	// 'default'
});

testsControllers
		.controller(
				'testsController',
				function($scope) {

					$scope.user = {
						    dob: new Date(1984, 4, 15)
						  };					
					
					
					
}); // End controller

