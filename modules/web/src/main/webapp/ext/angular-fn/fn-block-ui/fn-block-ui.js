/**
 * Block UI feature
 *
 * Inspired by https://github.com/McNull/angular-block-ui
 */
(function (angular) {
    /**
     * A constant key to put default template into $templateCache
     */
    var DEFAULT_TEMPLATE_URL = '$$fnBlockUiDefaultTemplateUrl$$';

    /**
     * Map to hold existing fnBlockUi instances
     */
    var instances = {};

    var module = angular.module('fn.block-ui', []);

    /**
     * Default options for the directive
     */
    module.constant('fnBlockUiConstants', {
        containerClass: 'fn-block-ui-container',
        defaultTemplateUrl: DEFAULT_TEMPLATE_URL,
        elementClass: 'fn-block-ui',
        visibleClass: 'fn-block-ui-visible',
        message: 'Loading...'
    });

    module.directive('fnBlockUi', fnBlockUi);

    fnBlockUi.$inject = ['$templateRequest', '$compile', 'fnBlockUiConstants'];

    function fnBlockUi ($templateRequest, $compile, constants) {

        function compileFn ($element, $attrs) {
            if (!$element.hasClass(constants.elementClass)) {
                $element.addClass(constants.elementClass);
            }

            var $container = angular.element('<div class="' + constants.containerClass + '"></div>');
            $element.append($container);

            return {
                pre: function ($scope, $element, $attrs) {
                    preLink($scope, $element, $attrs);
                }
            };
        }

        function preLink($scope, $element, $attrs) {
            var id = $attrs.fnBlockUi;
            var instance = instances[id];

            if (!instance) {
                instance = instances[id] = { active: false };
            }

            instance.message = $attrs.fnBlockUiMessage || constants.message;
            $scope.instance = instance;

            var templateUrl = $attrs.fnBlockUiTemplate || constants.defaultTemplateUrl;

            // Load the template via templateRequest
            $templateRequest(templateUrl).then(function(html) {
                var template = angular.element(html);
                var $container = $($element).find('.' + constants.containerClass)[0];
                angular.element($container).append(template);

                // Compile the template with the scope of the directive
                // Let user be able to use scope variables in template
                $compile(template)($scope);
            });

            $scope.$watch('instance.active', function(value) {
                // All the show/hide work is done via a simple css class
                $element.toggleClass(constants.visibleClass, !!value);
            });

            $scope.$on('$destroy', function () {
                delete instances[id];
            });
        }

        return {
            scope: true,
            restrict: 'A',
            compile: compileFn
        };
    }

    module.factory('fnBlockUi', fnBlockUiService);

    fnBlockUiService.$inject = ['$timeout'];

    function fnBlockUiService ($timeout) {
        function serviceFn(id, timeout) {
            var instance = instances[id];
            if (!instance) {
                instance = instances[id] = { active: true };
            } else {
                instance.active = true;
            }

            function unblock() {
                instance.active = false;
            }

            if (isNumeric(timeout) && timeout >= 0) {
                $timeout(unblock, timeout);
            }

            return unblock;
        }

        return serviceFn;
    }

    module.run(moduleRunFn);

    moduleRunFn.$inject = ['$templateCache', 'fnBlockUiConstants'];

    function moduleRunFn ($templateCache, fnBlockUiConstants) {
        $templateCache.put(DEFAULT_TEMPLATE_URL, '<div class="fn-block-ui-overlay"></div><div class="fn-block-ui-message-container"><div class="fn-block-ui-message">{{instance.message}}</div></div>');
    }

    /**
     * From a jQuery implementation
     */
    function isNumeric( obj ) {
        return !Array.isArray( obj ) && (obj - parseFloat( obj ) + 1) >= 0;
    }

}(angular));