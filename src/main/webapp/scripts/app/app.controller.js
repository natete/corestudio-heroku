/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .controller('MainController', MainController);

    MainController.$inject = ['Alerts'];

    function MainController(Alerts) {
        var vm = this;
        vm.alerts = Alerts.list;
        vm.closeAlert = closeAlert;

        function closeAlert(index) {
            Alerts.closeAlert(index);
        }
    }

})();

