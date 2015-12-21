/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.modals')
        .controller('ConfirmModalController', ConfirmModalController);

    ConfirmModalController.$inject = ['$uibModalInstance', 'title', 'message'];

    function ConfirmModalController($uibModalInstance, title, message) {
        var vm = this;

        vm.dismiss = dismiss;
        vm.confirm = confirm;

        activate();

        ///////////////////////////////////

        function activate() {
            vm.title = title;
            vm.message = message;
        }

        function dismiss() {
            $uibModalInstance.dismiss('CANCEL');
        }

        function confirm() {
            $uibModalInstance.close('OK');
        }
    }
})();

