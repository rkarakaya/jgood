/**
 * Resolves place holders in request urls via webProperties.
 * Note that the 'webProperties' object is filled with the parameters prefixed with '_web_' during gradle build.
 * @author Ramazan KARAKAYA
 */
(function () {
    function requestRetryInterceptor(webProperties) {

        function resolvePlaceHolders(str) {
            str = str.replace(/{(.*?)}/gi, function (a, variable) {
                return webProperties[variable];
            });

            return str;
        }

        return {
            request: function (config) {
                config.url = resolvePlaceHolders(config.url);
                return config;
            }
        };
    }

    requestRetryInterceptor.$inject = ['jg.webProperties'];
    angular.module('jgood').factory('jg.urlPlaceHolderInterceptor', requestRetryInterceptor);


}());