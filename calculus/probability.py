#!/usr/bin/env python

import math

t=2./5.

def fact(n):
	res = 1
	for i in range(1,n+1):
		res *= i
	return res

def a(n):
	return math.pow(-1,n) * math.pow(t,2*n+1) / fact(n) / (2*n+1)

print "1/977 = ", 1./977
sum = 0
for i in range(0,10):
	sum += a(i)
	print "%30.26f  %30.26f"%(a(i),sum)

print 8./21
print 14./37
print 20./53
print 71./187
print 13./34
