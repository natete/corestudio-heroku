/**
 * Created by natete on 25/10/15.
 */

'use strict';

angular.module('corestudioApp')
    .controller('LoginController', ['$state', 'Auth', function ($state, Auth) {
        var vm = this;
        vm.buttonText = 'Entrar';

        vm.login = function () {

            vm.buttonText = 'Entrando...';
            vm.authenticationError = false;

            Auth.login(vm.credential)
                .then(function () {
                    $state.go('inbox');
                    vm.authenticationError = false;
                    //if ($rootScope.previousStateName === 'register') {
                    //    $state.go('home');
                    //} else {
                    //    $rootScope.back();
                    //}
                })
                .catch(function () {
                    vm.authenticationError = true;
                    vm.buttonText = 'Entrar';
                });
        };
    }]);