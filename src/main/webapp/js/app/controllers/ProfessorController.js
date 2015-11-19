/**
 * Created by natete on 22/10/15.
 */

'use strict';

angular.module('corestudioApp.controllers')
    .controller('ProfessorController', ['$scope', 'Professor', function($scope, Professor) {
        $scope.formTitle = 'Nuevo profesor';
        $scope.professor = {};

        $scope.saveProfessor = function() {
            if($scope.professor.password === $scope.passwordConfirm) {
                $scope.professor.address = undefined;
                $scope.professor.admissionDate = undefined;
                $scope.professor.birthdate = undefined;
                $scope.professor.holidaysUsed = undefined;
                $scope.professor.photo = undefined;
                $scope.professor.qualification = undefined;
                $scope.professor.training = undefined;
                Professor.save($scope.professor, function() {
                    alert("Guardado");
                });
            } else {
                alert("Las contraseñas deben ser iguales");
            }
        }
    }]);
