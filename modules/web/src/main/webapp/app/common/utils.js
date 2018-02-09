/**
 * global util functions
 * @author Ramazan Karakaya
 */


_.arrayToMap = function (arr, keyProperty, options) {
    options = options || {mode: 'override'};
    var
        retMap = {}, modeOverride = options.mode == 'override';
    if (arr) {
        var target = arr.$$v || arr;
        _.each(target, function (item) {
            var propName = item[keyProperty];
            var value = options.valueProperty ? item[options.valueProperty] : item;
            if (propName != '$$hashKey') {
                if (modeOverride) {
                    retMap[propName] = value;
                } else {
                    retMap[propName] = retMap[propName] || [];
                    retMap[propName].push(value);
                }
            }
        });
    }
    return retMap;
}
_.mapToArray = function (map, keyPropertyName) {
    var retArr = [];
    if (map) {
        var target = map.$$v || map;
        _.each(target, function (val, key) {
            var item = {};
            item[keyPropertyName] = key;
            _.extend(item, val);
            retArr.push(item);

        });
    }
    return retArr;
}

_.getPropertyByArrayPath = function (object, arrPath) {
    var result = object;

    for (var i = 0; i < arrPath.length; i++) {
        result = result[arrPath[i]];
    }

    return result;
}

_.getPropertyByStrPath = function (object, strPath) {
    return _.getPropertyByArrayPath(object, strPath.split('.'));
}

_.addOrReplaceArray = function (arr, newValue, propPath) {
    var
        srcArr = arr || [],
        target = srcArr.$$v || srcArr,
        len = target.length,
        propNames = _.isArray(propPath) ? propPath : [propPath] , propPaths = [];

    for (var i = 0; i < propNames.length; i++) {
        propPaths.push(propNames[i].split('.'));
    }

    var leftVal, rightVal;

    for (i = 0; i < len; i++) {
        var equal = true;
        for (var j = 0; j < propPaths.length; j++) {
            leftVal = _.getPropertyByArrayPath(target[i], propPaths[j]);
            rightVal = _.getPropertyByArrayPath(newValue, propPaths[j]);
            if (leftVal != rightVal) {
                equal = false;
                break;
            }
        }

        if (equal) {
            return target.splice(i, 1, newValue);
        }
    }
    target.push(newValue);
}
_.findByProperty = function (collection, property, value) {
    if (!collection) {
        return null;
    }

    var
        target = collection.$$v || collection
        , len = target.length
        , arrPath = property.split('.');

    for (var i = 0; i < len; i++) {
        if (_.getPropertyByArrayPath(target[i], arrPath) == value) {
            return target[i];
        }
    }
    return null;
}

_.indexOfByProperty = function (collection, property, value) {
    if (!collection) {
        return -1;
    }

    var
        target = collection.$$v || collection
        , len = target.length
        , arrPath = property.split('.');

    for (var i = 0; i < len; i++) {
        if (_.getPropertyByArrayPath(target[i], arrPath) == value) {
            return i;
        }
    }
    return -1;
}

_.findByPropertyExample = function (collection, example) {
    if (collection) {
        var target = collection.$$v || collection;
        var resultArray = _.where(target, example);
        return resultArray.length > 0 ? resultArray[0] : null;
    }
    return null;
}

_.containsObjectByProp = function (collection, property, value) {
    if (collection) {
        var obj = _.findByProperty(collection, property, value);
        return obj !== null;
    }
    return false;
}
_.containsObjectOtherThanByProperty = function (collection, item, propName) {
    var obj = _.findByProperty(collection, propName, item[propName]);
    if (obj) {
        return obj != item;
    }
    return false;
}

_.notContainsObjectByProp = function (collection, property, value) {
    if (collection) {
        var obj = _.findByProperty(collection, property, value);
        return obj == null;
    }
    return true;
}

_.removeByProperty = function (collection, property, search) {
    if (collection) {
        var
            target = collection.$$v || collection
            , arrPath = property.split('.');
        if (_.isArray(target)) {
            var len = target.length;
            var indexArr = [];
            for (var i = 0; i < len; i++) {
                if (_.getPropertyByArrayPath(target[i], arrPath) == search) {
                    indexArr.push(i);
                }
            }
            if (indexArr.length > 0) {
                for (var i = 0; i < indexArr.length; i++) {
                    target.splice(indexArr[i] - i, 1);
                }
            }
        } else if (_.isObject(target)) {
            _.forEach(target, function (value, key) {
                if (value && _.getPropertyByArrayPath(value, arrPath) == search) {
                    delete target[key];
                }
            });
        }
    }
}

_.removeByExample = function (collection, example) {
    if (collection) {
        var
            target = collection.$$v || collection
            , exWithPaths = []
            ;

        for (var p in example) {
            exWithPaths.push({
                path: p.split('.'),
                val: example[p]
            });
        }

        if (_.isArray(target)) {
            var len = target.length;
            var indexArr = [];
            for (var i = 0; i < len; i++) {
                var equal = true;

                for (var k = 0; k < exWithPaths.length; k++) {
                    if (_.getPropertyByArrayPath(target[i], exWithPaths[k].path) != exWithPaths[k].val) {
                        equal = false;
                        break;
                    }
                }

                if (equal) {
                    indexArr.push(i);
                }

            }
            if (indexArr.length > 0) {
                for (var i = 0; i < indexArr.length; i++) {
                    target.splice(indexArr[i] - i, 1);
                }
            }
        } else if (_.isObject(target)) {
            _.forEach(target, function (key, val) {
                if (_.isObject(val)) {
                    var equal = true;
                    for (var p in example) {
                        if (val[p] != example[p]) {
                            equal = false;
                            break;
                        }
                    }
                    if (equal) {
                        delete target[key];
                    }
                }
            });
        }
    }
}

_.isNotEmpty = function (obj) {
    if (obj) {
        var target = obj.$$v || obj;
        return _.isEmpty(target) == false;
    }
    return false;
}

_.valuesFirst = function (obj) {
    var target = obj.$$v || obj;
    for (var key in target) {
        if (_.str.startsWith(key, '$') == false && _.has(target, key)) {
            return target[key];
        }
    }

    return  null;
}

_.keysFirst = function (obj) {
    if (obj) {
        var target = obj.$$v || obj;
        var keyArr = _.keys(target);
        return keyArr.length > 0 ? keyArr[0] : null;
    }
    return null;
}

_.pushAll = function (dstArray, srcArray) {
    var src = srcArray.$$v || srcArray;
    _.forEach(src, function (item) {
        dstArray.push(item);
    });
}

_.isNumeric = function (obj) {
    return !isNaN(parseFloat(obj)) && isFinite(obj);
}
_.str.substrAfterNumeric = function (str) {
    if (str) {
        var k = 0;
        for (; k < str.length; k++) {
            if (str.charAt(k) != '.' && isNaN(str.charAt(k))) {
                break;
            }
        }
        if (k < str.length) {
            return str.substr(k);
        }
    }
    return '';
}
_.str.substrBeforeAlpha = function (str) {
    var retStr = '';
    if (str) {
        var k = 0;
        for (; k < str.length; k++) {
            if (str.charAt(k) == '.' || !isNaN(str.charAt(k))) {
                retStr += str.charAt(k);
            } else {
                break;
            }
        }
    }
    return retStr;
}


_.removeFromSet = function (sourceArray, itemToBeRemoved) {
    var index = _.indexOf(sourceArray, itemToBeRemoved);
    if (index !== -1) {
        sourceArray.splice(index, 1);
    }
}

_.removeFromStringSet = function (obj, property, strRemove) {
    var str = obj[property];
    if (str) {
        var arr = str.split(',');
        obj[property] = _.without(arr, strRemove).join(',');
    }
}

_.addToStringSet = function (obj, property, strAdd) {
    var str = obj[property];
    if (str) {
        var arr = str.split(',');
        if (_.contains(arr, strAdd) == false) {
            obj[property] = str + ',' + strAdd;
        }
    } else {
        obj[property] = strAdd;
    }
}

_.addToSet = function (sourceArray, itemToBeAdd) {
    if (_.isNotEmpty(itemToBeAdd)) {
        var index = _.indexOf(sourceArray, itemToBeAdd);
        if (index == -1) {
            sourceArray.push(itemToBeAdd);
            return true;
        }
    }
    return false;
}
_.overrideObject = function (target, source) {
    for (var p in target) {
        if (_.isUndefined(source[p]) == false) {
            target[p] = source[p];
        }
    }
}

_.copyDefaults = function (target, defaults) {
    for (var p in defaults) {
        if (_.isUndefined(target[p])) {
            target[p] = defaults[p];
        }
    }
}

_.ensureObjectsByPath = function (obj, path) {
    var arr = path.split('.');
    var objRef = obj;
    for (var i = 0; i < arr.length; i++) {
        objRef[arr[i]] = objRef[arr[i]] || {};
        objRef = objRef[arr[i]];
    }
}

_.cleanEmptyProperties = function (obj) {
    if (_.isObject(obj)) {
        _.forEach(obj, function (val, key) {
            if (val == null) {
                delete obj[key];
            } else if (_.isArray(val)) {
                if (!val.length) {
                    delete obj[key];
                }
                //do nothing
            } else if (_.isNumber(val) || _.isBoolean(val)) {
                //do nothing
            } else if (_.isObject(val)) {
                _.cleanEmptyProperties(val);
            } else if (_.isEmpty(val)) {
                delete obj[key];
            }
        });
    }
}

_.innerObject = function (obj) {
    var innerObj = null;
    if (_.isObject(obj)) {
        _.forEach(obj, function (innerVal, innerKey) {
            if (_.isObject(innerVal)) {
                innerObj = innerVal;
            }
        });
    }
    return innerObj;
}


_.deleteProperties = function (obj, propArray) {
    _.forEach(propArray, function (propName) {
        delete obj[propName];
    });
}

_.leafKeysRecursive = function (rootObj) {
    var leafPaths = [];

    function traverse(obj, path) {

        _.forEach(obj, function (value, key) {
            if (_.isObject(value)) {
                var innerPath = [].concat(path);
                innerPath.push(key);
                traverse(value, innerPath);
            } else {
                if (path.length) {
                    leafPaths.push(path.join('.') + '.' + key);
                } else {
                    leafPaths.push(key);
                }
            }
        });
    }

    traverse(rootObj, []);
    return leafPaths;
}

_.deletePropertiesPrefixedBy = function (obj, prefix) {
    _.forEach(obj, function (value, key) {
        if (_.isObject(value)) {
            _.deletePropertiesPrefixedBy(value, prefix);
        } else if (_.str.startsWith(key, prefix)) {
            delete obj[key];
        }
    });

}


_.elementById = function (id) {
    var elm = angular.element(document.querySelector('#' + id));
    return elm;
}