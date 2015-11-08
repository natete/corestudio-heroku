/**
 * Created by natete on 02/11/15.
 */
(function() {
    'use strict';

    angular.module('corestudioApp.client')
        .factory('Client', ['$resource', 'CLIENT_ENDPOINT', function ($resource, CLIENT_ENDPOINT) {
            return $resource(CLIENT_ENDPOINT);
        }]);
})();