#!/usr/bin/env python

def gcdRecur(a,b):
	if b==0:
		return a
	else:
		return gcdRecur(b, a%b)

print gcdRecur(2,12)
print gcdRecur(6,12)
print gcdRecur(9,12)
print gcdRecur(17,12)

#def gcdIter(a,b):
#	res = min(a,b)
#	while a%res != 0 or b%res != 0:
#		res -= 1
#	return res
#
#print gcdIter(2,12)
#print gcdIter(6,12)
#print gcdIter(9,12)
#print gcdIter(17,12)

