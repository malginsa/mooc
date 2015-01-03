#!/usr/bin/env python

def add(a,b):
	assert a>0, 'a is a negative'
	assert b>0, 'b is a negative'
	return a+b

try:
	add(-1,2)
except AssertionError, e:
	print 'AssertionError in routine add: ', e

