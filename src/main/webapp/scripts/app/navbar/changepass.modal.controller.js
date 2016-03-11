/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 12/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .controller('ChangepassController', ChangepassController);

    ChangepassController.$inject = ['$uibModalInstance', '$scope'];

    function ChangepassController($uibModalInstance, $scope) {
        var vm = this;
        vm.title = 'Cambiar contraseña';
        vm.dismiss = dismiss;
        vm.savePassword = savePassword;

        /////////////////////

        function dismiss() {
            $uibModalInstance.dismiss('CANCEL');
        }

        function savePassword() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.changePasswordForm.$valid) {
                $uibModalInstance.close({action: 'SAVE', passwords: {oldPassword: vm.oldPassword, newPassword: vm.newPassword}});
            }
        }
    }

})();

