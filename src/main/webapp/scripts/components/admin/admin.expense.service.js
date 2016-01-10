/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .factory('Expense', Expense);

    Expense.$inject = ['$resource', 'EXPENSES_ENDPOINT'];

    function Expense($resource, EXPENSES_ENDPOINT) {
        return $resource(EXPENSES_ENDPOINT, {}, {
            'query': {
                method: 'GET',
                isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT'
            },
            'save': {
                method: 'POST'
            }
        });
    }

})();

