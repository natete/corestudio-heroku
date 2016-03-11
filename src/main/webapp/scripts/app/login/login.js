/**
 * Created by natete on 25/10/15.
 */

'use strict';

angular.module('corestudioApp')
    .config(function($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            controller: 'LoginController',
            controllerAs: 'loginCtrl',
            templateUrl: 'scripts/app/login/login.html',
            resolve: {
                auth: ['$q', 'Principal', function($q, Principal) {
                    var deferred = $q.defer();
                    if(Principal.isAuthenticated()) {
                        deferred.reject();
                    } else {
                        deferred.resolve();
                    }
                    return deferred.promise;
                }]
            }
        });
    });