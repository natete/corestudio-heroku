/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .filter('passType', passType);

    function passType() {
        return function (input) {
            return input.activity.name + ' ' + input.numberOfSessions + ' sesiones';
        };
    }
})();

