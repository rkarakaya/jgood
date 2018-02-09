(function () {
    function i18nService($q, $http) {
        var props = {
            general: {
                login: 'Login',
                ok: 'Ok',
                cancel: 'Cancel',
                warn: {
                    delete_item: 'Are you sure?'
                },
                error: {
                    required: 'This field is required'
                }
            },
            report: {
                send: 'Send Now',
                informedBy: 'Your Name',
                gsm: 'Your Phone Number',
                gsm_h: 'Your phone number is not required but providing this may help us to improve our service',
                text: 'Text',
                address: 'Address',
                address_h: 'Please provide us a complete address'
            }
        };

        function init() {


            var deferred = $q.defer(),
                lang = navigator.language;

            /*
             * FIXME:  browser settings don't actually affect the navigator.language property that is obtained via javascript.
             * seee http://stackoverflow.com/questions/1043339/javascript-for-detecting-browser-language-preference
             * */

            if (lang !== 'en') {
                //load i18n file for this language
                return $http.get('/resources/locales/' + lang + '.json')
                    .then(function (response) {
                        angular.merge(props, response.data || {});
                    }).finally(function () {
                        deferred.resolve();
                    });
            }
            return deferred.promise;

        }

        function replaceParams(string, placeholders) {
            return string.replace(/\((\d+)\)/g, function (a, b) {
                return placeholders[b];
            });
        }

        function translateKey(strKey) {
            var translated, placeholders = [], i, keyParts;
            if (strKey) {
                placeholders = [];

                for (i = 1; i < arguments.length; i++) {
                    if (typeof (arguments[i]) === 'object') {
                        angular.forEach(arguments[i], function (item) {
                            placeholders.push(item);
                        });
                    } else {
                        placeholders.push(arguments[i]);
                    }
                }
                keyParts = strKey.split('.');
                translated = props;
                for (i = 0; i < keyParts.length; i++) {
                    translated = translated[keyParts[i]];
                    if (!translated) {
                        break;
                    }
                }
                if (translated && typeof translated === 'string') {
                    translated = replaceParams(translated, placeholders);
                }
            }
            return translated;
        }


        return {
            init: init,
            translateSafe: function (strKey) {
                var translated = translateKey.apply(this, arguments);
                return translated || strKey;
            },

            translate: function (strKey) {
                return translateKey.apply(this, arguments);
            }

        };
    }

    i18nService.$inject = ['$q', '$http'];
    angular.module('jgood').factory('jg.textsService', i18nService);
}());
