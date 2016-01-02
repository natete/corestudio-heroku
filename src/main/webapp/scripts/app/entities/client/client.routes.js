/**
 * Created by natete on 07/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];

    function config($stateProvider, $urlRouterProvider) {
        $urlRouterProvider
            .when('/clients', '/clients/list');
        $stateProvider
            .state('clients', {
                url: '/clients',
                templateUrl: 'scripts/app/entities/client/clients.html'
                //abstract: true,
            })
            .state('clients.list', {
                url: '/list',
                templateUrl: 'scripts/app/entities/client/clients.list.html',
                controller: 'ClientController',
                controllerAs: 'clients'
            })
            .state('clients.newClient', {
                url: '/new',
                templateUrl: 'scripts/app/entities/client/client.form.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm'
            })
            .state('clients.viewClient', {
                url: '/:id/:permalink/view',
                templateUrl: 'scripts/app/entities/client/client.details.html',
                //abstract: true,
                controller: 'ClientTabsController',
                controllerAs: 'tabs'
                //params: {
                //    client: undefined
                //}
            })
            .state('clients.viewClient.personal', {
                url: '/personal',
                templateUrl: 'scripts/app/entities/client/client.personal.html'
            })
            .state('clients.viewClient.passes', {
                url: '/payments',
                templateUrl: 'scripts/app/entities/client/client.pass.html',
                controller: 'ClientPassController',
                controllerAs: 'vm'
            })
            .state('clients.viewClient.calendar', {
                url: '/calendar',
                templateUrl: 'scripts/app/entities/client/client.calendar.html',
                controller: 'ClientCalendarController',
                controllerAs: 'vm'
            })
            .state('clients.editClient', {
                url: '/:id/:permalink/edit',
                templateUrl: 'scripts/app/entities/client/client.form.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm'
                //params: {
                //    client: undefined
                //}
            });
    }
})();