/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];

    function config($stateProvider) {
        $stateProvider
            .state('admin', {
                url: '/admin',
                templateUrl: 'scripts/app/admin/admin.html'
            })
            .state('admin.holidays', {
                url: '/holidays',
                templateUrl: 'scripts/app/admin/holidays/admin.holidays.html',
                controller: 'HolidaysController',
                controllerAs: 'holidays',
                data: {
                    authorities: ['ADMIN']
                },
                resolve: {
                    principal: ['Auth', function (Auth) {
                        return Auth.authorize();
                    }]
                }
            })
            .state('admin.activities', {
                url: '/activities',
                templateUrl: 'scripts/app/admin/activities/admin.activities.html',
                controller: 'ActivitiesController',
                controllerAs: 'activities',
                data: {
                    authorities: ['ADMIN']
                },
                resolve: {
                    principal: ['Auth', function (Auth) {
                        return Auth.authorize();
                    }]
                }
            })
            .state('admin.passTypes', {
                url: '/passTypes',
                templateUrl: 'scripts/app/admin/passType/admin.passType.html',
                controller: 'PassTypeController',
                controllerAs: 'passTypes',
                data: {
                    authorities: ['ADMIN']
                },
                resolve: {
                    principal: ['Auth', function (Auth) {
                        return Auth.authorize();
                    }]
                }
            })
            .state('admin.expenses', {
                url: '/expenses',
                templateUrl: 'scripts/app/admin/expense/admin.expenses.html',
                controller: 'ExpenseController',
                controllerAs: 'expensesCtrl',
                data: {
                    authorities: ['ADMIN']
                },
                resolve: {
                    principal: ['Auth', function (Auth) {
                        return Auth.authorize();
                    }]
                }
            })
        ;
    }
})();

