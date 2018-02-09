(function () {
    "use strict";
    function informingService($q, $http, errorService) {


        return {
            fetchRecentItems: function () {
                var deferred = $q.defer();
                $http.get('{apiPrefix}/informings').then(function (response) {
                    deferred.resolve(response.data);
                }, errorService.httpCatch('Can not list informings', deferred));
                return deferred.promise;
            },

            deleteInformingById: function (id) {
                var deferred = $q.defer();
                $http['delete']('{apiPrefix}/informings/' + id).then(function (response) {
                    deferred.resolve();
                }, errorService.httpCatch('Delete informing failed', deferred));
                return deferred.promise;
            },

            closeInformingById: function (id) {
                var deferred = $q.defer();
                $http.post('{apiPrefix}/informings/' + id + '/close').then(function (response) {
                    deferred.resolve(response.data);
                }, errorService.httpCatch('Close informing failed', deferred));
                return deferred.promise;
            },

            saveInforming: function (inf) {
                var deferred = $q.defer();
                $http.put('{apiPrefix}/informings', inf).then(function (response) {
                    deferred.resolve(response.data);
                }, errorService.httpCatch('Can not save your report', deferred));
                return deferred.promise;
            }
        };
    }

    informingService.$inject = ['$q', '$http', 'jg.errors'];
    angular.module('jgood').factory('jg.informingService', informingService);
}());

