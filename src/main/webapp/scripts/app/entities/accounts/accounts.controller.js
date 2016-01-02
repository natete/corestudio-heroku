/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.accounts')
        .controller('AccountsController', AccountsController);

    AccountsController.$inject = ['Accounts', '$scope'];

    function AccountsController(Accounts, $scope) {

        var vm = this;

        vm.getTotalSessions = getTotalSessions;
        vm.getTotalAmount = getTotalAmount;
        vm.getAccounts = getAccounts;
        vm.previousMonth = previousMonth;
        vm.nextMonth = nextMonth;

        activate();

        ///////////////////

        function activate() {
            vm.selectedDate = new Date();

            vm.getAccounts();
        }

        function getAccounts() {
            Accounts.query({
                year: vm.selectedDate.getFullYear(),
                month: vm.selectedDate.getMonth() + 1
            }, function (responseData) {
                vm.accounts = responseData;
            }, function (responseData) {

            });
        }

        function getTotalSessions() {
            var total = 0;

            if(vm.accounts) {
                vm.accounts.forEach(function(account) {
                    total += account.totalSessions;
                });
            }

            return total;
        }

        function getTotalAmount() {
            var total = 0;

            if(vm.accounts) {
                vm.accounts.forEach(function(account) {
                    total += account.totalIncomes;
                });
            }

            return total;
        }

        function previousMonth() {
            vm.selectedDate = getDateWithOffset(vm.selectedDate, -1);
            vm.getAccounts();
        }

        function nextMonth() {
            vm.selectedDate = getDateWithOffset(vm.selectedDate, 1);
            vm.getAccounts();
        }

        function getDateWithOffset(date, offset) {
            var newDate = new Date();
            newDate.setMonth(date.getMonth() + offset);
            return newDate;
        }
    }
})();

