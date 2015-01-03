#!/usr/bin/env python
import math
import sys

#power=int(sys.argv[1])
power=4
summ=0
for i in range(1,11):
	an= math.pow(i,power)
	summ+= an
	print "%2d %5d %5d"%(i, an, summ)
print math.pow(10,power+1)/power
