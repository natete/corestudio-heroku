/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.message')
        .controller('MessageController', MessageController);

    MessageController.$inject = ['Alerts', 'Message'];

    function MessageController(Alerts, Message) {
        var vm = this;

        vm.readMessage = readMessage;
        vm.deleteMessage = deleteMessage;

        activate();

        function activate() {
            Message.query({}, function(responseData) {
                vm.messages = responseData;
            });
        }

        function readMessage(message) {
            Message.update({id: message.id}, function (responseData) {
                message.isRead = true;
            }, function(response) {
               Alerts.addHeaderErrorAlert(response.headers());
            });
        }

        function deleteMessage(message) {
            Message.delete({id: message.id}, function (responseData) {
                vm.messages.splice(vm.messages.indexOf(message), 1);
            }, function (response) {
                Alerts.addHeaderErrorAlert(response.headers());
            });
        }
    }

})();

