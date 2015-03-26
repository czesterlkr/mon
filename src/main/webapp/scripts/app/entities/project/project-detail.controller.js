'use strict';

angular.module('monApp')
    .controller('ProjectDetailController', function ($scope, $stateParams, Project) {
        $scope.project = {};
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
              $scope.project = result;
            });
        };
        $scope.load($stateParams.id);
    });
