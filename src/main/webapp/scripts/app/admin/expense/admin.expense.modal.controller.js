/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 07/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ExpenseModalController', ExpenseModalController);

    ExpenseModalController.$inject = ['$uibModalInstance', 'expense', 'frequencies', '$scope'];

    function ExpenseModalController($uibModalInstance, expense, frequencies, $scope) {
        var vm = this;
        var isNew = expense === undefined;

        vm.frequencies = frequencies;

        vm.dismiss = dismiss;
        vm.saveExpense = saveExpense;

        activate();

        ///////////////////////////////

        function activate() {
            if(expense) {
                vm.title = 'Editar gasto';
                vm.expense = processExpense(expense);
            } else {
                vm.title = 'Guardar gasto';
                vm.expense = { expenseDate: new Date() };
            }
        }

        function saveExpense() {
            $scope.$broadcast('show-errors-check-validity');

            if($scope.expenseForm.$valid) {
                if(isNew) {
                    $uibModalInstance.close({
                        action: 'SAVE',
                        expense: vm.expense
                    });
                } else {
                    $uibModalInstance.close({
                        action: 'UPDATE',
                        expense: vm.expense
                    });
                }
            }
        }

        function processExpense(expense) {
            expense.expenseDate = new Date(expense.expenseDate);
            if(expense.endDate) {
                expense.endDate = new Date(expense.endDate);
            }
            return expense;
        }

        function dismiss() {
            $uibModalInstance.dismiss('CANCEL');
        }
    }

})();

