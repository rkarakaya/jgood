/**
 * authenticationService - service to manage authentication for users
 *
 * @author Ramazan Karakaya
 */
(function () {
    function authenticationService($rootScope, $http, $httpParamSerializer, $q, $state, fnSecurity, $sessionStorage) {


        function setUiUserProfile(profile) {
            $rootScope.username = profile.username;
            fnSecurity.setPermissions(profile.roles);
        }


        if ($sessionStorage.profile) {
            setUiUserProfile($sessionStorage.profile);
        }

        function authenticate(params) {
            var deferred = $q.defer();

            $http({
                method: 'POST',
                url: '/api/login',
                data: $httpParamSerializer(params),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function () {
                    $http.get('/api/user/profile').then(function (prfResponse) {
                        var profile = {username: params.username, roles: prfResponse.data.roles};
                        setUiUserProfile(profile);
                        $sessionStorage.profile = profile;
                        deferred.resolve();
                    }, function (response) {
                        deferred.reject(response.data.error);
                    });

                }, function (response) {
                    deferred.reject(response.data.error);
                });
            return deferred.promise;
        }


        function authenticateUsernamePassword(username, pwd) {
            var config = {
                username: username,
                password: pwd
            };
            return authenticate(config);
        }

        function logout() {

            $http.get('/api/user/logout').then(function () {
                // Clear any state.
                $rootScope.username = null;
                fnSecurity.setPermissions([]);
                $state.go('login');
            });
        }

        return {
            authenticate: authenticateUsernamePassword,
            logout: logout
        };
    }

    authenticationService.$inject = ['$rootScope', '$http', '$httpParamSerializer', '$q', '$state', 'fnSecurity', '$sessionStorage'];
    angular.module('jgood').factory('jg.authService', authenticationService);

}());