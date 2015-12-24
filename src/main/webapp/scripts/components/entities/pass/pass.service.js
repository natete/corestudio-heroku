/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.pass')
        .factory('Pass', Pass);

    Pass.$inject = ['$resource', 'PASS_ENDPOINT'];

    function Pass($resource, PASS_ENDPOINT) {
        return $resource(PASS_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {method: 'POST'},
            'getByClient': {
                method: 'GET',
                isArray: true,
                url: 'api/pass/getByClient/:clientId'
            }
        });
    }

})();

