/**
 * Created by natete on 17/10/15.
 */
'use strict';

angular.module('corestudioApp.controllers', [])
.controller('LoginController', ['$scope', 'authService', '$state', function($scope, authService, $state) {

        $scope.buttonText = 'Entrar';

        $scope.login = function() {

            $scope.buttonText = 'Entrando...';

            authService.login($scope.credential.username, $scope.credential.password).
                then(function() {
                    $state.go('home');
            }, function(error) {
                    $scope.invalidLogin = true;
                }).finally(function() {
                    $scope.buttonText = 'Entrar';
                });
        }
    }]);