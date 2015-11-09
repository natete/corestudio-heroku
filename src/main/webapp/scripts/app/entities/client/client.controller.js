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

        activate();

        ////////////////

        function activate() {
            vm.data = Client.query();
        }
    }
})();
