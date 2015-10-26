'use strict';

function binarySearch(key, arr) {
    var
        mid,
        lo = 0,
        hi = arr.length - 1;
    while (lo <= hi) {
        mid = lo + Math.floor((hi - lo) / 2);
        if (key < arr[mid]) {
            hi = mid - 1;
        } else if (key > arr[mid]) {
            lo = mid + 1;
        } else {
            return mid;
        }
    }
    return -1;
}

function main() {
    var i, r, input, inputs = require('../input.json');
    for (i=0; i<inputs.length; i++) {
        input = inputs[i];
        r = binarySearch(input.key, input.array);
        console.log('binarySearch(' + input.key + ', ' + JSON.stringify(input.array) + ' => ' + r)
        if (r !== input.result) {
            throw 'failed. expected = ' + JSON.stringify(input.result) + ', actual = ' + r;
        }
    }
}

main();
