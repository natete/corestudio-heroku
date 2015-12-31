/**
 * Created by natete on 02/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .factory('Client', Client);

    Client.$inject = ['$resource', '$cacheFactory', 'CLIENT_ENDPOINT'];

    function Client($resource, $cacheFactory, CLIENT_ENDPOINT) {
        var cache = $cacheFactory('clientCache');

        return $resource(CLIENT_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                cache: cache,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {
                method: 'POST',
                interceptor: {
                    response: function (response) {
                        cache.removeAll();
                        return response.data;
                    }
                }
            }
        });
    }
})();