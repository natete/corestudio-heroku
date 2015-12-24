/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 24/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientCalendarController', ClientCalendarController);

    ClientCalendarController.$inject = ['Alerts', 'Pass', '$scope', 'Overlay', 'Holiday'];

    function ClientCalendarController(Alerts, Pass, $scope, Overlay, Holiday) {
        var vm = this;

        vm.yearsList = [];
        vm.passDates = [];

        vm.previousYear = previousYear;
        vm.nextYear = nextYear;
        vm.updateCalendar = updateCalendar;

        activate();

        //////////////////////

        function activate() {
            vm.year = new Date().getFullYear();

            updateCalendar(vm.year);

            for (var i = 0; i < 20; i++) {
                vm.yearsList.push(vm.year - 10 + i);
            }
        }

        function updateCalendar(year) {
            Overlay.on();
            Pass.getByClientAndYear({clientId: $scope.tabs.client.id, year: year}, function(data) {
                if(data) {
                    data.forEach(function(pass) {
                        vm.passDates = vm.passDates.concat(processPass(pass));
                    });
                }
                Holiday.query({id: year}, function(holidays) {
                    vm.passDates.push({
                        type: 'holiday',
                        dates: holidays
                    });
                    $scope.$broadcast('update-selected-dates', vm.passDates, year, Overlay.off);
                });
            });
        }

        function processPass(pass) {
            var selectedDates = [];
            var description = pass.passType.activity.name;
            if(pass.consumedDates && pass.consumedDates.length) {
                var consumedDates = {};
                consumedDates.type = 'consumed-date';
                consumedDates.dates = pass.consumedDates.map(function(date) {
                    return {
                        date: date,
                        description: description
                    }
                });
                selectedDates.push(consumedDates);
            }
            if(pass.pendingDates && pass.pendingDates.length) {
                var pendingDates = {};
                pendingDates.type = 'pending-date';
                pendingDates.dates = pass.pendingDates.map(function(date) {
                    return {
                        date: date,
                        description: description
                    }
                });
                selectedDates.push(pendingDates);
            }
            if(pass.frozenDates && pass.frozenDates.length) {
                var frozenDates = {};
                frozenDates.type = 'frozen-date';
                frozenDates.dates = pass.frozenDates.map(function(date) {
                    return {
                        date: date,
                        description: description
                    }
                });
                selectedDates.push(frozenDates);
            }
            return selectedDates;
        }

        function previousYear() {
            vm.year--;
            updateCalendar(vm.year);
        }

        function nextYear() {
            vm.year++;
            updateCalendar(vm.year);
        }
    }

})();

