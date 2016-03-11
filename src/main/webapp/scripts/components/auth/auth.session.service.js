/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .factory('AuthServerProvider', AuthServerProvider);

    AuthServerProvider.$inject = ['$http'];

    function AuthServerProvider($http) {
        return {
            login: function (credentials) {
                var data = 'username=' + encodeURIComponent(credentials.username) +
                    '&password=' + encodeURIComponent(credentials.password) + '&submit=Login';

                return $http.post('api/authentication', data, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (response) {
                    return response;
                });
            },
            logout: function () {
                // logout from the server
                $http.post('api/logout').success(function (response) {
                    // to get a new csrf token call the api
                    return response;
                });
            },
            changePassword: function(passwords) {
                return $http.post('/api/auth/changePassword', passwords);
            }
        };
    }
})();

