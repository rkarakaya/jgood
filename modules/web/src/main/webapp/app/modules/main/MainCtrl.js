/**
 * @author Ramazan Karakaya
 */



(function () {
    "use strict";
    function MainCtrl($scope, authService) {

        this.logout = function () {
            authService.logout();
        };

    }


    function MainCtrlResolver(resolveProvider) {
        resolveProvider.registerForCtrl('jg.MainCtrl', {
            preChecks: ['jg.textsService',
                function (textsService) {
                    return textsService.init();
                }]
        });
    }

    MainCtrl.$inject = ['$scope', 'jg.authService'];
    angular.module('jgood')
        .config(['jg.resolveProvider', MainCtrlResolver])
        .controller('jg.MainCtrl', MainCtrl);


}());