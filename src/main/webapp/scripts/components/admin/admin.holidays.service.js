/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .factory('Holiday', Holiday);

    Holiday.$inject = ['$resource', 'HOLIDAYS_ENDPOINT'];

    function Holiday ($resource, HOLIDAYS_ENDPOINT) {
        return $resource(HOLIDAYS_ENDPOINT, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                cache: true
            },
            'get': {
                method: 'GET',
                cache: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {method: 'POST'},
            'delete': {method: 'DELETE'}
        });
    }
})();

