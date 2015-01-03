#!/usr/bin/env python

def Hanoi (n,t1,t2,spare):
	if n==1:
#		print t1,' moves to',t2
		return 1
	else:
		return \
		Hanoi( n-1, t1, spare, t2) + \
		Hanoi( 1, t1, t2, spare) +\
		Hanoi( n-1, spare, t2, t1) \

for i in range(1,65):
	print i,'\t',Hanoi(i,'t1','t2','t3')

