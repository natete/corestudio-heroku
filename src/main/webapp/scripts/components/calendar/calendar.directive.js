/**
 * Created by Ignacio González Bullón on 21/11/15.
 */

(function () {
    'use strict';

    angular.module('corestudioApp.calendar')
        .directive('horizontalCalendar', directive);


    function directive() {
        var linkFunction, daysInMonth, setUpCalendar, processSelectedDates, arrangeDates, getCalendar, getMonth, addSelectedToMonth, getMaxDays, getDayNames, controllerFunction;
        var config = {};
        config.monthsNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
        config.daysName = ["lu", "ma", "mi", "ju", "vi", "sa", "do"];

        linkFunction = function (scope, elem, attrs) {
            //var arrangedSelectedDates = processSelectedDates(scope.vm.selectedDates);
            setUpCalendar(scope, attrs);

            scope.$on('update-selected-dates', function (e, selectedDates, year, callback) {
                setUpCalendar(scope, attrs);
                var arrangedSelectedDates = processSelectedDates(selectedDates, year);
                scope.months.forEach(function (month, index) {
                    addSelectedToMonth(month, index, arrangedSelectedDates);
                });
                callback && callback();
            });
            scope.$on('deleted-date', function (e, date) {
                var month = scope.months[date.date.getMonth()];
                var day = month.days[date.date.getDate() + month.offset];
                day.type = undefined;
                day.description = undefined;
            });
        };

        setUpCalendar = function (scope, attrs) {
            var calendar = getCalendar(attrs.year);

            scope.daysNames = calendar.daysNames;
            scope.months = calendar.months;
        };

        processSelectedDates = function (selectedDates, year) {
            var arrangedSelectedDates = [];
            selectedDates.forEach(function (selectedType) {
                var arrangedType = {
                    type: selectedType.type
                };
                arrangedType.arrangedDates = arrangeDates(selectedType.dates, year);
                arrangedSelectedDates.push(arrangedType);
            });
            return arrangedSelectedDates;
        };

        arrangeDates = function (dates, year) {
            var arrangedDates = [];
            dates.forEach(function (date) {
                if (new Date(date.date).getFullYear() === year) {
                    date.date = new Date(date.date);
                    var month = date.date.getMonth();
                    if (arrangedDates[month] === undefined) {
                        arrangedDates[month] = [];
                    }
                    arrangedDates[month].push(date);
                }
            });
            return arrangedDates;
        };

        getCalendar = function (year) {
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
            month.offset = firstWeekDay - 1;
            month.days = [];
            for (var i = 0; i < firstWeekDay; i++) {
                month.days.push(undefined);
            }
            for (var j = 0; j < daysInMonth(monthNumber, year); j++) {
                var day = {
                    date: new Date(initialDate)
                };
                month.days.push(day);
                initialDate.setDate(initialDate.getDate() + 1);
            }
            return month;
        };

        addSelectedToMonth = function (month, monthNumber, selectedDates) {
            selectedDates.forEach(function (selectedType) {
                if (selectedType.arrangedDates[monthNumber] !== undefined) {
                    selectedType.arrangedDates[monthNumber].forEach(function (date) {
                        var day = month.days[date.date.getDate() + month.offset];
                        day.type = selectedType.type;
                        day.description = date.description;
                        day.id = date.id;

                    });
                }
            });
            return month;
        };

        getMaxDays = function (months) {
            var result = 0;
            months.forEach(function (month) {
                if (month.days.length > result) {
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

        controllerFunction = function () {
            var vm = this;
            vm.selectDate = selectDate;

            function selectDate(date) {
                vm.onSelectDate({date: date});
            }
        };

        return {
            restrict: 'AE',
            scope: {
                year: '@',
                onSelectDate: '&',
                selectedDates: '='
            },
            link: linkFunction,
            controller: controllerFunction,
            controllerAs: 'vm',
            bindToController: true,
            templateUrl: 'scripts/components/calendar/calendar.template.html'
        }
    }
})();