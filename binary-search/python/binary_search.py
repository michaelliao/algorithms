#!/usr/bin/env python3

import os, json

def binary_search(key, arr):
    lo = 0
    hi = len(arr) - 1
    while lo <= hi:
        mid = lo + (hi - lo) // 2;
        if key < arr[mid]:
            hi = mid - 1
        elif key > arr[mid]:
            lo = mid + 1
        else:
            return mid
    return -1

def main():
    with open(os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 'input.json')) as f:
        for input in json.load(f):
            r = binary_search(input['key'], input['array'])
            if input['result'] != r:
                raise Error('failed in %s, actual = %s' % (json.dumps(input), r))
    print('ok.')

if __name__ == '__main__':
    main()
