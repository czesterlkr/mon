'use strict';

angular.module('monApp')
    .controller('HolidayDetailController', function ($scope, $stateParams, Holiday, KindOfHoliday, StatusOfHoliday, User) {
        $scope.holiday = {};
        $scope.load = function (id) {
            Holiday.get({id: id}, function(result) {
              $scope.holiday = result;
            });
        };
        $scope.load($stateParams.id);
    });
