'use strict';

angular.module('monApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('holiday', {
                parent: 'entity',
                url: '/holiday',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.holiday.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/holiday/holidays.html',
                        controller: 'HolidayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('holiday');
                        return $translate.refresh();
                    }]
                }
            })
            .state('holidayAdmin', {
                parent: 'entity',
                url: '/holidayAdmin',
                data: {
                    roles: ['ROLE_USER', 'ROLE_ADMIN'],
                    pageTitle: 'monApp.holiday.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/holiday/holidaysAdmin.html',
                        controller: 'HolidayAdminController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('holiday');
                        return $translate.refresh();
                    }]
                }
            })
            .state('holidayDetail', {
                parent: 'entity',
                url: '/holiday/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.holiday.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/holiday/holiday-detail.html',
                        controller: 'HolidayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('holiday');
                        return $translate.refresh();
                    }]
                }
            });
    });
