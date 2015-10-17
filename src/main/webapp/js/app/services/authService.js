'use strict';

angular.module('corestudioApp.services', [])
.factory('authService', ['LOGIN_ENDPOINT', '$http', '$cookieStore', function(LOGIN_ENDPOINT, $http, $cookieStore) {
        var auth = {};

        auth.login = function (username, password) {
            return $http.post(LOGIN_ENDPOINT, {username: username, password: password})
                .then(function(response, status) {
                    auth.user = response.data;
                    $cookieStore.put('user', auth.user);
                    return auth.user;
                });
        };

        return auth;
    }]);