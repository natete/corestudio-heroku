/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.group')
        .controller('GroupController', GroupController);

    GroupController.$inject = ['Alerts', '$uibModal', 'Config', 'Group']

    function GroupController(Alerts, $uibModal, Config, Group) {

        var vm = this;
        vm.displayData = [].concat(vm.data);

        vm.openModal = openModal;
        vm.deleteGroup = deleteGroup;

        activate();

        //////////////////

        function activate() {
            Config.query({}, function(data) {
                vm.config = data;
            });

            Group.query({}, function(data) {
                vm.data = data;
            })
        }

        function openModal(group) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/entities/group/group.modal.html',
                controller: 'GroupModalController',
                controllerAs: 'modal',
                resolve: {
                    group: function () {
                        return group;
                    },
                    config: function() {
                        return vm.config;
                    }
                }
            });

            modalInstance.result.then(function (result) {
                switch (result.action) {
                    case 'SAVE':
                        createGroup(result.group);
                        break;
                    case 'UPDATE':
                        updateGroup(result.group);
                        break;
                    default:
                        break;
                }
            });
        }

        function createGroup(group) {
            Group.save(group, function(data) {
                vm.data.push(data);
                Alerts.addSuccessAlert('Se ha creado el grupo ' + daysArrayToString(group.days) + ' a las ' + data.hour);
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error creando el grupo');
            });
        }

        function updateGroup(group) {
            var index = vm.data.indexOf(group);
            Group.update(group, function (data) {
                vm.data[index] = data;
                Alerts.addSuccessAlert('Se ha actualizado el grupo ' + daysArrayToString(group.days) + ' a las ' + data.hour)
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error actualizando el grupo');
            })
        }

        function deleteGroup(group) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/components/modals/confirmModal.html',
                size: 'sm',
                controller: 'ConfirmModalController',
                controllerAs: 'modal',
                resolve: {
                    title: function () {
                        return 'Eliminar grupo';
                    },
                    message: function () {
                        return 'Está seguro de que desea eliminar el grupo de ' + daysArrayToString(group.days) + ' a las ' + group.hour + '?';
                    }

                }
            });

            modalInstance.result.then(function (result) {
                if(result === 'OK') {
                    var index = vm.data.indexOf(group);
                    Group.delete({id: group.id}, function () {
                        vm.data.splice(index, 1);
                        Alerts.addSuccessAlert('Se ha eliminado el grupo de ' + daysArrayToString(group.days) + ' a las ' + group.hour + '?');
                    }, function () {
                        Alerts.addErrorAlert('Se ha producido un error eliminando el grupo');
                    });
                }
            });
        }

        function daysArrayToString(array) {
            var daysMapper = {
                MONDAY: 'lunes',
                TUESDAY: 'martes',
                WEDNESDAY: 'miércoles',
                THURSDAY: 'jueves',
                FRIDAY: 'viernes',
                SATURDAY: 'sábado',
                SUNDAY: 'domingo'
            };

            var translated = array.map(function(value) {
                return daysMapper[value];
            });

            return translated.join(', ');
        }
    }
})();

