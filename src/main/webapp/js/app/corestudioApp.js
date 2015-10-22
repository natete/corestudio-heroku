/**
 * Created by natete on 17/10/15.
 */
'use strict';

angular.module('corestudioApp', [
                                    'ui.router',
                                    'corestudioApp.controllers',
                                    'corestudioApp.services',
                                    'corestudioApp.security',
                                    'ngCookies',
                                    'ngResource']);

angular.module('corestudioApp').config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
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
       templateUrl: 'partials/login.html'
   }).state('home', {
       url: '/home',
       controller: 'HomeController',
       resolve: {
           user: ['authService', '$q', function(authService, $q) {
               return authService.user || $q.reject({unAuthorized: true});
           }]
       },
       templateUrl: 'partials/home.html'
   }).state('professor', {
       url: '/professor',
       controller: 'ProfessorController',
       resolve: {
           user: ['authService', '$q', function(authService, $q) {
               return authService.user || $q.reject({unAuthorized: true});
           }]
       },
       templateUrl: 'partials/professors/professor_details.html'
   })
   ;

    $urlRouterProvider.otherwise('/home');
    $locationProvider.html5Mode(true);

    $httpProvider.defaults.withCredentials = true;

}]);

angular.module('corestudioApp.services', []);
angular.module('corestudioApp.controllers', []);