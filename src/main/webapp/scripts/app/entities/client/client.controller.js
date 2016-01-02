/**
 * Created by natete on 02/11/15.
 */
(function() {

    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientController', ClientController);

    ClientController.$inject = ['Client'];

    function ClientController(Client) {
        var vm = this;

        vm.data = {};
        vm.displayData = [].concat(vm.data);

        vm.isConsumedPass = isConsumedPass;

        activate();

        ////////////////

        function activate() {
            vm.data = Client.query();
        }

        function isConsumedPass(lastDate) {
            return new Date(lastDate) < new Date();
        }
    }
})();
