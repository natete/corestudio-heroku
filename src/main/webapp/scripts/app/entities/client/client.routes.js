/**
 * Created by natete on 07/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .config(config);

    config.$inject = ['$stateProvider'];

    function config($stateProvider) {
        $stateProvider.state('clients', {
            url: '/clients',
            templateUrl: 'scripts/app/entities/client/clients.html',
            controller: 'ClientController',
            controllerAs: 'clients'
        })
            .state('newClient', {
                url: '/newClient',
                templateUrl: 'scripts/app/entities/client/client_details.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm'
            });
    }
})();