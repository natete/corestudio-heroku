/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    ClientFormController.$inject = ['Client'];

    function ClientFormController(Client) {
        var vm = this;

        this.mode = 'edit';
        this.name = '';
        this.firstSurname = '';
        this.secondSurname = '';
    }
})();