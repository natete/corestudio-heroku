/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.accounts')
        .controller('AccountsController', AccountsController);

    AccountsController.$inject = ['Accounts'];

    function AccountsController(Accounts) {

        var vm = this;

        vm.getTotalActivity = getTotalActivity;
        vm.getTotalAccount = getTotalAccount;
        vm.getTotalSessions = getTotalSessions;
        vm.getTotalAmount = getTotalAmount;

        activate();

        ///////////////////

        function activate() {
            vm.selectedDate = new Date();

            Accounts.query({
                year: vm.selectedDate.getFullYear(),
                month: vm.selectedDate.getMonth() + 1
            }, function (responseData) {
                vm.accounts = responseData;
            }, function (responseData) {

            });


        }

        function getTotalActivity(activity) {
            var total = 0;
            for (var passType in activity) {
                total += activity[passType].incomes;
            }
            return total / 100;
        }

        function getTotalAccount(account) {
            var total = 0;
            for (var activity in account) {
                total += getTotalActivity(account[activity].map);
            }
            return total;
        }

        function getTotalSessions() {
            var total = 0;

            if(vm.accounts) {
                vm.accounts.forEach(function(account) {
                    total += getTotalAccountSessions(account.map);
                });
            }

            return total;
        }

        function getTotalAccountSessions(account) {
            var total = 0;

            for(var activity in account) {
                total += getTotalActivitySessions(account[activity].map);
            }

            return total;
        }

        function getTotalActivitySessions(activity) {
            var total = 0;

            for (var passType in activity) {
                total += activity[passType].numberOfSessions;
            }

            return total;
        }

        function getTotalAmount() {
            var total = 0;

            if(vm.accounts) {
                vm.accounts.forEach(function(account) {
                    total += getTotalAccount(account.map);
                });
            }

            return total;
        }
    }
})();

