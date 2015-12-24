/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.group')
        .filter('group', group);

    group.$inject = ['translateDaysFilter', 'translateLevelFilter']

    function group(translateDaysFilter, translateLevelFilter) {
        return function (input) {
            return translateDaysFilter(input.days) + ' ' + input.hour + ':00 - Nivel ' + translateLevelFilter(input.level);
        }
    }

})();

