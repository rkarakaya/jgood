(function () {
    "use strict";
    function ReportUsCtrl($state, informingService) {
        this.inf = {};
        this.saveReport = function (infForm) {
            if (infForm.$valid) {
                informingService.saveInforming(this.inf).then(function () {
                    $state.go('index.thankyou');
                });
            }
        };
    }

    ReportUsCtrl.$inject = ['$state', 'jg.informingService'];
    angular.module('jgood').controller('jg.ReportUsCtrl', ReportUsCtrl);
}());