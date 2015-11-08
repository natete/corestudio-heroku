'use strict';

angular.module('corestudioApp')
    .controller('NavController', ['$scope', function ($scope) {
        $scope.loggedIn = true;
        //$scope.user = authService.user;
        //
        //if (authService.user) {
        //    $scope.loggedIn = true;
        //} else {
        //    $('#wrapper').css('padding-left', '0');
        //}
        //
        //$scope.logout = function() {
        //    authService.logout();
        //}

    }]);

