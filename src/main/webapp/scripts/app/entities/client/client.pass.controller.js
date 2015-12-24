/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientPassController', ClientPassController);

    ClientPassController.$inject = ['Pass', '$stateParams', '$uibModal', 'Alerts', '$scope'];

    function ClientPassController(Pass, $stateParams, $uibModal, Alerts, $scope) {
        var vm = this;

        vm.openModal = openModal;

        activate();

        function activate() {
            Pass.getByClient({clientId: $stateParams.id}, function (data) {
                vm.data = data;
                vm.displayData = [].concat(vm.data);
            });
        }

        function openModal(pass) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/entities/pass/pass.modal.html',
                controller: 'PassModalController',
                controllerAs: 'modal',
                resolve: {
                    params: function () {
                        return {
                            client: $scope.tabs.client,
                            pass: pass
                        };
                    }
                }
            });

            modalInstance.result.then(function (result) {
                switch (result.action) {
                    case 'SAVE':
                        createPass(result.pass);
                        break;
                    case 'UPDATE':
                        updatePass(result.pass, vm.data.indexOf(pass));
                        break;
                    default:
                        break;
                }
            });
        }

        function createPass(pass) {
            Pass.save(pass, function (data) {
                vm.data.push(data);
                Alerts.addSuccessAlert('Se ha creado el bono de ' + pass.client.name + ' ' + pass.client.firstSurname);
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error creando el bono');
            });
        }

        function updatePass(pass, index) {
            Pass.update(pass, function (data) {
                vm.data[index] = data;
                vm.displayData = [].concat(vm.data);
                Alerts.addSuccessAlert('Se ha actualizado el bono de ' + pass.client.name + ' ' + pass.client.firstSurname);
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error actualizando el bono');
            });
        }
    }
})();

