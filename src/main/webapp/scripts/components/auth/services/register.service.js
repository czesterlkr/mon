'use strict';

angular.module('monApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


