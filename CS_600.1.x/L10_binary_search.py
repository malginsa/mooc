#!/usr/bin/env python

def search(L, e): 
    def bSearch(L, e, low, high): 
        if high == low: 
            return L[low] == e 
        mid = low + int((high - low)/2) 
        if L[mid] == e: 
            return True 
        if L[mid] > e: 
            return bSearch(L, e, low, mid-1) # it makes unlimited recursion calls
        else: 
            return bSearch(L, e, mid + 1, high) 
 
    if len(L) == 0: 
        return False 
    else: 
        return bSearch(L, e, 0, len(L) - 1)

def rBinarySearch(list,element):
    if len(list) == 1:
        return element == list[0]
    mid = len(list)/2
    if list[mid] > element:
        return rBinarySearch( list[ : mid] , element )
    if list[mid] < element:
        return rBinarySearch( list[mid : ] , element)
    return True

print rBinarySearch([10,20],25)

