#!/usr/bin/env python

import math

def harmonic():
	s=0
	for n in range(1,119):
		s+=1./n
		print "%3d"%(n),
		print "%20.15f"%(s)

def harmonic2():
	s=0
	for n in range(1,100000000):
		s+=1./2/n
		if n%1000000 == 0:
			print "%3d"%(n),
			print "%20.15f"%(s)

def series1():
	s=0
	for n in range(1,126):
		s+=1./(n**2)
		print "%3d"%(n),
		print "%20.15f"%(s)

def series2():
	s=0
	s_=s
	for n in range(1,150):
		s+=math.factorial(n)/math.pow(n/math.e,n)
		print "%3d"%(n),
		print "%20.15f"%(s),
		print "%20.15f"%(s-s_)
		s_=s
	
harmonic2()
