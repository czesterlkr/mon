'use strict';

angular.module('monApp')
    .factory('StatusOfHoliday', function ($resource) {
        return $resource('api/statusOfHolidays/:id', {}, {
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
