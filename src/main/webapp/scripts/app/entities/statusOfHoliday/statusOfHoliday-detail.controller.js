'use strict';

angular.module('monApp')
    .controller('StatusOfHolidayDetailController', function ($scope, $stateParams, StatusOfHoliday) {
        $scope.statusOfHoliday = {};
        $scope.load = function (id) {
            StatusOfHoliday.get({id: id}, function(result) {
              $scope.statusOfHoliday = result;
            });
        };
        $scope.load($stateParams.id);
    });
