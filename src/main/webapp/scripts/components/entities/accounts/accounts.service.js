/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.accounts')
        .factory('Accounts', Accounts);

    Accounts.$inject = ['$resource', 'ACCOUNTS_ENDPOINT'];

    function Accounts($resource, ACCOUNTS_ENDPOINT) {
        return $resource(ACCOUNTS_ENDPOINT, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }

})();

