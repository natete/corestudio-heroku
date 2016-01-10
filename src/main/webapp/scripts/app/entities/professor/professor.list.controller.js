/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.professor')
        .controller('ProfessorsListController', ProfessorsListController);

    ProfessorsListController.$inject = ['Professor'];

    function ProfessorsListController(Professor) {
        var vm = this;

        vm.data = {};
        vm.displayData = [].concat(vm.data);

        activate();

        //////////////////////

        function activate() {
            Professor.query({}, function (responseData) {
                vm.data = responseData;
                vm.displayData = [].concat(vm.data);
            });
        }

    }
})();

