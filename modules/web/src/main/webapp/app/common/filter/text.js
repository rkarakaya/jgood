/**
 * @author Ramazan Karakaya
 */

(function () {
    function textFilter(textsService) {
        return function () {
            return textsService.translateSafe.apply(this, arguments);
        };
    }

    textFilter.$inject = ['jg.textsService'];
    angular.module('jgood').filter('text', textFilter);
}());


