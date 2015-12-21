/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.group')
        .config(config);

    config.$inject = ['$stateProvider'];

    function config($stateProvider) {
        $stateProvider
            .state('groups', {
                url: '/groups',
                templateUrl: 'scripts/app/entities/group/groups.html',
                controller: 'GroupController',
                controllerAs: 'groups'
            });
    }

})();

