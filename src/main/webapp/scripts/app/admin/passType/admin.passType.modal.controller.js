/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('PassTypeModalController', PassTypeModalController);

    PassTypeModalController.$inject = ['$uibModalInstance', 'passType', 'Activity', '$scope'];

    function PassTypeModalController($uibModalInstance, passType, Activity, $scope) {
        var vm = this;

        vm.dismiss = dismiss;
        vm.savePassType = savePassType;

        activate();

        function activate() {
            if(passType) {
                vm.title = 'Editar tipo de abono';
                vm.passType = passType;
            } else {
                vm.title = 'Nuevo tipo de abono';
                vm.passType = {};
            }


            Activity.query({}, function (response) {
                vm.activities = response.content;
                if(vm.passType.activity) {
                    var index = -1;

                    for (var i = 0; i < vm.activities.length; i++) {
                        if(vm.activities[i].activity.id === vm.passType.activity.id) {
                            index = i;
                            break;
                        }
                    }

                    vm.passType.activity = vm.activities[index].activity;
                }
            });
        }

        function dismiss() {
            $uibModalInstance.dismiss('CANCEL');
        }

        function savePassType() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.passTypeForm.$valid) {
                if(vm.passType.id) {
                    $uibModalInstance.close({action: 'UPDATE', passType: vm.passType});
                } else {
                    $uibModalInstance.close({action: 'SAVE', passType: vm.passType});
                }
            }
        }
    }

})();

