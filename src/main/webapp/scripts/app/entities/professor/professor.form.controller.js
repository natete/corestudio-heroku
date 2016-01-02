/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .controller('ProfessorFormController', ProfessorFormController);

    ProfessorFormController.$inject = ['$stateParams', '$state', 'Professor', 'Alerts', '$scope'];

    function ProfessorFormController($stateParams, $state, Professor, Alerts, $scope) {
        var vm = this;
        vm.birthdateDatepicker = {
            opened: false
        };
        vm.admissionDateDatepicker = {
            opened: false
        };
        vm.saveBtnText = 'Guardar';

        vm.processSaveProfessor = processSaveProfessor;

        activate();

        //////////////////////

        function activate() {
            if ($state.is('professors.newProfessor')) {
                vm.area = 'Nuevo profesor';
                vm.classArea = 'fa-plus'
            } else if ($state.is('professors.editProfessor')) {
                vm.area = 'Editar profesor';
                vm.classArea = 'fa-pencil';
                Professor.get({id: $stateParams.id}, function (responseData) {
                    vm.professor = responseData;
                }, function (responseData) {
                    Alerts.addHeaderErrorAlert(responseData);
                });
            }
        }

        function processSaveProfessor() {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.professorForm.$invalid) {
                Alerts.addErrorAlert("El formulario contiene datos erróneos")
            } else {
                if ($state.is('professors.newProfessor')) {
                    Professor.save(vm.professor, function(responseData) {
                        $state.go('professors');
                        Alerts.addHeaderSuccessAlert(responseData.headers());
                    }, function(responseData) {
                        Alerts.addHeaderErrorAlert(responseData);
                    });
                }
            }
        }
    }

})();

