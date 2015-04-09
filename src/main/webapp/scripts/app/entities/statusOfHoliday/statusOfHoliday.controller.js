'use strict';

angular.module('monApp')
    .controller('StatusOfHolidayController', function ($scope, StatusOfHoliday) {
        $scope.statusOfHolidays = [];
        $scope.loadAll = function() {
            StatusOfHoliday.query(function(result) {
               $scope.statusOfHolidays = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            StatusOfHoliday.update($scope.statusOfHoliday,
                function () {
                    $scope.loadAll();
                    $('#saveStatusOfHolidayModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            StatusOfHoliday.get({id: id}, function(result) {
                $scope.statusOfHoliday = result;
                $('#saveStatusOfHolidayModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            StatusOfHoliday.get({id: id}, function(result) {
                $scope.statusOfHoliday = result;
                $('#deleteStatusOfHolidayConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StatusOfHoliday.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatusOfHolidayConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.statusOfHoliday = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
