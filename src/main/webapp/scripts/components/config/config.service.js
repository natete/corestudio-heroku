/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 12/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .factory('Config', config);

    config.$inject = ['$resource', 'CONFIG_ENDPOINT'];

    function config($resource, CONFIG_ENDPOINT) {
        return $resource(CONFIG_ENDPOINT, {}, {
            'query': {method: 'GET'}
        });
    }

})();

