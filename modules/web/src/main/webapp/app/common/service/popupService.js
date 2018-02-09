(function () {
    function popupServiceFactory($uibModal, $q) {
        var okCancelCtrl = ['$scope', '$uibModalInstance', 'params',
                function ($scope, $uibModalInstance, params) {
                    $scope.params = params;
                    $scope.ok = function (result) {
                        $uibModalInstance.close(result);
                        if ($scope.params.fnOk) {
                            $scope.params.fnOk();
                        }
                    };
                    $scope.cancel = function (result) {
                        $uibModalInstance.dismiss(result);
                    };
                }],

            infoDlgCtrl = ['$scope', '$uibModalInstance', 'modelMap',
                function ($scope, $uibModalInstance, modelMap) {
                    if (modelMap) {
                        angular.extend($scope, modelMap);
                    }

                    $scope.ok = function (result) {
                        $uibModalInstance.close(result);
                    };
                }],


            ctrlConfirm = ['$scope', '$uibModalInstance', 'modelMap', 'fnConfirm',
                function ($scope, $uibModalInstance, modelMap, fnConfirm) {
                    if (modelMap) {
                        angular.extend($scope, modelMap);
                    }

                    $scope.confirm = function () {
                        $uibModalInstance.close();
                        fnConfirm();
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss();
                    };
                }],


            alertCtrl = ['$scope', '$uibModalInstance', 'params',
                function AlertDialogController($scope, $uibModalInstance, params) {
                    $scope.params = params;
                    $scope.close = function () {
                        $uibModalInstance.close();
                    };
                }];

        return {


            alert: function (msgKey, params) {
                var opts = {
                    templateUrl: 'app/common/partial/dialogAlert.html',
                    controller: alertCtrl,
                    resolve: {
                        params: function () {
                            return {
                                msgKey: msgKey,
                                msgParams: params || []
                            }
                        }
                    }
                };
                $uibModal.open(opts);
            },

            confirmDelete: function (itemName, fnOk) {
                var opts = {
                    templateUrl: 'app/common/partial/dialogConfirmDelete.html',
                    controller: okCancelCtrl,
                    resolve: {
                        params: function () {
                            return {
                                itemName: itemName,
                                fnOk: fnOk
                            }
                        }
                    }
                };
                $uibModal.open(opts);
            },

            showFormDialog: function (parentScope, formModels) {
                var modelMap = formModels || {},
                    deferred = $q.defer(),
                    dialogScope = angular.extend(parentScope.$new(), modelMap),
                    ctrl = ['$scope', '$uibModalInstance',
                        function ($scope, $uibModalInstance) {
                            $scope.ok = function (form) {
                                if (form.$valid) {
                                    $uibModalInstance.close();
                                    deferred.resolve();
                                }
                            };

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss();
                                deferred.reject();
                            };
                        }],

                    opts = {
                        templateUrl: 'app/common/partial/formDlg.html',
                        controller: ctrl,
                        scope: dialogScope
                    };

                $uibModal.open(opts);
                return deferred.promise;
            },

            showDialog: function (templateUrl, objParams, controller) {
                var opts = {
                    templateUrl: templateUrl,
                    controller: controller,
                    resolve: {
                        params: function () {
                            return objParams;
                        }
                    }
                };
                $uibModal.open(opts);
            },

            showInfoDialog: function (templateUrl, modelMap) {
                var models = angular.extend({}, modelMap);
                models.partialUrl = templateUrl;

                var opts = {
                    templateUrl: 'ce/app/common/partial/partialInfoDialog.html',
                    controller: infoDlgCtrl,
                    resolve: {
                        modelMap: function () {
                            return models;
                        }
                    }
                };
                $uibModal.open(opts);
            },


            confirmDialog: function (modelMap, fnConfirm) {
                var onConfirm, models;

                if (angular.isFunction(modelMap)) {
                    onConfirm = modelMap;
                    models = {};
                } else {
                    models = modelMap;
                    onConfirm = fnConfirm;
                }


                var opts = {
                    templateUrl: 'app/common/partial/dialogConfirm.html',
                    controller: ctrlConfirm,
                    resolve: {
                        modelMap: function () {
                            return models;
                        },
                        fnConfirm: function () {
                            return onConfirm;
                        }
                    }
                };
                $uibModal.open(opts);
            }



        };
    }

    popupServiceFactory.$inject = ['$uibModal', '$q'];
    angular.module('jgood').factory('jg.popupService', popupServiceFactory);
}());

