/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ExpenseController', ExpenseController);

    ExpenseController.$inject = ['Expense', 'Alerts', '$uibModal'];

    function ExpenseController(Expense, Alerts, $uibModal) {
        var vm = this;

        vm.regularExpenses = [];
        vm.expenses = [];

        vm.openModal = openModal;



        activate();

        function activate() {
            Expense.query({}, function(responseData) {
                processExpenses(responseData);
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function processExpensees(expenses) {
            expenses.forEach(function(expense) {
                if(expense.frequency === 'EXCEPTIONAL') {
                    vm.expenses.push(expense);
                } else {
                    vm.regularExpenses.push(expense);
                }
            });
        }

        function openModal(expense) {
            var modalInstance = $uibModal.open({
               templateUrl: 'scripts/app/admin/expense/admin.expense.modal.html',
                size: 'sm',
                controller: 'ExpenseModalController',
                controllerAs: 'modal',
                resolve: {
                    expense: function() {
                        return angular.copy(expense);
                    }
                }
            });

            modalInstance.result.then(function(result) {
                switch (result.action) {
                    case 'SAVE':
                        break;
                    case 'UPDATE':
                        break;
                    default:
                        break;
                }
            })
        }
    }
})();

