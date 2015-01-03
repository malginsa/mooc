#!/usr/bin/env python

def myPow(b,p):
	if p==0:
		return 1
	if p < 0:
		bb = 1./b
	else:	
		bb = b
	res = 1
	for i in range(abs(p)):
		res *= bb
	return res

def myLog(x,b):
	if x == 1:
		return 0
	elif x>=b:
		res = 1
		while myPow(b,res) <= x:
			res += 1
		return res-1
	else:
		return 0

print myLog(15,2)
print myLog(16,2)
print myLog(15,3)
print myLog(3,7)

#print myPow(2,3)
#print myPow(3,3)
#print myPow(3,2)
#print myPow(3,1)
#print myPow(9,-2)

