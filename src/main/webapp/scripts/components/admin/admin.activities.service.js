/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .factory('Activity', Activity);

    Activity.$inject = ['$resource', 'ACTIVITIES_ENDPOINT'];

    function Activity($resource, ACTIVITIES_ENDPOINT) {
        return $resource(ACTIVITIES_ENDPOINT, {}, {
            'query': {
                url: 'api/admin/activities/getAll',
                method: 'GET',
                isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                url: 'api/admin/activities/update',
                method: 'PUT'
            },
            'save': {
                url: 'api/admin/activities/save',
                method: 'POST'
            },
            'getGroupActivities': {
                method: 'GET',
                isArray: true,
                url: 'api/admin/activities/getGroupActivities'
            }
        });
    }

})();

