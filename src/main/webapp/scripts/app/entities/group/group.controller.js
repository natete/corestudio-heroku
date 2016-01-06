/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.group')
        .controller('GroupController', GroupController);

    GroupController.$inject = ['Alerts', '$uibModal', 'Config', 'Group', 'translateDaysFilter'];

    function GroupController(Alerts, $uibModal, Config, Group, translateDaysFilter) {

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
                        return angular.copy(group);
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
            Group.save(group, function(data, headers) {
                vm.data.push(data);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updateGroup(group) {
            var index = vm.data.indexOfId(group.id);
            Group.update(group, function (data, headers) {
                vm.data[index] = data;
                vm.displayData[index] = data;
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
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
                        return 'Está seguro de que desea eliminar el grupo de ' + translateDaysFilter(group.days) + ' a las ' + group.hour + '?';
                    }

                }
            });

            modalInstance.result.then(function (result) {
                if(result === 'OK') {
                    var index = vm.data.indexOfId(group.id);
                    Group.delete({id: group.id}, function (data, headers) {
                        vm.data.splice(index, 1);
                        Alerts.addHeaderSuccessAlert(headers());
                    }, function (response) {
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                }
            });
        }
    }
})();

