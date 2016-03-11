(function () {

    'use strict';

    angular.module('corestudioApp')
        .factory('Auth', Auth);

    Auth.$inject = ['$q', '$rootScope', 'Principal', 'AuthServerProvider', 'Alerts'];


    function Auth($q, $rootScope, Principal, AuthServerProvider, Alerts) {
        var auth = {};

        auth.login = function (credentials) {
            var deferred = $q.defer();

            AuthServerProvider.login(credentials).then(function (data) {
                // retrieve the logged account information
                Principal.identity(true).then(function (account) {
                    deferred.resolve(account);
                });
            }).catch(function (err) {
                this.logout();
                deferred.reject(err);
            }.bind(this));

            return deferred.promise;
        };

        auth.logout = function () {
            AuthServerProvider.logout();
            Principal.authenticate(null);
            $rootScope.$emit('authentication-change');
            // Reset state memory
            $rootScope.previousStateName = undefined;
            $rootScope.previousStateNameParams = undefined;
        };

        auth.authorize = function (force) {
            var deferred = $q.defer();
            Principal.identity(force)
                .then(function (identity) {
                    var isAuthenticated = Principal.isAuthenticated();

                    // an authenticated user can't access to login and register pages
                    if (isAuthenticated && $rootScope.toState.name === 'login') {
                        //$state.go('inbox');
                        $rootScope.$emit('authentication-change', identity);
                        deferred.resolve(identity);
                    }

                    if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                        if (isAuthenticated) {
                            // user is signed in but not authorized for desired state
                            //$state.go('accessdenied');
                            Alerts.addErrorAlert('No tiene permiso para acceder al recurso');
                            deferred.reject();
                        }
                        else {
                            // user is not authenticated. stow the state they wanted before you
                            // send them to the signin state, so you can return them when you're done
                            $rootScope.previousStateName = $rootScope.toState;
                            $rootScope.previousStateNameParams = $rootScope.toStateParams;

                            // now, send them to the signin state so they can log in
                            //$state.go('login');
                            deferred.reject();
                        }

                    } else {
                        $rootScope.$emit('authentication-change', identity);
                        deferred.resolve(identity);
                    }
                });
            return deferred.promise;
        };

        auth.changePassword = function (passwords) {
            var deferred = $q.defer();

            AuthServerProvider.changePassword(passwords)
                .then(function (response) {
                    deferred.resolve(response);
                })
                .catch(function (response) {
                    deferred.reject(response);
                });

            return deferred.promise;
        };

        return auth;
    }
})();