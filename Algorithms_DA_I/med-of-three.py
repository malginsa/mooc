#!/usr/bin/env python

def medprn(gAr):
	lo=0
	hi=len(gAr)-1
	med = (hi-lo)/2
	if gAr[lo]<gAr[med]:
		i1=lo
		i2=med
	else:
		i1=med
		i2=lo
	if gAr[hi]<gAr[i2]:
		if gAr[hi]>gAr[i1]:
			i2=hi
		else:
			i2=i1
	print gAr, gAr[i2]

medprn([1,2,3])
medprn([7,2,5])
medprn([5,6,4])
medprn([5,2,1,3])
medprn([5,2,1,3,8,9])
medprn([5,2,6,3,8,9,1])
medprn([1,2])
medprn([2,1])

