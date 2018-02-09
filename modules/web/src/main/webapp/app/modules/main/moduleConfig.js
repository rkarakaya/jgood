/**

 * @author Ramazan Karakaya
 */
(function () {
    "use strict";
    function mainConfig($stateProvider, $urlRouterProvider, resolveProvider, $httpProvider, $compileProvider, $provide, blockUIConfig) {

        // Disable DEBUG for a perfect AngularJS performance!
        $compileProvider.debugInfoEnabled(false);

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        // initialize get if not there
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }

        // Disable ajax request caching
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

        $httpProvider.interceptors.push('jg.urlPlaceHolderInterceptor');
        $httpProvider.interceptors.push('jg.securityInterceptor');

        // Decorate $state to add next state to use in resolver functions.
        $provide.decorator('$state', ['$delegate', '$rootScope', function ($delegate, $rootScope) {
            $rootScope.$on('$stateChangeStart', function (event, state, params) {
                $delegate.next = state;
                $delegate.toParams = params;
            });

            return $delegate;
        }]);

        $urlRouterProvider.otherwise('/login');

        $stateProvider

            .state('index', {
                abstract: true,
                url: "/index",
                templateUrl: "app/modules/main/partial/content.html",
                controller: 'jg.MainCtrl',
                controllerAs: 'ctrl',
                resolve: resolveProvider.getForCtrl('jg.MainCtrl')
            })
            .state('index.dashboard', {
                url: "/dashboard",
                templateUrl: "app/modules/dashboard/partial/dashboard.html",
                data: {pageTitle: 'Dashboard'},
                controller: 'jg.DashboardCtrl'
            })
            .state('index.informings', {
                url: "/informings",
                templateUrl: "app/modules/informing/partial/list.html",
                controllerAs: 'ctrl',
                controller: 'jg.InformingListCtrl',
                resolve: resolveProvider.getForCtrl('jg.InformingListCtrl')

            })
            .state('index.reportus', {
                url: "/reportus",
                templateUrl: "app/modules/informing/partial/reportus.html",
                controllerAs: 'ctrl',
                controller: 'jg.ReportUsCtrl',
                resolve: resolveProvider.getForCtrl('jg.ReportUsCtrl')

            })
            .state('index.thankyou', {
                url: "/thankyou",
                templateUrl: "app/modules/informing/partial/thankyou.html"
            })
            .state('login', {
                url: "/login",
                templateUrl: "app/modules/main/partial/login.html",
                data: {pageTitle: 'Login', specialClass: 'gray-bg'},
                controller: 'jg.LoginCtrl',
                controllerAs: 'ctrl',
                resolve: resolveProvider.getForCtrl('jg.LoginCtrl')
            });
    }

    mainConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'jg.resolveProvider', '$httpProvider', '$compileProvider', '$provide' ];
    angular.module('jgood').config(mainConfig);
}());


