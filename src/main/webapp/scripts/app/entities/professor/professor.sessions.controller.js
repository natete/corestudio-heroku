/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .controller('ProfessorSessionsController', ProfessorSessionsController);

    ProfessorSessionsController.$inject = ['Professor', '$stateParams', 'MonthlySession', 'Alerts'];

    function ProfessorSessionsController(Professor, $stateParams, MonthlySession, Alerts) {
        var vm = this;

        vm.yearsList = [];

        vm.previousYear = previousYear;
        vm.nextYear = nextYear;
        vm.updateSessions = updateSessions;


        activate();

        function activate() {
            vm.year = new Date().getFullYear();

            for (var i = 0; i < 20; i++) {
                vm.yearsList.push(vm.year - 10 + i);
            }

            Professor.get({id: $stateParams.id}, function (data) {
                vm.professor = data;
                updateSessions(vm.year);
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updateSessions(year) {
            MonthlySession.query({professorId: vm.professor.id, year: year}, function (responseData, headers) {
                vm.months = processSessions(responseData);
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
            })
        }

        function processSessions(responseData) {
            initMonths();
            responseData.forEach(function(sessions, index) {
                vm.months[index].numberOfSessions = sessions.numberOfSessions;
            });
            return sessions;
        }

        function initMonths() {
            vm.months = [];
            var date = new Date();
            for (var i = 0; i < 12; i++) {
                date.setMonth(i);
                months.push({
                    name: date.getMonthName(),
                    numberOfSessions: 0
                });
            }
        }

        function previousYear() {
            vm.year--;
            updateSessions(vm.year);
        }

        function nextYear() {
            vm.year++;
            updateSessions(vm.year);
        }
    }
})();

