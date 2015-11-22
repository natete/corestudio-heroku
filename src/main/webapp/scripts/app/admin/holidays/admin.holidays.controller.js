/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('HolidaysController', HolidaysController);

    HolidaysController.$inject = ['$uibModal'];

    function HolidaysController($uibModal) {
        var vm = this;
        vm.yearsList = [];
        vm.selectDate = selectDate;
        vm.selectedDate = new Date();
        vm.getHolidaysList = getHolidaysList;
        vm.holidaysList;

        activate();

        //////////////

        function activate() {
            var currentYear = new Date().getFullYear();
            vm.year = currentYear;
            var firstYear = currentYear - 10;

            for (var i = 0; i < 20; i++) {
                vm.yearsList.push(firstYear + i);
            }

            vm.getHolidaysList(vm.year);
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

            modalInstance.result.then(function(needReload) {

            });
        }

        function getHolidaysList(year) {

        }
    }
})();

