/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.alerts', []);

    angular.module('corestudioApp.alerts')
        .factory('Alerts', Alerts);

    function Alerts() {
        var alerts = {};

        alerts.list = [];

        alerts.addAlert = function(alert) {
            alerts.list.push(alert);
        };
        alerts.addErrorAlert = function(msg) {
            alerts.list.push({type: 'danger', msg: msg});
        };
        alerts.addSuccessAlert = function(msg) {
            alerts.list.push({type: 'success', msg: msg});
        };
        alerts.closeAlert = function (index) {
            alerts.list.splice(index, 1);
        };
        alerts.addHeaderErrorAlert = function(headers) {
            alerts.addErrorAlert(headers['x-corestudioapp-alert']);
        };
        alerts.addHeaderSuccessAlert = function(headers) {
            alerts.addSuccessAlert(headers['x-corestudioapp-alert']);
        };

        return alerts;
    }


})();

