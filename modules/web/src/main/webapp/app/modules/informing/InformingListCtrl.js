(function () {
    "use strict";
    function InformingListCtrl(popupService, informingService, recentItems) {
        this.recentItems = recentItems;

        this.closeInformingAt = function (index) {
            var inf = this.recentItems.list[index];
            informingService.closeInformingById(inf.id).then(function (updated) {
                angular.extend(inf, updated);
            }.bind(this));
        };

        this.deleteInformingAt = function (index) {
            var list = this.recentItems.list,
                inf = list[index];
            popupService.confirmDelete(inf.id, function () {
                informingService.deleteInformingById(inf.id).then(function () {
                    list.splice(index, 1);
                }.bind(this));
            });
        };

        this.canDelete = function (inf) {
            return inf.status === 'CLOSED';
        };

        this.canClose = function (inf) {
            return inf.status === 'NEW';
        };
    }


    function InformingListCtrlResolver(resolveProvider) {
        resolveProvider.registerForCtrl('jg.InformingListCtrl', {
            recentItems: ['$q', 'jg.informingService',
                function ($q, informingService) {
                    return informingService.fetchRecentItems();
                }]
        });
    }

    InformingListCtrl.$inject = ['jg.popupService', 'jg.informingService', 'recentItems'];
    angular.module('jgood').config(['jg.resolveProvider', InformingListCtrlResolver]);
    angular.module('jgood').controller('jg.InformingListCtrl', InformingListCtrl);
}());