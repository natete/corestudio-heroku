/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientPassController', ClientPassController);

    ClientPassController.$inject = ['Pass', '$stateParams', '$uibModal', 'Alerts', 'Client'];

    function ClientPassController(Pass, $stateParams, $uibModal, Alerts, Client) {
        var vm = this;

        vm.search = search;
        vm.openModal = openModal;
        vm.getPendingSessions = getPendingSessions;

        activate();

        function activate() {
            Client.get({id: $stateParams.id}, function(data) {
                vm.client = data;
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function search(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? (pagination.start + 1) % pagination.number : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            pageRequest.clientId = $stateParams.id;
            Pass.getByClient(pageRequest, function (responseData) {
                vm.data = responseData.content;
                tableState.pagination.numberOfPages = responseData.totalPages;
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
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
                            client: vm.client,
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
            Pass.save(pass, function (data, headers) {
                vm.data.push(data);
                vm.displayData = [].concat(vm.data);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updatePass(pass, index) {
            Pass.update(pass, function (data, headers) {
                vm.data[index] = data;
                vm.displayData = [].concat(vm.data);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function getPendingSessions(pass) {
            return pass.passType.numberOfSessions - (pass.consumedDates ? pass.consumedDates.length : 0);
        }
    }
})();

