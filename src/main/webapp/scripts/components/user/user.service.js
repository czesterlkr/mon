'use strict';

angular.module('monApp')
    .factory('User', function ($resource) {
        return $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getAllUsers': {method: 'GET', isArray: true, url: 'api/users/'},
//            'update': { method:'GET', url: 'api/updateUser/:login' }
            'update': { method:'POST', url: 'api/updateUser' }
        });
    });
