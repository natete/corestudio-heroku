/**
 * Created by natete on 02/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .factory('Client', Client);

    Client.$inject = ['$resource', 'CLIENT_ENDPOINT'];

    function Client($resource, CLIENT_ENDPOINT) {
        return $resource(CLIENT_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {method: 'POST'}
        });
    }
})();