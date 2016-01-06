/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .controller('ProfessorSessionsController', ProfessorSessionsController);

    ProfessorSessionsController.$inject = ['Professor', '$stateParams', 'MonthlySession', 'Alerts', '$scope'];

    function ProfessorSessionsController(Professor, $stateParams, MonthlySession, Alerts, $scope) {
        var vm = this;

        vm.yearsList = [];

        vm.previousYear = previousYear;
        vm.nextYear = nextYear;
        vm.updateSessions = updateSessions;
        vm.initEditing = initEditing;
        vm.cancelEditing = cancelEditing;
        vm.saveSessions = saveSessions;

        var tempNumOfSessions;


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
                processSessions(responseData);
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            })
        }

        function initEditing(month) {
            tempNumOfSessions = month.numberOfSessions;
            month.editing = true;
        }

        function cancelEditing(month) {
            month.numberOfSessions = tempNumOfSessions;
            month.editing = undefined;
        }

        function saveSessions(monthNumber, sessions) {
            $scope.$broadcast('show-errors-check-validity');

            if($scope.sessionsForm.$invalid) {
                Alerts.addErrorAlert('El formulario contiene datos erróneos');
                return;
            } else {
                var sessions = {
                    professor: vm.professor,
                    month: monthNumber,
                    year: vm.year,
                    numberOfSessions: sessions.numberOfSessions
                };

                if (!sessions.persisted) {
                    MonthlySession.save(sessions, function (responseData, headers) {
                        sessions.editing = undefined;
                        sessions.persisted = true;
                        Alerts.addHeaderSuccessAlert(headers());
                    }, function (response) {
                        cancelEditing(sessions);
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                } else {
                    MonthlySession.update(sessions, function (responseData, headers) {
                        sessions.editing = undefined;
                        Alerts.addHeaderSuccessAlert(headers());
                    }, function (response) {
                        cancelEditing(sessions);
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                }
            }
        }

        function processSessions(responseData) {
            initMonths();
            responseData.forEach(function (sessions, index) {
                vm.months[index].numberOfSessions = sessions.numberOfSessions;
                vm.months[index].persisted = true;
            });
        }

        function initMonths() {
            vm.months = [];
            var date = new Date();
            for (var i = 0; i < 12; i++) {
                date.setMonth(i);
                vm.months.push({
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

