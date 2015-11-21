/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    ClientFormController.$inject = ['Client', 'editMode', '$stateParams', '$scope', '$state'];

    function ClientFormController(Client, editMode, $stateParams, $scope, $state) {
        var vm = this;

        vm.editMode = editMode;
        vm.client = {};
        vm.birthdateDatepicker = {
            opened: false
        };
        vm.admissionDateDatepicker = {
            opened: false
        };
        vm.saveDisabled = false;
        vm.saveBtnText = 'Guardar';

        vm.saveClient = saveClient;

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
                vm.saveDisabled = true;
                vm.saveBtnText = 'Guardando...';
                var result = Client.save(this.client);
                result.$promise.then(function() {
                    $state.go('clients');
                });
            }
        }

    }
})();