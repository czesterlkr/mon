'use strict';

angular.module('monApp')
    .controller('KindOfHolidayDetailController', function ($scope, $stateParams, KindOfHoliday) {
        $scope.kindOfHoliday = {};
        $scope.load = function (id) {
            KindOfHoliday.get({id: id}, function(result) {
              $scope.kindOfHoliday = result;
            });
        };
        $scope.load($stateParams.id);
    });
