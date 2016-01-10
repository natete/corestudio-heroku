/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .controller('ProfessorTabsController', ProfessorTabsController);

    ProfessorTabsController.$inject = ['Professor', '$stateParams', '$state', '$scope'];

    function ProfessorTabsController(Professor, $stateParams, $state, $scope) {
        var vm = this;

        vm.go = go;

        activate();


        ///////////////////////

        function activate() {

            if ($stateParams.id !== undefined) {
                if ($stateParams.client === undefined) {
                    Professor.get({id: $stateParams.id}, function (professor) {
                        vm.professor = parseDates(professor);
                    }, function (response) {
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                } else {
                    vm.professor = $stateParams.professor;
                }
            }

            vm.tabs = [
                { title: 'Personal', route: 'professors.viewProfessor.personal', active: false },
                { title: 'Sesiones', route: 'professors.viewProfessor.sessions', active: false }
            ]
        }

        function go(route) {
            $state.go(route);
        }

        $scope.$on("$stateChangeSuccess", function() {
            vm.tabs.forEach(function(tab) {
                tab.active = active(tab.route);
            });
        });

        function active(route){
            return $state.is(route);
        }

        function parseDates(professor) {
            if (professor.birthdate) {
                professor.birthdate = new Date(professor.birthdate);
            }
            if (professor.admissionDate) {
                professor.admissionDate = new Date(professor.admissionDate);
            }
            return professor;
        }
    }

})();

