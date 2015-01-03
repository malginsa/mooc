#!/usr/bin/env python
import math

def fact(n):
	res = 1
	for i in range(1,n+1):
		res *= i
	return res

#for n in range(1,12):
#	print "%d  %20.16f"% (n, math.pow(math.e,3)/fact(n+1))

res = math.sqrt(1+math.e**2) -math.sqrt(2)
res += 1./2.*math.log( (math.sqrt(1+math.e**2)-1)/(math.sqrt(1+math.e**2)+1) )
res += 1./2.*math.log(3.+2*math.sqrt(2))

print res
