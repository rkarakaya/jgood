(function (angular) {
    var module = angular.module('fn.permission', ['fn.trim']);

    module.factory('fnSecurity', securityService);

    module.directive('fnPermission', permissionDirective);

    function securityService() {
        var userPermissions = [];
        var appServices = [];
        var spa = false;

        function hasPermission (permission) {
            return spa || userPermissions.indexOf(permission) !== -1;
        }

        return {
            hasPermission: hasPermission,

            hasAllPermissions: function (permissions) {
                var allCheck = true;

                for (var i = 0; i < permissions.length; i++) {
                    allCheck = allCheck && hasPermission(permissions[i]);
                    if (!allCheck) {
                        break;
                    }
                }

                return allCheck;
            },

            hasService: function (service) {
                return appServices.indexOf(service) !== -1;
            },

            setPermissions: function (permissions) {
                if (Array.isArray(permissions)) {
                    userPermissions = permissions;
                } else {
                    userPermissions = [];
                }
            },

            setServices: function (services) {
                if (Array.isArray(services)) {
                    appServices = services;
                } else {
                    appServices = [];
                }
            },

            setSpa: function (isSpa) {
                spa = isSpa === true;
            }
        };
    }

    permissionDirective.$inject = ['$compile', 'fnSecurity', 'fnTrimFilter'];

    function permissionDirective($compile, securityService, fnTrim) {
        function getList(attr) {
            var result = null;

            if (attr) {
                result = attr.split(',');
                result = Array.isArray(result) ? result : [];

                for (var i = 0; i < result.length; i++) {
                    result[i] = fnTrim(result[i]);
                }
            }

            return result;
        }

        function checkAll(arr, fn) {
            var allCheck = true;

            for (var i = 0; i < arr.length; i++) {
                allCheck = allCheck && fn(arr[i]);
                if (!allCheck) {
                    break;
                }
            }

            return allCheck;
        }

        function checkAny(arr, fn) {
            var anyCheck = false;

            for (var i = 0; i < arr.length; i++) {
                anyCheck = anyCheck || fn(arr[i]);
                if (anyCheck) {
                    break;
                }
            }

            return anyCheck;
        }

        function checkNotAll(arr, fn) {
            return !checkAny(arr, fn);
        }

        function checkNotAny(arr, fn) {
            return !checkAll(arr, fn);
        }

        function checkPositive(anyAttr, allAttr, fn) {
            var anyCheck = true;
            var allCheck = true;

            var anyArr = getList(anyAttr);
            if (anyArr) {
                anyCheck = checkAny(anyArr, fn);
            }

            var allArr = getList(allAttr);
            if (allArr) {
                allCheck = checkAll(allArr, fn);
            }

            return anyCheck && allCheck;
        }

        function checkNegative(anyAttr, allAttr, fn) {
            var anyCheck = true;
            var allCheck = true;

            var anyArr = getList(anyAttr);
            if (anyArr) {
                anyCheck = checkNotAny(anyArr, fn);
            }

            var allArr = getList(allAttr);
            if (allArr) {
                allCheck = checkNotAll(allArr, fn);
            }

            return anyCheck && allCheck;
        }

        function checkUser($attr) {
            return checkPositive($attr.ifUserAny, $attr.ifUserAll, securityService.hasPermission)
                && checkNegative($attr.ifUserNotAny, $attr.ifUserNotAll, securityService.hasPermission);
        }

        function checkApp($attr) {
            return checkPositive($attr.ifAppAny, $attr.ifAppAll, securityService.hasService)
                && checkNegative($attr.ifAppNotAny, $attr.ifAppNotAll, securityService.hasService);
        }

        return {
            restrict: 'E',
            scope: false,
            compile: function ($element, $attr) {
                // Check for user permissions to display this directive content.
                var permissionCheck = checkUser($attr) && checkApp($attr);

                // Cache the element contents
                var contents = $element.html();

                // Create a HTML comment node to replace the element.
                var comment = document.createComment(' fn-permission: ' + permissionCheck + ' ');
                comment = angular.element(comment);

                // Remove the element and put the comment node instead of it.
                $element.replaceWith(comment);

                // Link function
                // $element is the comment node here.
                return function ($scope, $element) {
                    if (permissionCheck) {
                        // Compile the cached HTML contents of the directive
                        // Use parent scope. No need to create a new scope for this directive.
                        var compiled = $compile(contents)($scope);

                        // Add compiled content after comment node.
                        $element.after(compiled);
                    }
                };
            }
        };
    }
}(angular));