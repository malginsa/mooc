#!/usr/bin/env python

import math

def seq1():
	for n in range(1,100):
		an = (10.*n+3)/(3.*n+9)
		threshold = 247./75
		print "%3d"%(n),
		print "%15.12f"%(an),
		print "%15.12f"%(threshold),
		print "%15.12f"%(an-threshold)

def seq2():
	n = 1
	an = (10.*n+3)/(3.*n+9)
	threshold = 247./75
	while (an-threshold) < 0:
		print "%3d"%(n),
		print "%15.12f"%(an),
		print "%15.12f"%(threshold),
		print "%15.12f"%(an-threshold)
		n+=1
		an = (10.*n+3)/(3.*n+9)

seq2()
