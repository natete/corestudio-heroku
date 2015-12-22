/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .filter('siNo', siNo);

    function siNo() {
        return function (input) {
            return input ? 'Sí' : 'No';
        }
    }
})();

