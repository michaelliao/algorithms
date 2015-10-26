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
            print('binary_search(%s, %s) => %s' % (input['key'], input['array'], r))
            if input['result'] != r:
                raise Error('failed. expected = %s, actual = %s' % (input['result'], r))

if __name__ == '__main__':
    main()
