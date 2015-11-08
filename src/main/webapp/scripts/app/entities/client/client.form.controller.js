/**
 * Created by natete on 08/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .controller('ClientFormController', ClientFormController);

    function ClientFormController() {
        var vm = this;

        this.mode = 'edit';
        this.name = '';
        this.firstSurname = '';
        this.secondSurname = '';
    }
})();