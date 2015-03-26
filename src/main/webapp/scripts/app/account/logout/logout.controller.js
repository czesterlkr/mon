'use strict';

angular.module('monApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
