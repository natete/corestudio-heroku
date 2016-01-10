/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .factory('PassType', PassType);

    PassType.$inject = ['$resource', 'PASS_TYPES_ENDPOINT'];

    function PassType($resource, PASS_TYPES_ENDPOINT) {
        return $resource(PASS_TYPES_ENDPOINT, {}, {
            'query': {method: 'GET'},
            'get': {
                method: 'GET',
                transformResponse: function(data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {method: 'POST'}
        });
    }
})();

