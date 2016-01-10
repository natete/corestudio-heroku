/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 13/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.group')
        .factory('Group', Group);

    Group.$inject = ['$resource', 'GROUP_ENDPOINT'];

    function Group($resource, GROUP_ENDPOINT) {
        return $resource(GROUP_ENDPOINT, {}, {
            'query': {method: 'GET'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'save': {method: 'POST'},
            'getGroupsByActivity':{
                method: 'GET',
                isArray: true,
                url: 'api/groups/getByActivity/:activityId'
            }
        });
    }

})();

