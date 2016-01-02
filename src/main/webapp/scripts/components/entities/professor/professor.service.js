/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .factory('Professor', Professor);

    Professor.$inject = ['$resource', '$cacheFactory', 'PROFESSOR_ENDPOINT'];

    function Professor($resource, $cacheFactory, PROFESSOR_ENDPOINT) {
        var cache = $cacheFactory('professorCache');

        var interceptor = {
            response: function (response) {
                cache.removeAll();
                return response.data;
            }
        };

        return $resource(PROFESSOR_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                cache: cache,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                interceptor: interceptor
            },
            'save': {
                method: 'POST',
                interceptor: interceptor
            }
        });
    }

    }

    )();

