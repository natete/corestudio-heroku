/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 24/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientCalendarController', ClientCalendarController);

    ClientCalendarController.$inject = ['Alerts', 'Pass', '$scope', 'Overlay', 'Holiday', 'Client', '$stateParams', '$uibModal', 'DATE_TYPES'];

    function ClientCalendarController(Alerts, Pass, $scope, Overlay, Holiday, Client, $stateParams, $uibModal, DATE_TYPES) {
        var vm = this;

        vm.yearsList = [];

        vm.previousYear = previousYear;
        vm.nextYear = nextYear;
        vm.updateCalendar = updateCalendar;
        vm.selectDate = selectDate;

        var passes;
        var holidays;

        activate();

        //////////////////////

        function activate() {
            vm.year = new Date().getFullYear();

            for (var i = 0; i < 20; i++) {
                vm.yearsList.push(vm.year - 10 + i);
            }

            Client.get({id: $stateParams.id}, function (data) {
                vm.client = data;
                updateCalendar(vm.year);
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updateCalendar(year) {
            Overlay.on();
            vm.passDates = [];
            Pass.getByClientAndYear({clientId: vm.client.id, year: year}, function (responseData) {
                if (responseData) {
                    passes = responseData;
                    processPasses(passes);
                }
                Holiday.query({year: year}, function (responseData) {
                    holidays = responseData;
                    addHolidays(holidays);
                    $scope.$broadcast('update-selected-dates', vm.passDates, year, Overlay.off);
                });
            });
        }

        function processPasses(passes) {
            passes.forEach(function (pass) {
                vm.passDates = vm.passDates.concat(processPass(pass));
            });
        }

        function addHolidays(holidays) {
            vm.passDates.push({
                type: DATE_TYPES.HOLIDAY,
                dates: holidays.map(function(holiday) {
                    holiday.type = DATE_TYPES.HOLIDAY;
                    return holiday;
                })
            });
        }

        function processPass(pass) {
            var selectedDates = [];
            var description = pass.passType.activity.name;
            if (pass.consumedDates && pass.consumedDates.length) {
                var consumedDates = {};
                consumedDates.type = DATE_TYPES.CONSUMED;
                consumedDates.dates = pass.consumedDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Consumida',
                        type: DATE_TYPES.CONSUMED,
                        groupDate: pass.passType.activity.groupActivity
                    }
                });
                selectedDates.push(consumedDates);
            }
            if (pass.pendingDates && pass.pendingDates.length) {
                var pendingDates = {};
                pendingDates.type = DATE_TYPES.PENDING;
                pendingDates.dates = pass.pendingDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Pendiente',
                        type: DATE_TYPES.PENDING
                    }
                });
                selectedDates.push(pendingDates);
            }
            if (pass.frozenDates && pass.frozenDates.length) {
                var frozenDates = {};
                frozenDates.type = DATE_TYPES.FROZEN;
                frozenDates.dates = pass.frozenDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Congelada',
                        type: DATE_TYPES.FROZEN
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

        function selectDate(date) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/entities/client/client.date.modal.html',
                controller: 'ClientModalDateController',
                controllerAs: 'modal',
                resolve: {
                    date: function () {
                        return date;
                    }
                }
            });

            modalInstance.result.then(function (action) {
                switch (action) {
                    case 'RELEASE':
                        releaseDate(date);
                        break;
                    case 'FREEZE':
                        freezeDate(date);
                        break;
                    case 'CONSUME':
                        consumeDate(date);
                        break;
                    default:
                        break;
                }
            });
        }

        function releaseDate(date) {
            Overlay.on();
            Pass.releaseDate({clientId: vm.client.id, date: date.date}, function (responseData, headers) {

                updatePass(responseData);

                $scope.$broadcast('update-selected-dates', vm.passDates, vm.year, Overlay.off);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Overlay.off();
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function freezeDate(date) {
            Overlay.on();
            Pass.freezeDate({clientId: vm.client.id, date: date.date}, function (responseData, headers) {

                updatePass(responseData);

                $scope.$broadcast('update-selected-dates', vm.passDates, vm.year, Overlay.off);

                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Overlay.off();
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function consumeDate(consumedDate) {
            Overlay.on();

            Pass.consumeDate({clientId: vm.client.id, date: consumedDate.date}, function (responseData, headers) {

                updatePass(responseData);

                $scope.$broadcast('update-selected-dates', vm.passDates, vm.year, Overlay.off);

                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Overlay.off();
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updatePass(pass) {
            var index = -1;
            passes.forEach(function(value, i) {
                if(pass.id === value.id) {
                    index = i;
                }
            });
            passes[index] = pass;
            vm.passDates = [];
            processPasses(passes);
            addHolidays(holidays);
        }
    }
})();

