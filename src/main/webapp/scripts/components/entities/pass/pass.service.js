/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.pass')
        .factory('Pass', Pass);

    Pass.$inject = ['$resource', 'PASS_ENDPOINT', 'GET_PASSES_BY_CLIENT_ENDPOINT', 'GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT', '$cacheFactory', 'FREEZE_DATE_ENDPOINT', 'CONSUME_DATE_ENDPOINT'];

    function Pass($resource, PASS_ENDPOINT, GET_PASSES_BY_CLIENT_ENDPOINT, GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT, $cacheFactory, FREEZE_DATE_ENDPOINT, CONSUME_DATE_ENDPOINT) {

        var cache = $cacheFactory('passCache');

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
            'save': {
                method: 'POST',
                interceptor: {
                    response: function (response) {
                        cache.removeAll();
                        return response.data;
                    }
                }
            },
            'getByClient': {
                method: 'GET',
                isArray: true,
                cache: cache,
                url: GET_PASSES_BY_CLIENT_ENDPOINT
            },
            'getByClientAndYear': {
                method: 'GET',
                isArray: true,
                cache: cache,
                url: GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT
            },
            'freezeDate': {
                method: 'POST',
                url: FREEZE_DATE_ENDPOINT,
                interceptor: {
                    response: function (response) {
                        cache.removeAll();
                        return response.data;
                    }
                }
            },
            'consumeDate': {
                method: 'POST',
                url: CONSUME_DATE_ENDPOINT,
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

