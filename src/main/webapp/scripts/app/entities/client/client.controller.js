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

        vm.uiGrid = {
            paginationPageSizes: [10, 25, 50],
            paginationPageSize: 10,
            enableHorizontalScrollbar : 0,
            enableVerticalScrollbar : 0,
            enableColumnMenus : false,
            enableRowHashing: true,
            columnDefs: [
                { name: 'photo', displayName: '' },
                { name: 'name', displayName: 'Nombre' },
                { name: 'classes', displayName: 'Clases pendientes' },
            ]
        };

        activate();

        ////////////////

        function activate() {
            vm.uiGrid.data = Client.query();
        }
    }
})();
