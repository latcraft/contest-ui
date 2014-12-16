"use strict";

angular
  .module('latcraftContestApp')
  .config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/rules', {
      templateUrl: 'views/rules/rules.html',
      controller: 'ResultController'
    });

  }])
  .controller('RulesController', function($scope, $log, $http, $cookies, $interval, $route, $routeParams) {


  });

