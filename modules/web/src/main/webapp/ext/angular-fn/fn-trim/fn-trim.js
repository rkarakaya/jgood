(function (angular) {
    var module = angular.module('fn.trim', []);

    module.filter('fnTrim', trim);

    function trim () {
        return function(value) {
            if(!angular.isString(value)) {
                return value;
            }
            return value.replace(/^\s+|\s+$/g, '');
        };
    }
} (angular));