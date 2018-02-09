/**
 * @author Ramazan Karakaya
 */

(function () {
    function errorsFactory() {
        function handleError(response, title, deferred) {
            var body = (response.data && response.data.error && response.data.error.reason) || 'NA';
            if (_.isObject(body)) {
                body = JSON.stringify(body);
            }
            console.error(title, body);
            if (deferred) {
                deferred.reject(response);
            }

        }

        return {
            httpCatch: function (message, deferred) {
                return function (response) {
                    handleError(response, message, deferred);
                };

            }
        };
    }

    errorsFactory.$inject = [];
    angular.module('jgood').factory('jg.errors', errorsFactory);

}());

