/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    ClientFormController.$inject = ['Client', '$stateParams', '$scope', '$state', 'Alerts'];

    function ClientFormController(Client, $stateParams, $scope, $state, Alerts) {
        var vm = this;

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
            if ($state.is('clients.newClient')) {
                vm.area = 'Nuevo cliente';
                vm.classArea = 'fa-eye';
            } else if ($state.is('clients.editClient')) {
                vm.area = 'Editar cliente';
                vm.classArea = 'fa-pencil-square-o';
            }
            if ($stateParams.id !== undefined) {
                Client.get({id: $stateParams.id}, function (client) {
                    vm.client = parseDates(client);
                }, function () {
                    Alerts.addErrorAlert('Se ha producido un error accediendo al cliente')
                });
            }
        }

        function processSaveClient() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.userForm.$invalid) {
                Alerts.addErrorAlert('El formulario de creación contiene datos erróneos');
            } else {
                vm.saveDisabled = true;
                vm.saveBtnText = 'Guardando...';
                if ($state.is('clients.newClient')) {
                    saveClient();
                } else if ($state.is('clients.editClient')) {
                    updateClient();
                }
            }
        }

        function saveClient() {
            Client.save(vm.client, function (client) {
                $state.go('clients');
                Alerts.addSuccessAlert('Se ha guardado el cliente ' + client.name + ' ' + client.firstSurname);
            }, function (client) {
                Alerts.addErrorAlert('Se ha producido un error guardando el client');
            });
        }

        function updateClient() {
            Client.update(vm.client, function (client) {
                Alerts.addSuccessAlert('Se ha actualizado el cliente ' + client.name + ' ' + client.firstSurname);
                $state.go('clients');
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error actualizando el cliente ' + client.name + ' ' + client.firstSurname);
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