/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .directive('matchValidator', matchValidator);

    function matchValidator() {
        return {
            require: 'ngModel',
            restrict: 'A',
            link: link
        }
    }

    function link (scope, elem, attrs, ngModel) {
        ngModel.$parsers.unshift(validate);

        // Force-trigger the parsing pipeline.
        scope.$watch(attrs.matchValidator, function () {
            validate(ngModel.$viewValue);
        });

        function validate(value) {
            var isValid = scope.$eval(attrs.matchValidator) == value;

            ngModel.$setValidity('match', isValid);

            return isValid ? value : undefined;
        }
    }
})();

