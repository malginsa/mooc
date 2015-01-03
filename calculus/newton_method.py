#!/usr/bin/env python

import math

def func(x):
	return 1+math.log(x)-x/200.
def der(x):
	return 1./x-0.005
x=111115
eps=0.00000000000000001
while abs(func(x))>eps:
	x-=float(func(x))/float(der(x))
	print "%20.15f"%(x)

