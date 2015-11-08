/**
 * Created by natete on 25/10/15.
 */

'use strict';

angular.module('corestudioApp')
    .config(function($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            controller: 'LoginController',
            resolve: {
                user:['authService', '$q', function(authService, $q) {
                    if(authService.user) {
                        return $q.reject({authorized: true});
                    }
                }]
            },
            templateUrl: 'scripts/app/login/login.html'
        });
    });