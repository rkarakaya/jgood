/**
 * @author Ramazan Karakaya
 */

(function () {
    "use strict";
    var jgood = angular.module('jgood',
        [
            'ui.router',
            'ngSanitize',
            'ui.bootstrap',
            'ui.validate',
            'ngAnimate',
            'angular-fn',
            'ngStorage'
        ]);


    (function (core, coreFactory) {

        // Controllers will be defined by dot-delimited namespaces
        // that end in "Ctrl" (ex. foo.BarCtrl).
        var pattern = /\.[^.]*?Ctrl$/i;

        // As the factories are invoked, each will return the
        // constructor for the given Controller; we can cache these
        // so we don't have to keep re-wiring the factories.
        var constructors = {};

        // I proxy the core factory and route the request to either
        // the Controller provider or the underlying factory.
        function factory(name, controllerFactory) {

            // If the given injectable name is not one of our
            // factories, then just hand it off to the core
            // factory registration.
            if (!pattern.test(name)) {
                return coreFactory.apply(core, arguments);
            }

            // Register the Controller Factory method as a
            // Controller. Here, we will leverage the fact that
            // the *RETURN* value of the constructor is what is
            // actually being used as the Controller instance.
            core.controller(
                name,
                function ($scope, $injector) {

                    var cacheKey = "cache_" + name;

                    var Constructor = constructors[cacheKey];

                    // If the cached constructor hasn't been built
                    // yet, invoke the factory and cache the
                    // constructor for later use.
                    if (!Constructor) {
                        Constructor = constructors[cacheKey] = $injector.invoke(controllerFactory);
                    }

                    // By returning something other than _this_,
                    // we are telling AngularJS to use the following
                    // object instance as the Controller instead of
                    // the of the current context (ie, the Factory).
                    // --
                    // NOTE: We have to pass $scope through as an
                    // injectable otherwise the Dependency-Injection
                    // framework will not know how to create it.
                    return $injector.instantiate(Constructor, {"$scope": $scope});

                }
            );

            // Return the core to continue method chaining.
            return core;

        }

        // Overwrite the Angular-provided factory.
        core.factory = factory;

    }(jgood, jgood.factory));

}());


