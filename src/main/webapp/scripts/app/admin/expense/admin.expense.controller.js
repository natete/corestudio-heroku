/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ExpenseController', ExpenseController);

    ExpenseController.$inject = ['Expense', 'Alerts', '$uibModal', 'Config'];

    function ExpenseController(Expense, Alerts, $uibModal, Config) {
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

            Config.query({}, function(data) {
               vm.config = data;
            });
        }

        function processExpenses(expenses) {
            expenses.forEach(function(expense) {
                if(expense.frequency === 'EXCEPTIONAL') {
                    vm.expenses.push(processExpense(expense));
                } else {
                    vm.regularExpenses.push(processExpense(expense));
                }
            });
        }

        function processExpense(expense) {
            expense.expenseDate = new Date(expense.expenseDate);
            if(expense.endDate) {
                expense.endDate = new Date(expense.endDate);
            }
            return expense;
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
                    },
                    frequencies: function() {
                        return vm.config.frequencies;
                    }
                }
            });

            modalInstance.result.then(function(result) {
                switch (result.action) {
                    case 'SAVE':
                        saveExpense(result.expense);
                        break;
                    case 'UPDATE':
                        updateExpense(result.expense);
                        break;
                    default:
                        break;
                }
            });
        }

        function saveExpense(expense) {
            Expense.save(expense, function(responseData, headers) {
                if(expense.frequency === 'EXCEPTIONAL') {
                    vm.expenses.push(expense);
                } else {
                    vm.regularExpenses.push(expense);
                }
                Alerts.addHeaderSuccessAlert(headers());
            }, function(response) {
               Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function updateExpense(expense) {
            Expense.update(expense, function(responseData, headers) {

                Alerts.addHeaderSuccessAlert(headers());
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }

    }
})();

