/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$rootScope', '$state', '$uibModal', 'Auth', 'Alerts'];

    function NavbarController($rootScope, $state, $uibModal, Auth, Alerts) {
        var vm = this;

        vm.logout = logout;
        vm.changePassword = changePassword;

        ////////////////////////////

        activate();

        function activate() {

            $rootScope.$on('authentication-change', function (event, identity) {
                vm.user = identity;
                vm.authenticated = identity ? true : false;
                vm.isAdmin = vm.authenticated && identity.authorities.indexOf('ADMIN') >= 0 ? true : false;
            });
        }

        function logout() {
            Auth.logout();
            vm.user = undefined;
            vm.authenticated = false;
            $state.go('login');
        }

        function changePassword() {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/navbar/changepass.modal.html',
                controller: 'ChangepassController',
                controllerAs: 'modal'
            });

            modalInstance.result.then(function (result) {
                if (result.action === 'SAVE') {
                    Auth.changePassword(result.passwords)
                        .then(function (responseData) {
                            Alerts.addHeaderSuccessAlert(responseData.headers());
                        })
                        .catch(function (response) {
                            Alerts.addHeaderErrorAlert(response.headers());
                        });
                }
            });
        }
    }
})();

