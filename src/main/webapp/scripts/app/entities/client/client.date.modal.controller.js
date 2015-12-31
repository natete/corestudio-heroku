/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 31/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientModalDateController', ClientModalDateController);

    ClientModalDateController.$inject = ['$uibModalInstance', 'date'];

    function ClientModalDateController($uibModalInstance, date) {
        var vm = this;
        vm.selectedDate = date;
        vm.dismiss = dismiss;
        vm.release = release;
        vm.freeze = freeze;
        vm.consume = consume;
        vm.canBeFrozen = canBeFrozen;
        vm.canBeReleased = canBeReleased;
        vm.canBeConsumed = canBeConsumed;

        var dateTypes = {
            PENDING: 'pending-date',
            FROZEN: 'frozen-date',
            CONSUMED: 'consumed-date'
        };

        /////////////////////////////////

        function dismiss() {
            $uibModalInstance.dismiss('cancel');
        }

        function release() {
            if(vm.selectedDate.type === dateTypes.PENDING) {
                dismiss();
            } else {
                $uibModalInstance.close('RELEASE');
            }
        }

        function freeze() {
            if(vm.selectedDate.type === dateTypes.FROZEN) {
                dismiss();
            } else {

                $uibModalInstance.close('FREEZE');
            }
        }

        function consume() {
            if(vm.selectedDate.type === dateTypes.CONSUMED) {
                dismiss();
            } else {

                $uibModalInstance.close('CONSUME');
            }
        }
        
        function canBeFrozen() {
            var dateType = vm.selectedDate.type;
            return dateType === dateTypes.CONSUMED || dateType === dateTypes.PENDING; 
        }

        function canBeReleased() {
            var dateType = vm.selectedDate.type;
            return dateType === dateTypes.CONSUMED || dateType === dateTypes.FROZEN;
        }

        function canBeConsumed() {
            return vm.selectedDate.type !== dateTypes.CONSUMED;
        }
    }

})();

