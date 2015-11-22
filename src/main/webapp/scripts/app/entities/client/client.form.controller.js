/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    ClientFormController.$inject = ['Client', 'editMode', '$stateParams', '$scope', '$state'];

    function ClientFormController(Client, editMode, $stateParams, $scope, $state) {
        var vm = this;

        vm.editMode = editMode;
        vm.client = {};
        vm.birthdateDatepicker = {
            opened: false
        };
        vm.admissionDateDatepicker = {
            opened: false
        };
        vm.saveDisabled = false;
        vm.saveBtnText = 'Guardar';

        vm.saveClient = processSaveClient;
        vm.updateClient = updateClient;

        activate();

        ////////
        function activate() {
            if ($stateParams.id !== undefined) {
                var result = Client.get({id: $stateParams.id});
                result.$promise.then(function (client) {
                    vm.client = client;
                    vm.client = parseDates(vm.client);
                });
            }
        }

        function processSaveClient() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.userForm.$invalid) {
                return;
            } else {
                vm.saveDisabled = true;
                vm.saveBtnText = 'Guardando...';
                vm.client = parseDates(vm.client);
                if ($state.is('clients.newClient')) {
                    saveClient();
                } else if ($state.is('clients.editClient')) {
                    updateClient();
                }
            }
        }

        function saveClient() {
            var result = Client.save(vm.client);
            result.$promise.then(function () {
                $state.go('clients');
            });
        }

        function updateClient() {
            var result = Client.update(vm.client);
            result.$promise.then(function () {
                $state.go('clients');
            });
        }

        function parseDates(client) {
            if (client.birthdate) {
                client.birthdate = new Date(client.birthdate);
            }
            if (client.admissionDate) {
                client.admissionDate = new Date(client.admissionDate);
            }
            return client;
        }

    }
})();