/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.accounts')
        .config(config);

    config.$inject = ['$stateProvider'];

    function config($stateProvider) {
        $stateProvider
            .state('accounts', {
                url: '/accounts',
                templateUrl: 'scripts/app/entities/accounts/accounts.html',
                controller: 'AccountsController',
                controllerAs: 'accountsCtrl'
            });
    }
})();

