"use strict";

angular
  .module('latcraftContestApp', ['ngCookies', 'ngRoute', 'latcraftFilters'])
  .config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/rules'});
}]);
