/**
 * Created by natete on 22/10/15.
 */

'use strict';

angular.module('corestudioApp.services')
.factory('Professor', ['PROFESSOR_ENDPOINT','$resource', function(PROFESSOR_ENDPOINT, $resource) {
        return $resource(PROFESSOR_ENDPOINT);
    }]);