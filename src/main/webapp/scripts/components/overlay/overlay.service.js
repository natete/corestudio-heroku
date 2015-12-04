/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 29/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.overlay', []);

    angular.module('corestudioApp.overlay')
        .factory('Overlay', Overlay);

    Overlay.$inject = ['$rootScope'];

    function Overlay($rootScope) {
        return {
            on: function() {
                $rootScope.spinner.active = true;
            },
            off: function() {
                $rootScope.spinner.active = false;
            }
        }
    }
})();

