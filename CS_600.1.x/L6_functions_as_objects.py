#!/usr/bin/env python

def square(i):
	return i*i

def applyToEach( L, f ):
	''' assumes L - list, f - function
	replacing each element e of L with f(e) '''
	for i in range( len(L) ):
		L[i] = f( L[i] )

L = [4,5,9]
applyToEach( L, square )
print L
 
L = [1,-2,3.4]
applyToEach( L, abs )
print L
applyToEach( L, int )
print L

