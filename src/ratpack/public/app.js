"use strict";

angular
  .module('latcraftContestApp', ['ngCookies', 'ngRoute'])
  .config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/rules'});
}]);
