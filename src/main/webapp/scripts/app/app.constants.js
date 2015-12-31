'use strict';

angular.module("corestudioApp")
    .constant("CLIENT_ENDPOINT", "api/clients/:id")
    .constant("HOLIDAYS_ENDPOINT", "api/admin/holidays/:id")
    .constant("ACTIVITIES_ENDPOINT", "api/admin/activities/:id")
    .constant("GROUP_ENDPOINT", "api/groups/:id")
    .constant("CONFIG_ENDPOINT", "api/config")
    .constant("PASS_TYPES_ENDPOINT", "api/admin/passTypes/:id")
    .constant("PASS_ENDPOINT", "api/pass/:id")
    .constant("GET_PASSES_BY_CLIENT_ENDPOINT", "api/pass/getByClient/:clientId")
    .constant("GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT", "api/pass/getByClientAndYear/:clientId/:year")
    .constant("FREEZE_DATE", "api/pass/freezeDate")

;