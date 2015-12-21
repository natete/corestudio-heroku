'use strict';

angular.module("corestudioApp")
    .constant("CLIENT_ENDPOINT", "api/clients/:id")
    .constant("HOLIDAYS_ENDPOINT", "api/admin/holidays/:id")
    .constant("ACTIVITIES_ENDPOINT", "api/admin/activities/:id")
    .constant("GROUP_ENDPOINT", "api/groups/:id")
    .constant("CONFIG_ENDPOINT", "api/config")
;