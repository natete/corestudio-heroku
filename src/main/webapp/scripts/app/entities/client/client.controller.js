/**
 * Created by natete on 02/11/15.
 */
(function () {

    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientController', ClientController);

    ClientController.$inject = ['Client'];

    function ClientController(Client) {
        var vm = this;

        vm.data = {};

        vm.search = search;
        vm.isConsumedPass = isConsumedPass;

        function search(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? (pagination.start + 1) % pagination.number : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            Client.query(pageRequest, function (responseData) {
                vm.data = responseData.content;
                tableState.pagination.numberOfPages = responseData.totalPages;
            });
        }

        function isConsumedPass(lastDate) {
            return new Date(lastDate) < new Date();
        }
    }
})();
