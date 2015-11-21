/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('HolidaysController', [function() {
            var vm = this;
            vm.yearsList = [];

            activate();

            //////////////

            function activate() {
                var currentYear = new Date().getFullYear();
                vm.year = currentYear;
                var firstYear = currentYear - 10;

                for(var i = 0; i < 20; i++) {
                    vm.yearsList.push(firstYear + i);
                }

            }
        }]);
})();

