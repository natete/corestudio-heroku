/**
 * Created by natete on 17/10/15.
 */

angular.module('corestudioApp.services')
    .value('LOGIN_ENDPOINT', 'api/login')
    .value('LOGOUT_ENDPOINT', 'api/logout')
    .value('PROFESSOR_ENDPOINT', 'api/professor/:id');