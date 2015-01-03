#!/usr/bin/env python
import math
import sys

#n=int(sys.argv[1])

def func(x):
	return x**3 +2*x**2 -x -13
def der(x):
	return 3*x**2 +4*x -1

x=1.5
eps=0.00000000001
i=0
while (math.fabs(func(x)) > eps):
	i+=1
	x-= func(x)/der(x)
	print i, x, func(x)
