/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 13/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .filter('range', range)
        .filter('hour', hour)
        .filter('daysArray', daysArray)
        .filter('level', level);

    function range() {
        return function (input, min, max) {
            min = parseInt(min);
            max = parseInt(max);

            for (var i = min; i <= max; i++) {
                input.push(i);
            }
            return input;
        }
    }

    function hour() {
        return function (input) {
            return input + ':00';
        }
    }

    function daysArray() {
        var mapping = {
            'MONDAY': 'Lu',
            'TUESDAY': 'Ma',
            'WEDNESDAY': 'Mi',
            'THURSDAY': 'Ju',
            'FRIDAY': 'Vi',
            'SATURDAY': 'Sa',
            'SUNDAY': 'Do'
        };

        return function (input) {
            var result = input.map(function (day) {
                return mapping[day];
            });
            return result.toString();
        }
    }

    function level() {
        var mapping = {
            'LOW': 'Bajo',
            'MEDIUM': 'Medio',
            'HIGH': 'Alto'
        }
        return function (input) {
            return mapping[input];
        }
    }
})();

