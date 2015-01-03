#!/usr/bin/env python

import math

def alt_harmonic():
	s=0
	for n in range(1,100):
		s+=float(pow(-1,n+1))/n**4
		print "%3d"%(n),
		print "%20.15f"%(s)

def alt1():
	s=0
	for n in range(1,20):
		s+=float(3*pow(-1,n+1))/n**2
		print "%3d"%(n),
		print "%20.15f"%(s)

def alt2():
	s=0
	for n in range(1,10):
		s+=float(pow(-1,n+1))/n/pow(6,n)
		print "%3d"%(n),
		print "%20.15f"%(s)

alt2()
print 2./11
print 2./13
print 1./8
print 1./9
print 1./5
