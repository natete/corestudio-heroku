/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];

    function config($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('admin', {
                url: '/admin',
                templateUrl: 'scripts/app/admin/admin.html'
            })
            .state('admin.holidays', {
                url: '/holidays',
                templateUrl: 'scripts/app/admin/holidays/admin.holidays.html',
                controller: 'HolidaysController',
                controllerAs: 'holidays'
            })
            .state('admin.activities', {
                url: '/activities',
                templateUrl: 'scripts/app/admin/activities/admin.activities.html',
                controller: 'ActivitiesController',
                controllerAs: 'activities'
            })
            .state('admin.passTypes', {
                url: '/passTypes',
                templateUrl: 'scripts/app/admin/passType/admin.passType.html',
                controller: 'PassTypeController',
                controllerAs: 'passTypes'
            });
    }
})();

