/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('PassTypeController', PassTypeController);

    PassTypeController.$inject = ['PassType', 'Alerts', '$scope', '$uibModal'];

    function PassTypeController(PassType, Alerts, $scope, $uibModal) {
        var vm = this;

        vm.data = [];
        vm.displayData = [].concat(vm.data);

        vm.openModal = openModal;
        vm.deletePassType = deletePassType;

        activate();

        ////////////////////////////

        function activate() {
            PassType.query({}, function (data) {
                vm.data = data;
            });
        }

        function openModal(passType) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/admin/passType/admin.passType.modal.html',
                size: 'sm',
                controller: 'PassTypeModalController',
                controllerAs: 'modal',
                resolve: {
                    passType: function () {
                        return passType;
                    }
                }
            });

            modalInstance.result.then(function (result) {
                switch (result.action) {
                    case 'SAVE':
                        createPassType(result.passType);
                        break;
                    case 'UPDATE':
                        updatePassType(result.passType);
                        break;
                    default:
                        break;
                }
            });
        }

        function createPassType(passType) {
            PassType.save(passType, function (data) {
                vm.data.push(data);
                Alerts.addSuccessAlert('Se ha creado el tipo de abono ' + data.activity.name + ' ' + data.numberOfSessions + ' sesiones.');
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error creando el tipo de abono');
            });
        }

        function updatePassType(passType) {
            var index = vm.data.indexOf(passType);
            PassType.update(passType, function (data) {
                vm.data[index] = data;
                Alerts.addSuccessAlert('Se ha actualizado el tipo de abono ' + data.activity.name + ' ' + data.numberOfSessions + ' sesiones.');
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error actualizando el tipo de abono');
            });
        }

        function deletePassType(passType) {

            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/components/modals/confirmModal.html',
                size: 'sm',
                controller: 'ConfirmModalController',
                controllerAs: 'modal',
                resolve: {
                    title: function () {
                        return 'Eliminar tipo de abono';
                    },
                    message: function () {
                        return 'Está seguro de que desea eliminar el abono ' + passType.activity.name + ' ' + passType.numberOfSessions + ' sesiones?';
                    }

                }
            });

            modalInstance.result.then(function (result) {
                if(result === 'OK') {
                    var index = vm.data.indexOf(passType);
                    PassType.delete({id: passType.id}, function () {
                        vm.data.splice(index, 1);
                        Alerts.addSuccessAlert('Se ha eliminado el tipo de abono ' + passType.activity.name + ' ' + passType.numberOfSessions + ' sesiones.');
                    }, function () {
                        Alerts.addErrorAlert('Se ha producido un error eliminando el tipo de abono');
                    });
                }
            });
        }
    }

})();

