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
        vm.openModal = openModal;
        vm.displayData = [].concat(vm.data);

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
                    case 'save':
                        createGroup(result.group);
                        break;
                    case 'update':
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
                Alerts.addSuccessAlert('Se ha creado el grupo ' + data.days + ' - ' + data.hour);
            }, function () {
                Alerts.addErrorAlert('Se ha producido un error creando el grupo');
            });
        }

        function updateGroup(group) {

        }
    }
})();

