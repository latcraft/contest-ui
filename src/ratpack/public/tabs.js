"use strict";

angular
  .module('latcraftContestApp')
  .controller('PageController', function($scope, $location) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
  }
);
