/**
 * Created by natete on 16/11/15.
 */

(function () {
    'use strict';

    angular.module('corestudioApp.client')
        .filter('permalink', function () {
            return function (person) {
                return person === undefined ? '' : angular.lowercase(person.name) + '-' + angular.lowercase(person.firstSurname);
            }
        })
        .filter('replaceAccents', function(){
            return function (source) {
                if(!angular.isDefined(source)){ return; }
                var accent = [
                        /[\300-\306]/g, /[\340-\346]/g, // A, a
                        /[\310-\313]/g, /[\350-\353]/g, // E, e
                        /[\314-\317]/g, /[\354-\357]/g, // I, i
                        /[\322-\330]/g, /[\362-\370]/g, // O, o
                        /[\331-\334]/g, /[\371-\374]/g, // U, u
                        /[\321]/g, /[\361]/g, // N, n
                        /[\307]/g, /[\347]/g, // C, c
                    ],
                    sanitized = ['A','a','E','e','I','i','O','o','U','u','N','n','C','c'];

                for (var i = 0; i < accent.length; i++){
                    source = source.replace(accent[i], sanitized[i]);
                }

                return source;
            };
        });
})();