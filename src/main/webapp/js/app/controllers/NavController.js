'use strict';

angular.module('corestudioApp.controllers')
.controller('NavController', ['authService', '$scope', function(authService, $scope) {
    if(authService.user) {
        $scope.loggedIn = true;
    } else {
        $('#wrapper').css('padding-left', '0');
    }
}]);

