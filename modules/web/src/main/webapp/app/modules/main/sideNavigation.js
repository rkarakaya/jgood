/**
 * Side navigation directive to run metisMenu plugin when the element is ready.
 *
 * @author Ramazan Karakaya
 */
(function () {
    function sideNavigation($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element) {
                // Call the metsiMenu plugin and plug it to sidebar navigation
                $timeout(function () {
                    element.metisMenu();
                });
            }
        };
    }

    sideNavigation.$inject = ['$timeout'];
    angular.module('jgood').directive('sideNavigation', sideNavigation);
}());