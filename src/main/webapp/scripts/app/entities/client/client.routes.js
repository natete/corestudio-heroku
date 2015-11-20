/**
 * Created by natete on 07/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];

    function config($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.when('/clients', '/clients/list')
        $stateProvider
            .state('clients', {
                url: '/clients',
                template: '<ui-view/>',
                //abstract: true,
            })
            .state('clients.list', {
                url: '/list',
                templateUrl: 'scripts/app/entities/client/clients.html',
                controller: 'ClientController',
                controllerAs: 'clients'
            })
            .state('clients.newClient', {
                url: '/new',
                templateUrl: 'scripts/app/entities/client/client_details.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm',
                resolve: {
                    editMode: function() {
                        return true;
                    }
                }
            })
            .state('clients.viewClient', {
                url: '/:id/:permalink/view',
                templateUrl: 'scripts/app/entities/client/client_details.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm',
                resolve: {
                    editMode: function() {
                        return false;
                    }
                }
            })
            .state('clients.editClient', {
                url: '/:id/:permalink/edit',
                templateUrl: 'scripts/app/entities/client/client_details.html',
                controller: 'ClientFormController',
                controllerAs: 'clientForm',
                resolve: {
                    editMode: function() {
                        return true;
                    }
                }
            });
    }
})();