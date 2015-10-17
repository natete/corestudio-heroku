/**
 * Created by natete on 17/10/15.
 */
'use strict';

angular.module('corestudioApp', ['ui.router', 'corestudioApp.controllers', 'corestudioApp.services', 'ngCookies']);

angular.module('corestudioApp').config(function($stateProvider, $urlRouterProvider, $locationProvider) {
   $stateProvider.state('login', {
       url: '/login',
       controller: 'LoginController',
       templateUrl: 'partials/login.html'
   }).state('home', {
       url: '/home',
       controller: 'HomeController',
       templateUrl: 'partials/home.html'
   });

    $urlRouterProvider.otherwise('/login');
    $locationProvider.html5Mode(true);
});