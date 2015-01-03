#!/usr/bin/env python
#
# Some sorting algorithms
# todo: gAr for every alg
#		in MergeSort change the order of comparison
#		optimize MergeSort by using gAr
#
import math
import time
import sys

# global variables
gAr = []	# global Array of integers to sort
cRec = 0	# counts of recursion calls
cCmp = 0	# conuts of comparisons for Tim

def main():
	global gAr
	global cRec
	global cCmp

	IntArray = LoadIntArray()

	cRec = 0
	IntArray_copy = IntArray[:]
	print "by MergeSort alg...",
	t_start = time.time()
	lmerge = MergeSort(IntArray_copy)
	t_elapsed = round( time.time() - t_start, 4 )
	print"    time elapsed", t_elapsed, "sec", "   #recurs=", cRec

	IntArray_copy = IntArray[:]
	print "by Inversions alg...",
	t_start = time.time()
	inv,linvers = CountInversionsAndSort(IntArray_copy)
	t_elapsed = round( time.time() - t_start, 4 )
	print "   time elapsed", t_elapsed, "sec",
	if linvers == lmerge :
		print " checked",
	else:
		print " ERROR: arrays mismached",
	print " #inversions = ",inv
	
	cRec = 0
	gAr = IntArray[:]
	print "by QuickSort2Dir alg...",
	t_start = time.time()
	QuickSort2Dir(0,len(gAr)-1)
	t_elapsed = round( time.time() - t_start, 4 )
	print "time elapsed", t_elapsed, "sec", 
	if gAr == lmerge :
		print " checked",
	else:
		print " ERROR: arrays mismached",
	print " #recurs=",cRec

	cRec = 0
	cCmp = 0
	gAr = IntArray[:]
	print "by QuickSort1Dir alg...",
	t_start = time.time()
	QuickSort1Dir(0,len(gAr)-1)
	t_elapsed = round( time.time() - t_start, 4 )
	print"time elapsed", t_elapsed, "sec",
	if gAr == lmerge :
		print " checked",
	else:
		print " ERROR: arrays mismached",
	print " #recurs=",cRec,
	print " #cmps=",cCmp

#	IntArray_copy = IntArray[:]
#	print "by SimpleSort alg...",
#	t_start = time.time()
#	lbubble = SimpleSort(IntArray_copy)
#	t_elapsed = round( time.time() - t_start, 4 )
#	print" time elapsed", t_elapsed, "sec",
#	if linvers == lbubble :
#		print " checked"
#	else:
#		print " ERROR: arrays mismached"


# rearrange gAr
# lo - position of element to which all elements are compared is exactly the first in gAr
# hi - positions of last elements in gAr
# return new position of lo-element
def rearrange2Dir(lo,hi):
	i = lo+1
	j = hi
	while (1):
		while (gAr[i]<gAr[lo])and(i<hi):
			i+=1
		while (gAr[j]>gAr[lo])and(j>lo):
			j-=1
		if i>=j:
			break
		gAr[i],gAr[j] = gAr[j],gAr[i]
		i+=1
		j-=1
	gAr[lo],gAr[j] = gAr[j],gAr[lo]
	return j

def rearrange1Dir(lo,hi):
#	gAr[lo], gAr[hi] = gAr[hi], gAr[lo]	# swap the lo and hi elements fot prgs assignments
# find median-of-three -- it would be with index i2
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
	gAr[lo], gAr[i2] = gAr[i2], gAr[lo]

	i = j = lo+1
	while (j<=hi):
		if gAr[j]<gAr[lo]:
			gAr[i], gAr[j] = gAr[j], gAr[i]
			i+=1
		j+=1
	gAr[lo], gAr[i-1] = gAr[i-1],gAr[lo]
	return i-1

# QuickSort with 2 directions  lo [i-> ... <-j] hi
def QuickSort2Dir(lo,hi):
	global cRec
	cRec+=1
	newpos = rearrange2Dir(lo,hi)
	if (newpos-lo>1):	# left part of gAr is bigger than 1
		QuickSort2Dir(lo,newpos-1)
	if (hi-newpos>1):
		QuickSort2Dir(newpos+1,hi)

# QuickSort with 1 direction lo [i-> j-> ...] hi
def QuickSort1Dir(lo,hi):
	global cCmp
	cCmp += hi
	global cRec
	cRec+=1
	newpos = rearrange1Dir(lo,hi)
	if (newpos-lo>1):
		QuickSort1Dir(lo,newpos-1)
	if (hi-newpos>1):
		QuickSort1Dir(newpos+1,hi)

def LoadIntArray():
	IntArray = []
	if len(sys.argv)==2:
		FileName = sys.argv[1]
	else:
		FileName = 'Array1000.txt'
	print "Array loading from",FileName,"...",
	inFile = open (FileName, 'r', 0)
	for line in inFile:
		IntArray.append(int(line.strip()))
	print " loaded, size = ",(len(IntArray))
	return IntArray

def SimpleSort(A):
	for i in range(0,len(A)-1):
		for j in range(i+1,len(A)):
			if A[i]>A[j]:
				A[i],A[j] = A[j],A[i]
	return A

def MergeSort(A):
	global cRec
	cRec+=1
#	if len(A)==1:
#		return A
	B = A[:len(A)/2]
	if len(B)>1:
		B = MergeSort(B)
	C = A[len(A)/2:]
	if len(C)>1:
		C = MergeSort(C)
	b=c=0
	D=[]
	while (1):
		if (len(B[b:])==0):
			D+=C[c:]
			break
		elif (len(C[c:])==0):
			D+=B[b:]
			break
		elif B[b]<C[c] :
			D.append(B[b])
			b+=1
		elif B[b]>C[c]:
			D.append(C[c])
			c+=1
		else:
			exit("Ahtung! Logical error in MergeSort alg")
	return D

def CountInversionsAndSort(A):
	if (len(A)==1):
		return 0, A
	B = A[:len(A)/2]
	i1, B = CountInversionsAndSort(B)
	C = A[len(A)/2:]
	i2, C = CountInversionsAndSort(C)
	i3=b=c=0
	D=[]
	while (1):
		if (len(B[b:])==0):
			D+=C[c:]
			break
		elif (len(C[c:])==0):
			D+=B[b:]
			break
		elif B[b]<C[c] :
			D.append(B[b])
			b+=1
		elif B[b]>C[c]:
			D.append(C[c])
			c+=1
			i3+=len(B[b:])
		else:
			exit("Ahtung! Logical error in inversions alg")
	return i1+i2+i3, D
			
main()

