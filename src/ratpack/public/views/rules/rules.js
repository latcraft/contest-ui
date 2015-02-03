"use strict";

angular
  .module('latcraftContestApp')
  .config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/rules', {
      templateUrl: 'views/rules/cloudopoly3.html',
      controller: 'ResultController'
    }).when('/cloudopoly1', {
      templateUrl: 'views/rules/cloudopoly1.html',
      controller: 'ResultController'
    }).when('/cloudopoly2', {
      templateUrl: 'views/rules/cloudopoly2.html',
      controller: 'ResultController'
    }).when('/cloudopoly3', {
      templateUrl: 'views/rules/cloudopoly3.html',
      controller: 'ResultController'
    });

  }])
  .controller('RulesController', function($scope, $log, $http, $cookies, $interval, $route, $routeParams) {


  });

