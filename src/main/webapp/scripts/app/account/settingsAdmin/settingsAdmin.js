'use strict';

angular.module('monApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settingsAdmin', {
                parent: 'entity',
                url: '/settingsEmployeesList',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.settingsAdmin'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/settingsAdmin/settingsEmployeesList.html',
                        controller: 'SettingsAdminController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        return $translate.refresh();
                    }]
                }
            });
    });
