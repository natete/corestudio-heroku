/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .factory('MonthlySession', MonthlySession);

    MonthlySession.$inject = ['$resource', 'MONTHLY_SESSION_ENDPOINT'];

    function MonthlySession($resource, MONTHLY_SESSION_ENDPOINT) {
        return $resource(MONTHLY_SESSION_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true},
            'update': {
                method: 'PUT'
            },
            'save': {
                method: 'POST'
            }
        });
    }

})();

