/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    ClientFormController.$inject = ['Client', 'editMode', '$stateParams', '$scope'];

    function ClientFormController(Client, editMode, $stateParams, $scope) {
        var vm = this;

        vm.editMode = editMode;
        vm.client = {};
        vm.birthdateDatepicker = {
            opened: false
        };
        vm.admissionDateDatepicker = {
            opened: false
        };

        vm.saveClient = saveClient;
        vm.openDatepicker = openDatepicker;

        activate();

        ////////
        function activate() {
            if($stateParams.id !== undefined) {
                var result = Client.get({ id: $stateParams.id });
                result.$promise.then(function(client) {
                    vm.client = client;
                });
            }
        }

        function saveClient() {
            $scope.$broadcast('show-errors-check-validity');

            if($scope.userForm.$invalid) {
                return;
            } else {
                Client.save(this.client);
            }
        }

        function openDatepicker(datepickerStatus) {
            datepickerStatus = true;
        }

    }
})();