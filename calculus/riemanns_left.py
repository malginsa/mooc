#!/usr/bin/env python
import math
import sys

low=0
high=1
n=int(sys.argv[1]) # count of dxs-1

dx=float(high-low)/float(n)
summ=0
for i in range(0,n):
	xi= low +i*dx +dx/2.
	summ+= math.sqrt(1 +math.e**(2*xi))
summ*= dx
print summ
