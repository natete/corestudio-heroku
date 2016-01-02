/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];

    function config($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.when('/professors', '/professors/list');

        $stateProvider
            .state('professors', {
                url: '/professors',
                templateUrl: 'scripts/app/entities/professor/professor.html'
            })
            .state('professors.list', {
                url: '/list',
                templateUrl: 'scripts/app/entities/professor/professor.list.html',
                controller: 'ProfessorsListController',
                controllerAs: 'professorsListCtrl'
            })
            .state('professors.newProfessor', {
                url: '/new',
                templateUrl: 'scripts/app/entities/professor/professor.form.html',
                controller: 'ProfessorFormController',
                controllerAs: 'professorFormCtrl'
            });
    }

})();

