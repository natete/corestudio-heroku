/**
 * Created by natete on 02/11/15.
 */
(function () {
    'use strict';

    angular
        .module('corestudioApp')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider'];

    function config ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

        $urlRouterProvider.otherwise('/login');
        $locationProvider.html5Mode(true);

        //$httpProvider.defaults.withCredentials = true;

    }
})();