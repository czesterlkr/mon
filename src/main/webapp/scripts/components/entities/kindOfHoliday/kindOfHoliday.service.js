'use strict';

angular.module('monApp')
    .factory('KindOfHoliday', function ($resource) {
        return $resource('api/kindOfHolidays/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
