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
        vm.dismiss = dismiss;
        vm.saveDate = saveDate;
        vm.deleteDate = deleteDate;

        /////////////////////////////

        function dismiss() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveDate() {
            if(vm.selectedDate.type === undefined) {
                $uibModalInstance.close('save');
            } else {
                $uibModalInstance.close('update');
            }
        }

        function deleteDate() {
            $uibModalInstance.close('delete');
        }
    }

})();

