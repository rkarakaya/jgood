/**
 * @author Ramazan Karakaya
 */

(function () {
    function jgRowLayoutDirective() {
        return {
            priority: 9999,
            restrict: 'A',
            compile: function compile(tElement, tAttrs, transclude) {

                return {
                    pre: function (scope, element, attrs) {
                        var layoutStr = attrs.jgRowLayout;
                        if (layoutStr) {
                            element.data('jgRowLayout', layoutStr);
                        }
                    }
                };
            }
        };
    }

    jgRowLayoutDirective.$inject = [];
    angular.module('jgood').directive('jgRowLayout', jgRowLayoutDirective);
}());


(function () {
    function jgFormGroupDirective(textsService, $compile, $interpolate) {
        return {
            restrict: 'EA',
            templateUrl: function (tElement, tAttrs) {
                var as = ['app/common/directive/partial/formGroup'];
                if (tAttrs.jqpTplSuffix) {
                    as.push('_');
                    as.push(tAttrs.jgpTplSuffix);
                }
                as.push('.html');
                return as.join('');
            },

            replace: true,
            transclude: true,
            require: '^form',
            scope: {
                labelKey: '@jgFormGroup',
                jgpHelpSuffix: '='
            },
            controller: ['$scope', '$element', '$transclude',
                function ($scope, $element, $transclude) {
                    $transclude(function (clone) {
                        var $controlBlock = $element.find('.tc');
                        $controlBlock.prepend(clone);

                        var errorElm = angular.element('<span class="help-inline">{{errorTipMessage}}</span>');

                        $controlBlock.append(errorElm);
                        $compile(errorElm)($scope);
                    });
                }],

            link: function (scope, element, attrs, formCtrl) {
                var formName = element.closest('[ng-form]').attr('ng-form') || element.closest('form').attr('name'),
                    elmInput = element.find('[ng-model]'),
                    remaining;

                if (!elmInput.is(':checkbox') && !elmInput.is(':hidden') && !elmInput.is(':radio')) {
                    elmInput.addClass('form-control');
                }

                var rowLayoutStr = element.inheritedData('jqRowLayout');
                if (rowLayoutStr) {
                    rowLayoutStr = "''" + rowLayoutStr;
                } else {
                    rowLayoutStr = '1:2:9';
                }
                var arrCells = rowLayoutStr.split(':');

                scope.labelColClass = 'col-md-' + arrCells[0];

                remaining = 12 - (arrCells[0] * 1);

                if (arrCells.length > 1) {
                    scope.controlColClass = 'col-md-' + (arrCells[1]);
                    remaining -= (arrCells[1] * 1);
                } else {
                    scope.controlColClass = 'col-md-' + Math.floor(remaining * 0.25);
                    scope.helpColClass = 'col-md-' + Math.floor(remaining * 0.58);
                }

                if (arrCells.length > 2) {
                    scope.helpColClass = 'col-md-' + remaining;
                }

                var helpKeyDefault = attrs.jgpHelpKey || (scope.labelKey + '_h'),
                    helpMessageDefault = textsService.translate(helpKeyDefault);
                scope.helpMessage = helpMessageDefault;
                scope.$watch('jgpHelpSuffix', function (suffix) {
                    var msg;
                    if (suffix) {
                        msg = textsService.translate(helpKeyDefault + '_' + suffix);
                    }
                    scope.helpMessage = msg || helpMessageDefault;
                });

                if (formName && elmInput.length) {

                    var inputName = elmInput.attr('name');
                    if (inputName) {
                        inputName = $interpolate(inputName)(scope.$parent);

                        var validateDef = scope.$eval(elmInput.attr('ui-validate') || '{}');
                        var validationKeys = _.keys(validateDef);
                        _.addToSet(validationKeys, 'required');
                        var errorMsgMap = {};
                        _.forEach(validationKeys, function (key) {
                            errorMsgMap[key] = textsService.translate([formName, 'error', inputName, key].join('.'))
                                || textsService.translate(['error', inputName, key].join('.'))
                                || textsService.translate('general.error.' + key);
                        });


                        var errorExpression = [formName, inputName, "$invalid"].join(".");
                        // Watch the parent scope, because current scope is isolated.
                        scope.$parent.$watch(errorExpression, function (isError) {

                            scope.isError = isError;
                            var errMsg = '';
                            if (isError) {
                                var errors = [];
                                _.forEach(validationKeys, function (validationKey) {
                                    if (formCtrl.$error[validationKey]) {
                                        errors.push(errorMsgMap[validationKey]);
                                    }
                                });
                                errMsg = errors.join(',');
                            }
                            scope.errorTipMessage = errMsg;

                        });
                    }
                }
            }
        };
    }

    jgFormGroupDirective.$inject = ['jg.textsService', '$compile', '$interpolate'];
    angular.module('jgood').directive('jgFormGroup', jgFormGroupDirective);
}());

