'use strict';

angular.module('monApp')
    .controller('HolidayAdminController', function ($scope, HolidayAdmin, KindOfHoliday, StatusOfHoliday, User, ParseLinks) {
        $scope.holidays = [];
        $scope.kindofholidays = KindOfHoliday.query();
        $scope.statusofholidays = StatusOfHoliday.query();
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            HolidayAdmin.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.holidays = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            HolidayAdmin.update($scope.holiday,
                function () {
                    $scope.loadAll();
                    $('#saveHolidayModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            HolidayAdmin.get({id: id}, function(result) {
                $scope.holiday = result;
                $('#saveHolidayModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            HolidayAdmin.get({id: id}, function(result) {
                $scope.holiday = result;
                $('#deleteHolidayConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HolidayAdmin.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHolidayConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.holiday = {fromDate: null, toDate: null, requestDate: null, comment: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
