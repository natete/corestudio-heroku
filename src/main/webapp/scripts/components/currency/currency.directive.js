/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.currency')
        .directive('currency', currency);

    function currency() {
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, ngModelController) {
                ngModelController.$parsers.push(function(data) {
                    return data * 100;
                });

                ngModelController.$formatters.push(function(data) {
                    return data / 100;
                });
            }
        }
    }
})();

