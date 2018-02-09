
/**
 * @author Ramazan Karakaya
 */

angular.module('jgood').provider('jg.resolve', function () {
    var ctrlResolveMap = {};

    this.getForCtrl = function (ctrlName) {
        return ctrlResolveMap[ctrlName];
    };

    this.registerForCtrl = function (ctrlName, prmResolveMap) {
        var resolveMap = ctrlResolveMap[ctrlName];
        if (!resolveMap) {
            ctrlResolveMap[ctrlName] = prmResolveMap;
        } else {
            angular.extend(resolveMap, prmResolveMap);
        }
    };

    this.$get = function () {
        return function (name) {
            return 'xxx ' + name + '!';
        };
    };
});