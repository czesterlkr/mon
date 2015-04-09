'use strict';

angular.module('monApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('kindOfHoliday', {
                parent: 'entity',
                url: '/kindOfHoliday',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.kindOfHoliday.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kindOfHoliday/kindOfHolidays.html',
                        controller: 'KindOfHolidayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kindOfHoliday');
                        return $translate.refresh();
                    }]
                }
            })
            .state('kindOfHolidayDetail', {
                parent: 'entity',
                url: '/kindOfHoliday/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'monApp.kindOfHoliday.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kindOfHoliday/kindOfHoliday-detail.html',
                        controller: 'KindOfHolidayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kindOfHoliday');
                        return $translate.refresh();
                    }]
                }
            });
    });
