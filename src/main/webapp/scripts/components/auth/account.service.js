/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .factory('Account', function Account($resource) {
            return $resource('api/auth/account', {}, {
                'get': {
                    method: 'GET', params: {}, isArray: false,
                    interceptor: {
                        response: function (response) {
                            // expose response
                            return response;
                        }
                    }
                }
            });
        });
})();

