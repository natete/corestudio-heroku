/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ExpenseController', ExpenseController);

    ExpenseController.$inject = ['Expense', 'Alerts', '$uibModal', 'Config', 'Overlay'];

    function ExpenseController(Expense, Alerts, $uibModal, Config, Overlay) {
        var vm = this;

        vm.regularExpenses = [];
        vm.expenses = [];

        vm.searchExceptional = searchExceptional;
        vm.searchRegular = searchRegular;
        vm.openModal = openModal;



        activate();

        function activate() {
            Config.query({}, function(data) {
               vm.config = data;
            });
        }

        function searchRegular(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? (pagination.start + 1) % pagination.number : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            pageRequest.frequency = 'REGULAR';
            Overlay.on();
            Expense.findByFrequency(pageRequest, function(responseData) {
                vm.regularExpenses = responseData.content;
                tableState.pagination.numberOfPages = responseData.totalPages;
                Overlay.off();
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
                Overlay.off();
            });
        }

        function searchExceptional(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? (pagination.start + 1) % pagination.number : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            pageRequest.frequency = 'EXCEPTIONAL';
            Overlay.on();
            Expense.findByFrequency(pageRequest, function(responseData) {
                vm.expenses = responseData.content;
                tableState.pagination.numberOfPages = responseData.totalPages;
                Overlay.off();
            }, function(response) {
                Alerts.addHeaderErrorAlert(response.headers());
                Overlay.off();
            });
        }

        function processExpenses(expenses) {
            vm.expenses = [];
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

