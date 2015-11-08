'use strict';

angular.module('corestudioApp')
.factory('Auth', ['LOGIN_ENDPOINT', '$http', '$cookieStore', '$rootScope', function(LOGIN_ENDPOINT, $http, $cookieStore, $rootScope) {

        var auth = {};

        auth.login = function (username, password) {
            $http.post(LOGIN_ENDPOINT, $.param({username: username, password: password}), {
                headers : {
                    "content-type" : "application/x-www-form-urlencoded"
                }
        	}).success(function (data) {
                this.authenticate();
            }).error(function () {
                auth.user = undefined;
                $cookieStore.remove('user');
                $rootScope.authenticated = false;
            });
        };

        auth.logout = function() {
            return $http.post(LOGOUT_ENDPOINT).then(function(response) {
                auth.user = undefined;
                $cookieStore.remove('user');
            })
        };
        
        auth.authenticate = function (callback) {
            $http.get('api/user').success(function(data) {
                if (data.name) {
                    auth.user = data;
                    $cookieStore.put('user', auth.user);
                    $rootScope.authenticated = true;
                } else {
                    auth.user = undefined;
                    $cookieStore.remove('user');
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function() {
                $rootScope.authenticated = false;
                callback && callback();
            });
        };



        return auth;
    }]);