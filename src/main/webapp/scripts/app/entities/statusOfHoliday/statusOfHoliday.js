'use strict';

angular.module('monApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('statusOfHoliday', {
                parent: 'entity',
                url: '/statusOfHoliday',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.statusOfHoliday.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statusOfHoliday/statusOfHolidays.html',
                        controller: 'StatusOfHolidayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statusOfHoliday');
                        return $translate.refresh();
                    }]
                }
            })
            .state('statusOfHolidayDetail', {
                parent: 'entity',
                url: '/statusOfHoliday/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.statusOfHoliday.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statusOfHoliday/statusOfHoliday-detail.html',
                        controller: 'StatusOfHolidayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statusOfHoliday');
                        return $translate.refresh();
                    }]
                }
            });
    });
