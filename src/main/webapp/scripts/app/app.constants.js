'use strict';

angular.module("corestudioApp")
    .constant("CLIENT_ENDPOINT", "api/clients/:id")
    .constant("HOLIDAYS_ENDPOINT", "api/admin/holidays/:id")
    .constant("ACTIVITIES_ENDPOINT", "api/admin/activities/:id")
    .constant("GROUP_ENDPOINT", "api/groups/:id")
    .constant("CONFIG_ENDPOINT", "api/config")
    .constant("PASS_TYPES_ENDPOINT", "api/admin/passTypes/:id")
    .constant("PASS_ENDPOINTS", {
        "PASS_ENDPOINT": "api/pass/:id",
        "GET_PASSES_BY_CLIENT_ENDPOINT": "api/pass/getByClient/:clientId",
        "GET_PASSES_BY_CLIENT_AND_YEAR_ENDPOINT": "api/pass/getByClientAndYear/:clientId/:year",
        "FREEZE_DATE_ENDPOINT": "api/pass/freezeDate",
        "CONSUME_DATE_ENDPOINT": "api/pass/consumeDate",
        "RELEASE_DATE_ENDPOINT": "api/pass/releaseDate"
    })
    .constant("DATE_TYPES", {
        PENDING: 'pending-date',
        FROZEN: 'frozen-date',
        CONSUMED: 'consumed-date',
        HOLIDAY: 'holiday'
    })
    .constant("ACCOUNTS_ENDPOINT", "api/accounts/:year/:month")
    .constant("PROFESSOR_ENDPOINT", "api/professor/:id")
    .constant("MONTHLY_SESSION_ENDPOINT", "api/professor/session/:professorId/:year")

;