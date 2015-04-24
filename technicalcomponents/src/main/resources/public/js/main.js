/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app = angular.module('technicalComponents', [ 'ngRoute', 'techMain',
		 'uploadControllers', 'galleryControllers',
		'storageControllers', 'kpisControllers', 'applicationsControllers',
		'testsControllers' ]);

/**
 * Configure the Routes
 */
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when("/catalog", {
		templateUrl : "partials/catalog.html",
		controller : "techMainController"
	}).when("/gallery", {
		templateUrl : "partials/gallery.html",
		controller : "galleryController"
	}).when("/storage", {
		templateUrl : "partials/storage.html",
		controller : "storageController"
	}).when("/kpis", {
		templateUrl : "partials/kpis.html",
		controller : "kpisController"
	}).when("/applications", {
		templateUrl : "partials/applications.html",
		controller : "applicationsController"
	}).when("/tests", {
		templateUrl : "partials/tests.html",
		controller : "testsController"
	}).otherwise("/", {
		redirectTo : '/',
		controller : "nopCtrl"
	});
} ]);

/**
 * Controller for index page
 */
app.controller('nopCtrl', function($scope) {	
});

app.run(function($rootScope) {
	$rootScope.currentUser = {
		id : 1,
		name : "Erik"
	};
});
