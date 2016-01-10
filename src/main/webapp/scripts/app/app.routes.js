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
        //$stateProvider.state('home', {
        //    url: '/home',
        //    //controller: 'HomeController',
        //    //resolve: {
        //    //    user: ['authService', '$q', function(authService, $q) {
        //    //        return authService.user || $q.reject({unAuthorized: true});
        //    //    }]
        //    //},
        //    templateUrl: 'partials/home.html'
        //})
        //    .state('professor', {
        //    url: '/professor',
        //    controller: 'ProfessorController',
        //    //resolve: {
        //    //    user: ['authService', '$q', function(authService, $q) {
        //    //        return authService.user || $q.reject({unAuthorized: true});
        //    //    }]
        //    //},
        //    templateUrl: 'partials/professors/professor_details.html'
        //})
        ;

        $urlRouterProvider.otherwise('/inbox');
        $locationProvider.html5Mode(true);

        //$httpProvider.defaults.withCredentials = true;

    }
})();