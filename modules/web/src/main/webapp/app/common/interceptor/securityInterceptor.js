/**
 * securityInterceptor - check security related HTTP status messages and redirect.
 *
 * @author Ramazan Karakaya
 */
(function () {
    var MSG_NOT_ALLOWED = "You are not allowed to perform this action!";

    function securityInterceptor($q, $log, $injector, $rootScope) {
        var $state;

        function goToLogin() {
            if (!$state) {
                $state = $injector.get('$state');
            }

            $rootScope.afterLogin = {};
            $rootScope.afterLogin.toState = $state.next;
            $rootScope.afterLogin.toParams = $state.toParams;

            $state.go('login');
        }

        return {
            requestError: function (rejection) {
                return $q.reject(rejection);
            },

            // optional method
            response: function (response) {
                return response || $q.when(response);
            },

            responseError: function (rejection) {
                if (rejection.status === 401) {
                    goToLogin();
                } else if (rejection.status === 403) {
                    rejection.data = rejection.data || {};
                    rejection.data.error = MSG_NOT_ALLOWED;
                }
                return $q.reject(rejection);
            }
        };
    }

    securityInterceptor.$inject = ['$q', '$log', '$injector', '$rootScope'];
    angular.module('jgood').factory('jg.securityInterceptor', securityInterceptor);


}());