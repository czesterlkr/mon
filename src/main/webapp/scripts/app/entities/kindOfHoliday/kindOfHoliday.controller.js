'use strict';

angular.module('monApp')
    .controller('KindOfHolidayController', function ($scope, KindOfHoliday) {
        $scope.kindOfHolidays = [];
        $scope.loadAll = function() {
            KindOfHoliday.query(function(result) {
               $scope.kindOfHolidays = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            KindOfHoliday.update($scope.kindOfHoliday,
                function () {
                    $scope.loadAll();
                    $('#saveKindOfHolidayModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            KindOfHoliday.get({id: id}, function(result) {
                $scope.kindOfHoliday = result;
                $('#saveKindOfHolidayModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            KindOfHoliday.get({id: id}, function(result) {
                $scope.kindOfHoliday = result;
                $('#deleteKindOfHolidayConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            KindOfHoliday.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteKindOfHolidayConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.kindOfHoliday = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
