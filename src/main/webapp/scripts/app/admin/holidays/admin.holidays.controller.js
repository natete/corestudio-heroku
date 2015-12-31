/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('HolidaysController', HolidaysController);

    HolidaysController.$inject = ['$uibModal', '$filter', '$scope', 'Holiday', 'Alerts', 'Overlay'];

    function HolidaysController($uibModal, $filter, $scope, Holiday, Alerts, Overlay) {
        var vm = this;
        vm.yearsList = [];
        vm.selectDate = selectDate;
        vm.updateHolidaysList = updateHolidaysList;
        vm.holidaysList = [];
        vm.addDate = addDate;
        vm.previousYear = previousYear;
        vm.nextYear = nextYear;

        activate();

        //////////////

        function activate() {
            var currentYear = new Date().getFullYear();
            vm.year = currentYear;
            var firstYear = currentYear - 10;

            for (var i = 0; i < 20; i++) {
                vm.yearsList.push(firstYear + i);
            }

            vm.updateHolidaysList(vm.year);
        }

        function selectDate(date) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/app/admin/holidays/admin.holidays.modal.html',
                controller: 'HolidaysModalController',
                controllerAs: 'modal',
                resolve: {
                    date: function () {
                        return date;
                    }
                }
            });

            modalInstance.result.then(function (action) {
                switch (action) {
                    case 'save':
                        addDate(date);
                        break;
                    case 'update':
                        updateDate(date);
                        break;
                    case 'delete':
                        deleteDate(date);
                        break;
                    default:
                        break;
                }
            });
        }

        function updateHolidaysList(year) {
            Overlay.on();
            Holiday.query({id: year}, function (holidays) {
                vm.holidaysList = [{
                    type: 'holiday',
                    dates: holidays
                }];
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year, Overlay.off);
            }, function (data) {
                Overlay.off();
                Alerts.addErrorAlert('Error accediendo a la lista de festivos');
            });
        }

        function addDate(date) {
            Holiday.save(date, function (holiday, headers) {
                holiday.type = 'holiday';
                vm.holidaysList[0].dates.push(holiday);
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year);
                Alerts.addSuccessAlert('Se ha añadido el festivo ' + $filter('date')(holiday.date, 'dd/MM/yyyy'));
            }, function () {
                Alerts.addErrorAlert('Ha habido un problema añandiendo el festivo ' + $filter('date')(date.date, 'dd/MM/yyyy'));
            });
        }

        function updateDate(date) {
            Holiday.update(date, function (holiday) {
                var updated = $filter('filter')(vm.holidaysList[0].dates, {id: holiday.id}, true)[0];
                updated.description = holiday.description;
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year);
                Alerts.addSuccessAlert('Se ha actualizado el festivo ' + $filter('date')(holiday.date, 'dd/MM/yyyy'));
            }, function () {
                Alerts.addErrorAlert('Ha habido un problema actualizando el festivo ' + $filter('date')(date.date, 'dd/MM/yyyy'));
            });
        }

        function deleteDate(date) {
            Holiday.delete({id: date.id}, function () {
                var deleted = $filter('filter')(vm.holidaysList[0].dates, {id: date.id}, true)[0];
                var index = vm.holidaysList[0].dates.indexOf(deleted);
                vm.holidaysList[0].dates.splice(index, 1);
                $scope.$broadcast('deleted-date', deleted);
                Alerts.addSuccessAlert('Se ha eliminado el festivo ' + $filter('date')(date.date, 'dd/MM/yyyy'));
            }, function () {
                Alerts.addErrorAlert('Ha ocurrido un error eliminando el festivo ' + $filter('date')(date.date, 'dd/MM/yyyy'));
            });
        }

        function previousYear() {
            vm.year--;
            vm.updateHolidaysList(vm.year);
        }

        function nextYear() {
            vm.year++;
            vm.updateHolidaysList(vm.year);
        }
    }
})();

