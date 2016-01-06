/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 23/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.pass')
        .controller('PassModalController', PassModalController);

    PassModalController.$inject = ['$uibModalInstance', 'params', 'PassType', 'Group', '$scope'];

    function PassModalController($uibModalInstance, params, PassType, Group, $scope) {
        var vm = this;

        vm.paymentDateDatepicker = {opened: false};
        vm.initialDateDatepicker = {opened: false};

        vm.dismiss = dismiss;
        vm.updateGroups = updateGroups;
        vm.savePass = savePass;

        activate();

        ///////////////////////

        function activate() {
            PassType.query({}, function (data) {
                vm.passTypes = data;
                if(params.pass) {
                    vm.pass = params.pass;
                    vm.updateGroups();
                    vm.pass.initialDate = new Date(vm.pass.initialDate);
                    vm.pass.paymentDate = new Date(vm.pass.paymentDate);
                } else {
                    vm.pass = {};
                    vm.pass.paymentDate = new Date();
                    vm.pass.initialDate = new Date();
                    vm.pass.client = params.client;
                    vm.title = vm.pass.client.name + ' ' + vm.pass.client.firstSurname;
                }
            });
        }

        function updateGroups() {
            Group.getGroupsByActivity({activityId: vm.pass.passType.activity.id}, function (data) {
                vm.groups = data;
                if(vm.pass.group) {
                    vm.pass.group = vm.groups[vm.groups.indexOfId(vm.pass.group.id)];
                }
            });

            vm.pass.price = vm.pass.passType.basePrice;
        }

        function savePass() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.passForm.$valid) {
                if (vm.pass.id) {
                    $uibModalInstance.close({action: 'UPDATE', pass: vm.pass});
                } else {
                    $uibModalInstance.close({action: 'SAVE', pass: vm.pass});
                }
            }
        }

        function dismiss() {
            $uibModalInstance.dismiss('CANCEL');
        }
    }

})();

