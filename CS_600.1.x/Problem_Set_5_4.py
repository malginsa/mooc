#!/usr/bin/env python

def multlist(m, n):
    '''
    m is the multiplication factor
    n is a list.
    '''
    result = n[:]
    for i in range(len(n)):
        result[i] = m*n[i]
    return result   

print multlist(3,[1,2,3])
