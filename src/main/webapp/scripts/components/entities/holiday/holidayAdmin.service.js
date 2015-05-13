'use strict';

angular.module('monApp')
    .factory('HolidayAdmin', function ($resource) {
        return $resource('api/holidays/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var fromDateFrom = data.fromDate.split("-");
                    data.fromDate = new Date(new Date(fromDateFrom[0], fromDateFrom[1] - 1, fromDateFrom[2]));
                    var toDateFrom = data.toDate.split("-");
                    data.toDate = new Date(new Date(toDateFrom[0], toDateFrom[1] - 1, toDateFrom[2]));
                    var requestDateFrom = data.requestDate.split("-");
                    data.requestDate = new Date(new Date(requestDateFrom[0], requestDateFrom[1] - 1, requestDateFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
