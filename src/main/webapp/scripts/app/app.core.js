/**
 * Created by natete on 06/11/15.
 */
(function () {
    'use strict';

    angular.module('app.core', [
            'ui.router',
            'ui.bootstrap.showErrors',
            'ngCookies',
            'smart-table',
            'ngResource',
            'ngMessages',
            'ui.bootstrap',
            'ngAnimate',
            'treasure-overlay-spinner',
            'checklist-model'
        ])
        .config(config)
        .run(run);

    config.$inject = ['showErrorsConfigProvider', 'uibDatepickerConfig', 'uibDatepickerPopupConfig'];

    function config(showErrorsConfigProvider, uibDatepickerConfig, uibDatepickerPopupConfig) {
        configShowErrors(showErrorsConfigProvider);
        configDatepicker(uibDatepickerConfig, uibDatepickerPopupConfig);

    }

    function configShowErrors(showErrorsConfigProvider) {
        showErrorsConfigProvider.showSuccess(true);
        showErrorsConfigProvider.showFeedback(true);
    }

    function configDatepicker(uibDatepickerConfig, uibDatePickerPopupConfig) {
        uibDatepickerConfig.showWeeks = false;
        uibDatepickerConfig.startingDay = 1;
        uibDatepickerConfig.initDate = new Date();
        uibDatePickerPopupConfig.showButtonBar = false;
        uibDatePickerPopupConfig.datepickerPopup = 'dd/MM/yyyy';
    }

    run.$inject = ['$rootScope'];

    function run($rootScope) {
        $rootScope.spinner = {
            active: false,
        };
    }
})();