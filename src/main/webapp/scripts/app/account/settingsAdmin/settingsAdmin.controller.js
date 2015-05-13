'use strict';

angular.module('monApp')
    .controller('SettingsAdminController', function ($scope, Principal, Auth, User, Project) {
        $scope.users = [];
        $scope.projects = Project.query();
        $scope.success = null;
        $scope.error = null;
        Principal.identity().then(function(account) {
            $scope.settingsAccount = account;
        });

        $scope.loadAll = function() {
            User.getAllUsers(function(result) {
                $scope.users = result;
            });
        };

        $scope.loadAll();

//        $scope.save = function () {
//            Auth.updateAccount($scope.settingsAccount).then(function() {
//                $scope.error = null;
//                $scope.success = 'OK';
//                Principal.identity().then(function(account) {
//                    $scope.settingsAccount = account;
//                });
//            }).catch(function() {
//                $scope.success = null;
//                $scope.error = 'ERROR';
//            });
//        };

        $scope.update = function (login) {
            User.get({login: login}, function(result) {
                $scope.user = result;
                $('#saveUserModal').modal('show');
            });
        };

        $scope.create = function (login) {
            User.update($scope.user,
                function () {
                    $scope.loadAll();
                    $('#saveUserModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.user = {firstName: null, lastName: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
