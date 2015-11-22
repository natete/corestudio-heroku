/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('HolidaysModalController', HolidaysModalController);

    HolidaysModalController.$inject = ['$uibModalInstance', 'date'];

    function HolidaysModalController($uibModalInstance, date) {
        var vm = this;
        vm.selectedDate = date;
        vm.close = close;

        function close() {
            $uibModalInstance.dismiss('cancel');
        }
    }

})();

