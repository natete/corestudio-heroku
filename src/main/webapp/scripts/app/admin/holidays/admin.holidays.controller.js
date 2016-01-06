/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('HolidaysController', HolidaysController);

    HolidaysController.$inject = ['$uibModal', '$filter', '$scope', 'Holiday', 'Alerts', 'Overlay', 'DATE_TYPES'];

    function HolidaysController($uibModal, $filter, $scope, Holiday, Alerts, Overlay, DATE_TYPES) {
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
            Holiday.query({year: year}, function (holidays) {
                vm.holidaysList = [{
                    type: 'holiday',
                    dates: holidays.map(function(holiday) {
                        holiday.type = DATE_TYPES.HOLIDAY;
                        return holiday;
                    })
                }];
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year, Overlay.off);
            }, function () {
                Overlay.off();
                Alerts.addErrorAlert('Error accediendo a la lista de festivos');
            });
        }

        function addDate(date) {
            Holiday.save(date, function (holiday, headers) {
                holiday.type = 'holiday';
                vm.holidaysList[0].dates.push(holiday);
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updateDate(date) {
            Holiday.update(date, function (holiday, headers) {
                var updated = $filter('filter')(vm.holidaysList[0].dates, {id: holiday.id}, true)[0];
                updated.description = holiday.description;
                $scope.$broadcast('update-selected-dates', vm.holidaysList, vm.year);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function deleteDate(date) {
            Holiday.delete({id: date.id}, function (data, headers) {
                var deleted = $filter('filter')(vm.holidaysList[0].dates, {id: date.id}, true)[0];
                var index = vm.holidaysList[0].dates.indexOf(deleted);
                vm.holidaysList[0].dates.splice(index, 1);
                $scope.$broadcast('deleted-date', deleted);
                Alerts.addHeaderSuccessAlert(headers());
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
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

