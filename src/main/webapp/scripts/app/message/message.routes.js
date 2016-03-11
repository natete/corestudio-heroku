/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.message')
        .config(config);

    config.$inject = ['$stateProvider'];

    function config($stateProvider) {
        $stateProvider.state('inbox', {
            url: '/inbox',
            templateUrl: 'scripts/app/message/message.html',
            controller: 'MessageController',
            controllerAs: 'messagesCtrl',
            data: {
                authorities: ['USER', 'ADMIN']
            },
            resolve: {
                principal: ['Auth', function (Auth) {
                    return Auth.authorize();
                }]
            }
        });
    }

})();

