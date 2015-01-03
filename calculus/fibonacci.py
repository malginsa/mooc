#!/usr/bin/env python

import math

def fibb(n):
	if n==0:
		return 0
	if n==1:
		return 1
	return fibb(n-1) + fibb(n-2)

def f(x):
	res=0
	for n in range(0,50):
		res += fibb(n)*pow(x,n)
	return res

x=0
for i in range(0,10):
	x+=0.1
	print x, f(x), x/(1-x-x**2)

