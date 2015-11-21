/**
 * Created by Ignacio González Bullón on 21/11/15.
 */

(function () {
    'use strict';

    angular.module('corestudioApp.calendar')
        .directive('horizontalCalendar', directive);


    function directive () {
        var linkFunction, daysInMonth, getCalendar, getMonth, getMaxDays, getDayNames;
        var config = {};
        config.monthsNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
        config.daysName = ["lu", "ma", "mi", "ju", "vi", "sa", "do"];

        linkFunction = function (scope, elem, attrs) {

            var calendar = getCalendar(attrs.year);

            scope.daysNames = calendar.daysNames;
            scope.months = calendar.months;

            scope.$watch(attrs.year, function (value) {
                getCalendar(value);
            });
        };

        getCalendar = function(year) {
            var result = {};
            result.months = [];

            for (var i = 0; i < 12; i++) {
                var month = getMonth(i, year);
                result.months.push(month);
            }

            var maxDays = getMaxDays(result.months);
            result.daysNames = [];
            result.daysNames = getDayNames(maxDays);

            return result;
        };

        getMonth = function (monthNumber, year) {
            var initialDate = new Date(year, monthNumber, 1);
            var firstWeekDay = (initialDate.getDay() + 6) % 7;
            var month = {};
            month.name = config.monthsNames[monthNumber];
            month.days = [];
            for (var i = 0; i < firstWeekDay; i++) {
                month.days.push(undefined);
            }
            for (var j = 0; j < daysInMonth(i, year); j++) {
                month.days.push(new Date(initialDate));
                initialDate.setDate(initialDate.getDate() + 1);
            }
            return month;
        };

        getMaxDays = function (months) {
            var result = 0;
            months.forEach(function(month) {
                if(month.days.length > result) {
                    result = month.days.length;
                }
            });
            return result;
        };

        getDayNames = function (days) {
            var dayNames = [];
            for (var i = 0; i < days; i++) {
                dayNames.push({name: config.daysName[i % 7]});
            }
            return dayNames;
        };

        daysInMonth = function (month, year) {
            return 32 - new Date(year, month, 32).getDate();
        };

        return {
            restrict: 'AE',
            scope: {},
            link: linkFunction,
            templateUrl: 'scripts/components/calendar/calendar.template.html'
        }
    }
})();