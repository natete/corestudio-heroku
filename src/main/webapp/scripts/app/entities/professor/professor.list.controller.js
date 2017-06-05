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

        vm.search = search;

        function search(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? Math.floor((pagination.start + 1) / pagination.number) : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            Professor.query(pageRequest, function(responseData) {
                vm.data = responseData.content;
                tableState.pagination.numberOfPages = responseData.totalPages;
            });
        }

    }
})();

