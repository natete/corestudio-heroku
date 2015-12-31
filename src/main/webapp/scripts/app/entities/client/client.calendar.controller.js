/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 24/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientCalendarController', ClientCalendarController);

    ClientCalendarController.$inject = ['Alerts', 'Pass', '$scope', 'Overlay', 'Holiday', 'Client', '$stateParams', '$uibModal', 'dateFilter'];

    function ClientCalendarController(Alerts, Pass, $scope, Overlay, Holiday, Client, $stateParams, $uibModal, dateFilter) {
        var vm = this;

        vm.yearsList = [];

        vm.previousYear = previousYear;
        vm.nextYear = nextYear;
        vm.updateCalendar = updateCalendar;
        vm.selectDate = selectDate;

        var dateTypes = {
            PENDING: 'pending-date',
            FROZEN: 'frozen-date',
            CONSUMED: 'consumed-date',
            HOLIDAY: 'holiday'
        };

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
            }, function () {
                Alerts.addErrorAlert("El cliente solicitado no existe");
            });
        }

        function updateCalendar(year) {
            Overlay.on();
            vm.passDates = [];
            Pass.getByClientAndYear({clientId: vm.client.id, year: year}, function (data) {
                if (data) {
                    data.forEach(function (pass) {
                        vm.passDates = vm.passDates.concat(processPass(pass));
                    });
                }
                Holiday.query({id: year}, function (holidays) {
                    vm.passDates.push({
                        type: dateTypes.HOLIDAY,
                        dates: holidays
                    });
                    $scope.$broadcast('update-selected-dates', vm.passDates, year, Overlay.off);
                });
            });
        }

        function processPass(pass) {
            var selectedDates = [];
            var description = pass.passType.activity.name;
            if (pass.consumedDates && pass.consumedDates.length) {
                var consumedDates = {};
                consumedDates.type = dateTypes.CONSUMED;
                consumedDates.dates = pass.consumedDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Consumida'
                    }
                });
                selectedDates.push(consumedDates);
            }
            if (pass.pendingDates && pass.pendingDates.length) {
                var pendingDates = {};
                pendingDates.type = dateTypes.PENDING;
                pendingDates.dates = pass.pendingDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Pendiente'
                    }
                });
                selectedDates.push(pendingDates);
            }
            if (pass.frozenDates && pass.frozenDates.length) {
                var frozenDates = {};
                frozenDates.type = dateTypes.FROZEN;
                frozenDates.dates = pass.frozenDates.map(function (date) {
                    return {
                        date: date,
                        description: description + ' - Congelada'
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

        }

        function freezeDate(date) {
            Overlay.on();
            Pass.freezeDate({clientId: vm.client.id, date: date.date}, function (data) {
                // Update dates
                removeDate(date.date, date.type);
                addDate(new Date(data.lastDate), data.passType.activity.name + ' Pendiente', dateTypes.PENDING);
                addDate(date.date, data.passType.activity.name + ' - Congelada', dateTypes.FROZEN);

                $scope.$broadcast('update-selected-dates', vm.passDates, vm.year, Overlay.off);

                Alerts.addSuccessAlert('Se ha congelado el día ' + dateFilter(date.date) + ' de ' + vm.client.name);
            }, function () {
                Overlay.off();
                Alerts.addErrorAlert('Ha ocurrido un error y no se ha podido congelar la fecha seleccionada');
            });
        }

        function consumeDate(consumedDate) {
            Overlay.on();

            Pass.consumeDate({clientId: vm.client.id, date: consumedDate.date}, function (data) {

                removeDate(consumedDate.date, consumedDate.type);
                addDate(consumedDate.date, data.passType.activity.name + ' - Consumida', dateTypes.CONSUMED);
                if(data.lastDate) {
                    updateLastDate(new Date(data.lastDate));
                }

                $scope.$broadcast('update-selected-dates', vm.passDates, vm.year, Overlay.off);

                Alerts.addSuccessAlert('Se ha consumido el día ' + dateFilter(consumedDate.date) + ' de ' + vm.client.name);
            }, function (data) {
                Overlay.off();
                Alerts.addHeaderErrorAlert(data);
            });
        }

        function addDate(date, description, dateType) {
            var found = false;
            vm.passDates.forEach(function (pass) {
                if (pass.type === dateType) {
                    pass.dates.push({
                        date: date,
                        description: description
                    });
                    found = true;
                }
            });
            if (!found) {
                vm.passDates.push({
                    dates: [{
                        date: date,
                        description: description
                    }],
                    type: dateType
                });
            }
        }

        function removeDate(targetDate, dateType) {
            vm.passDates.forEach(function (pass) {
                if (pass.type === dateType) {
                    var target = pass.dates.filter(function (date) {
                        if (date.date.isSameDay(targetDate)) {
                            return date;
                        }
                    });
                    pass.dates.splice(pass.dates.indexOf(target[0]), 1);
                }
            });
        }

        function updateLastDate(lastDate) {
            vm.passDates.forEach(function (pass) {
                if (pass.type === dateTypes.PENDING) {
                    if (pass.dates.length && !lastDate.isSameDay(pass.dates[pass.dates.length - 1].date)) {
                        pass.dates.pop();
                    }
                }
            });
        }
    }
})();

