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

    config.$inject = ['$httpProvider', 'showErrorsConfigProvider', 'uibDatepickerConfig', 'uibDatepickerPopupConfig'];

    function config($httpProvider, showErrorsConfigProvider, uibDatepickerConfig, uibDatepickerPopupConfig) {
        configShowErrors(showErrorsConfigProvider);
        configDatepicker(uibDatepickerConfig, uibDatepickerPopupConfig);
        //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
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

    run.$inject = ['$rootScope', '$state', 'Principal'];

    function run($rootScope, $state, Principal) {
        $rootScope.spinner = {
            active: false
        };

        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {

            // Remember previous state unless we've been redirected to login or we've just
            // reset the state memory after logout. If we're redirected to login, our
            // previousState is already set in the authExpiredInterceptor. If we're going
            // to login directly, we don't want to be sent to some previous state anyway
            if (toState.name !== 'login') {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            }
        });

        $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error) {
            if(Principal.isAuthenticated()) {
                if($rootScope.previousStateName) {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                } else {
                    $state.go('inbox');
                }
            } else {
                $state.go('login');
            }
        });
    }
})();