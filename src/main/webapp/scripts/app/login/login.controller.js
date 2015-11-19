/**
 * Created by natete on 25/10/15.
 */

'use strict';

angular.module('corestudioApp')
    .controller('LoginController', ['$rootScope', '$scope', '$state', 'Auth', function($rootScope, $scope, $state, Auth) {

        $scope.buttonText = 'Entrar';

        $scope.login = function () {

            $scope.buttonText = 'Entrando...';

            Auth.login({
                username: $scope.username,
                password: $scope.password,
            }).then(function () {
                $state.go('home');
                $scope.authenticationError = false;
                //if ($rootScope.previousStateName === 'register') {
                //    $state.go('home');
                //} else {
                //    $rootScope.back();
                //}
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    }]);