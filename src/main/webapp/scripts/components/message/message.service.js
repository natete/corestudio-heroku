/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.message')
        .service('Message', Message);

    Message.$inject = ['$resource', 'MESSAGE_ENDPOINT'];

    function Message($resource, MESSAGE_ENDPOINT) {
        return $resource(MESSAGE_ENDPOINT, {id: '@id'}, {
            'query': {
                method: 'GET'
            },
            'delete': {method: 'DELETE'},
            'update': {method: 'PUT'}
        });
    }

})();

