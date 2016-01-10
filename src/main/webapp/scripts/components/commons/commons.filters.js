/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .filter('siNo', siNo)
        .filter('translateDays', translateDays)
        .filter('translateLevel', translateLevel)
        .filter('toUnderscore', toUnderscore);

    function siNo() {
        return function (input) {
            return input ? 'Sí' : 'No';
        }
    }

    function translateDays() {
        return function (input) {
            var daysMapper = {
                MONDAY: 'Lunes',
                TUESDAY: 'Martes',
                WEDNESDAY: 'Miércoles',
                THURSDAY: 'Jueves',
                FRIDAY: 'Viernes',
                SATURDAY: 'Sábado',
                SUNDAY: 'Domingo'
            };

            var translated = input.map(function(value) {
                return daysMapper[value];
            });

            return translated.join(', ');
        }
    }

    function translateLevel() {
        return function (input) {
            var levelsMapper = {
                LOW: 'Bajo',
                MEDIUM: 'Medio',
                HIGH: 'Alto'
            };

            return levelsMapper[input];
        }
    }

    function toUnderscore() {
        return function (input) {
            return input.replace(/\s/g, '_');
        }
    }
})();

