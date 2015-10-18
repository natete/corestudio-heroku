/**
 * Created by natete on 18/10/15.
 */

'use strict';

angular.module('corestudioApp.security', ['corestudioApp.services']);

angular.module('corestudioApp.security').
    run(['$rootScope', '$state', '$cookieStore', 'authService', function($rootScope, $state, $cookieStore, authService) {
        $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error) {
            if(error.unAuthorized) {
                $state.go('login');
            } else if (error.authorized) {
                $state.go('home');
            }
        });

        authService.user = $cookieStore.get('user');
    }]);