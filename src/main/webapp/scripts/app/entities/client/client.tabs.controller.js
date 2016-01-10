/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientTabsController', ClientTabsController);

    ClientTabsController.$inject = ['Client', '$stateParams', '$state', '$scope'];

    function ClientTabsController(Client, $stateParams, $state, $scope) {
        var vm = this;

        vm.go = go;

        activate();


        ///////////////////////

        function activate() {

            if ($stateParams.id !== undefined) {
                if ($stateParams.client === undefined) {
                    Client.get({id: $stateParams.id}, function (client) {
                        vm.client = parseDates(client);
                    }, function (response) {
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                } else {
                    vm.client = $stateParams.client;
                }
            }

            vm.tabs = [
                { title: 'Personal', route: 'clients.viewClient.personal', active: false },
                { title: 'Pagos', route: 'clients.viewClient.passes', active: false },
                { title: 'Calendario', route: 'clients.viewClient.calendar', active: false }
            ]
        }

        function go(route) {
            $state.go(route);
        }

        $scope.$on("$stateChangeSuccess", function() {
            vm.tabs.forEach(function(tab) {
                tab.active = active(tab.route);
            });
        });

        function active(route){
            return $state.is(route);
        }

        function parseDates(client) {
            if (client.birthdate) {
                client.birthdate = new Date(client.birthdate);
            }
            if (client.admissionDate) {
                client.admissionDate = new Date(client.admissionDate);
            }
            return client;
        }
    }

})();

