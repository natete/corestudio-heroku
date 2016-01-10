/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.pass')
        .factory('Pass', Pass);

    Pass.$inject = ['$resource', '$cacheFactory', 'PASS_ENDPOINTS'];

    function Pass($resource, $cacheFactory, PASS_ENDPOINTS) {

        var cache = $cacheFactory('passCache');

        return $resource(PASS_ENDPOINTS.PASS_ENDPOINT, {clientId: '@clientId'}, {
            'query': {method: 'GET'},
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
                cache: cache,
                url: PASS_ENDPOINTS.GET_PASSES_BY_CLIENT_ENDPOINT
            },
            'getByClientAndYear': {
                method: 'GET',
                isArray: true,
                cache: cache,
                url: PASS_ENDPOINTS.GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT
            },
            'freezeDate': {
                method: 'POST',
                url: PASS_ENDPOINTS.FREEZE_DATE_ENDPOINT,
                interceptor: {
                    response: function (response) {
                        cache.removeAll();
                        return response.data;
                    }
                }
            },
            'consumeDate': {
                method: 'POST',
                url: PASS_ENDPOINTS.CONSUME_DATE_ENDPOINT,
                interceptor: {
                    response: function (response) {
                        cache.removeAll();
                        return response.data;
                    }
                }
            },
            'releaseDate': {
                method: 'POST',
                url: PASS_ENDPOINTS.RELEASE_DATE_ENDPOINT,
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

