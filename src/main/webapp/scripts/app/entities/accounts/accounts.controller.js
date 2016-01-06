/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.accounts')
        .controller('AccountsController', AccountsController);

    AccountsController.$inject = ['Accounts', 'Alerts'];

    function AccountsController(Accounts, Alerts) {

        var vm = this;

        vm.SALARY_PER_SESSION = 16;

        vm.getTotalIncomesSessions = getTotalIncomesSessions;
        vm.getTotalIncomesAmount = getTotalIncomesAmount;
        vm.getAccounts = getAccounts;
        vm.getTotalExpenses = getTotalExpenses;
        vm.getTotalSalariesAmount = getTotalSalariesAmount;
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
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function getTotalIncomesSessions() {
            var total = 0;

            if(vm.accounts && vm.accounts.incomes) {
                vm.accounts.incomes.forEach(function(account) {
                    total += account.totalSessions;
                });
            }

            return total;
        }

        function getTotalIncomesAmount() {
            var total = 0;

            if(vm.accounts && vm.accounts.incomes) {
                vm.accounts.incomes.forEach(function(account) {
                    total += account.totalIncomes;
                });
            }

            return total / 100;
        }

        function getTotalExpenses() {
            var total = 0;

            total += vm.getTotalSalariesAmount();

            return total;
        }

        function getTotalSalariesAmount() {
            var total = 0;

            if(vm.accounts && vm.accounts.salaries) {
                vm.accounts.salaries.forEach(function(salary) {
                   total += salary.numberOfSessions * vm.SALARY_PER_SESSION;
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
            newDate.setFullYear(date.getFullYear());
            newDate.setMonth(date.getMonth() + offset);
            return newDate;
        }
    }
})();

