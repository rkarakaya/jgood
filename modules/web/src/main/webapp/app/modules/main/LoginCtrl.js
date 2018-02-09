/**
 * LoginCtrl - Login Controller, handles username&password authentication
 *
 * @author Ramazan Karakaya
 */
(function () {


    function LoginCtrl($scope, $rootScope, authenticationService, $state) {
        this.credentials = {
            username: 'admin@jgood.com',
            password: '12345'
        };
        this.loginError = null;
        this.loginSubmitted = false;

        this.processSuccessfulAuthentication = function () {
            var afterLogin = $rootScope.afterLogin;
            if (afterLogin && afterLogin.toState) {
                delete $rootScope.afterLogin;
                $state.go(afterLogin.toState, afterLogin.toParams);
            } else {
                $state.go('index.dashboard');
            }
        };

        $scope.login = function () {
            if (this.loginSubmitted) {
                return;
            }

            this.loginError = null;
            this.loginSubmitted = true;

            var username = this.credentials.username,
                pwd = this.credentials.password;

            if (username && pwd) {
                authenticationService.authenticate(username, pwd)
                    .then(function () {
                        this.processSuccessfulAuthentication();
                    }.bind(this)).catch(function () {
                        this.loginError = 'Bad username or password';
                    }.bind(this)).finally(function () {
                        this.loginSubmitted = false;
                    }.bind(this));
            }
        }.bind(this);


    }

    function LoginCtrlResolver(resolveProvider) {
        resolveProvider.registerForCtrl('jg.LoginCtrl', {
            textInit: ['jg.textsService',
                function (textsService) {
                    return textsService.init();
                }],
            checkAlreadyLoggedIn: ['$q', '$state', '$rootScope',
                function ($q, $state, $rootScope) {
                    var deferred = $q.defer();
                    if ($rootScope.username) {
                        deferred.reject(true);
                        $state.go('index.dashboard');
                    } else {
                        // Not logged in.
                        deferred.resolve(true);
                    }
                    return deferred.promise;
                }]
        });
    }

    LoginCtrl.$inject = ['$scope', '$rootScope', 'jg.authService', '$state'];
    angular.module('jgood').config(['jg.resolveProvider', LoginCtrlResolver]);
    angular.module('jgood').controller('jg.LoginCtrl', LoginCtrl);

}());