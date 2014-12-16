"use strict";

angular
  .module('latcraftContestApp')
  .config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/queue', {
      templateUrl: 'views/queue/queue.html',
      controller: 'QueueController'
    });

  }])
  .controller('QueueController', function($scope, $log, $http, $cookies, $interval, $route, $routeParams) {

    $scope.queueCtrl = this;

    this.userName = $cookies.userName === undefined ? null : $cookies.userName;
    this.solutionHostName = $cookies.solutionHostName === undefined ? null : $cookies.solutionHostName;
    this.requests = [];
    this.currentRequest = {};

    this.loadQueue = function() {
      $http.get('/api/v1/queue/all').success(function(data, status, headers, config) {
        $log.debug('Received queue data:', data)
        if (data.response == 'OK') {
          $scope.queueCtrl.requests = data.requests;
          $log.debug('Data is loaded!');
        }
      });
      // TODO: implement loading through SSE/WebSocket
    };

    this.loadQueue();

    this.submitRequest = function() {
      this.currentRequest = {};
      this.currentRequest.userName = this.userName;
      this.currentRequest.solutionHostName = this.solutionHostName;
      $http.post('/api/v1/queue/submit', {
        userName: this.currentRequest.userName,
        solutionHostName: this.currentRequest.solutionHostName
      }).success(function(data, status, headers, config) {
        $log.debug('Received submit response:', data)
        if (data.response == 'OK') {
          $scope.queueCtrl.requests.push($scope.queueCtrl.currentRequest);
          $cookies.userName = $scope.queueCtrl.currentRequest.userName;
          $cookies.solutionHostName = $scope.queueCtrl.currentRequest.solutionHostName;
          $scope.queueCtrl.currentRequest = {};
        } else {
          // TODO: display error message
        }
      });
    };

    $interval(function() {
      $scope.queueCtrl.loadQueue();
    }, 5000); // TODO: stop this when view changes

  });

