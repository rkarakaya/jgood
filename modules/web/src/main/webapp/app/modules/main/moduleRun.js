(function () {
    "use strict";
    function mainRun($rootScope, $state) {
        $rootScope.$state = $state;
        $rootScope.network = {failure: false};

        var firstChangeSuccess = $rootScope.$on('$stateChangeSuccess', function () {
            $rootScope.isJgLoaded = true;
            firstChangeSuccess();
        });

        $rootScope.$on('jg:LostConnection', function () {
            $rootScope.network.failure = true;
        });

        $rootScope.$on('jg:ConnectionEstablished', function () {
            $rootScope.network.failure = false;
        });
    }

    mainRun.$inject = ['$rootScope', '$state'];
    angular.module('jgood').run(mainRun);
}());

