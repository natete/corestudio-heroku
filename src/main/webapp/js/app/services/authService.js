'use strict';

angular.module('corestudioApp.services')
.factory('authService', ['LOGIN_ENDPOINT', '$http', '$cookieStore', function(LOGIN_ENDPOINT, $http, $cookieStore) {
        var auth = {};

        auth.login = function (username, password) {
            return $http.post(LOGIN_ENDPOINT, $.param({username: username, password: password}), {
                headers : {
                    "content-type" : "application/x-www-form-urlencoded"
                }
        	})
                .then(function(response, status) {
                    auth.user = response.data;
                    $cookieStore.put('user', auth.user);
                    return auth.user;
                });
        };

        auth.logout = function() {
            return $http.post(LOGOUT_ENDPOINT).then(function(response) {
                auth.user = undefined;
                $cookieStore.remove('user');
            })
        }

        return auth;
    }]);