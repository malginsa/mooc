#!/usr/bin/env python
import array
import math
import sys
import time

numb = array.array('l',[0]*1000000)
numbsize = 0
echostep=10
#tcount = 0
targ = {}
chash = {}

def DeDupes():
	global numbsize
	dupes=0
	uniqid=0
	for i in range(1,numbsize):
		if numb[i] == numb[uniqid]:
			dupes+=1
		else:
			uniqid+=1
			numb[uniqid] = numb[i]
	numbsize = uniqid + 1
	return dupes

def rearrange2Dir(lo,hi):
  i = lo+1
  j = hi
  while (1):
    while (numb[i]<numb[lo])and(i<hi):
      i+=1
    while (numb[j]>numb[lo])and(j>lo):
      j-=1
    if i>=j:
      break
    numb[i],numb[j] = numb[j],numb[i]
    i+=1
    j-=1
  numb[lo],numb[j] = numb[j],numb[lo]
  return j

def QuickSort2Dir(lo,hi):
  pivot = rearrange2Dir(lo,hi)
  if (pivot-lo>1): # left part of numb is bigger than 1
    QuickSort2Dir(lo,pivot-1)
  if (hi-pivot>1):
    QuickSort2Dir(pivot+1,hi)

def Load(FileName):
	global numbsize
	print "loading from",FileName,"...",
	inFile = open (FileName, 'r', 0)
	numbsize=0
	for line in inFile:
		numb[numbsize]=long(line)
		numbsize+=1
	print "loaded",numbsize,'numbers'

def Rank(lo,hi,v):	# return max id | numb[id] <= v
	if lo >= hi:
		return lo
	med = lo+int(math.ceil((hi-lo)/2.))
	if v > numb[med]:
		return Rank(med,hi,v)
	elif v < numb[med]:
		return Rank(lo,med-1,v)
	else:	# v == nubm[med]
		return med

def CalcTargNaive():
#	global tcount
	print 'Naive method'
	print 'sorting...',
	QuickSort2Dir( 0, numbsize-1 )
	print 'done  ',
	print DeDupes(), 'dupes deleted'
	split=time.time()
	for i in range(numbsize-1):
		if (i)%int(math.ceil(numbsize/1./echostep)) == 0: print str(i*100/numbsize)+'%'
		for j in range(i+1,numbsize):
			sum = numb[i]+numb[j]
			if abs(sum) <= 10000: 
				targ[sum]=1
#				tcount+=1
	print 'len(targ.keys()) =',len(targ.keys()), '  time elapsed:',time.time()-split,'sec'

def CalcTargBSearch():
#	global tcount
	print 'Binary Search method'
	print 'sorting...',
	QuickSort2Dir( 0, numbsize-1 )
	print 'done  ',
	print DeDupes(), 'dupes deleted'
	split=time.time()
	for i in range(numbsize-1):
		j = Rank(i+1,numbsize-1,-10000-numb[i])
		if numb[j] < -10000-numb[i]:
			j+=1
		k = Rank(j-1,numbsize-1,10000-numb[i])
		for l in range(j,k+1):
			sum = numb[i] + numb[l]
			if abs(sum) <= 10000:
				targ[sum]=1
#				tcount += 1
			else:
				exit('ERROR abs(sum) of ['+str(i)+'] and ['+str(l)+'] is bigger than 10000')
	print 'len(targ.keys()) =',len(targ.keys()), '  time elapsed:',time.time()-split,'sec'

def fill_chash():
	for i in range(numbsize):
		key = numb[i]/10000
		if key in chash:
			if numb[i] not in chash[key]:
				chash[key].append(numb[i])
		else:
			chash[key]=[numb[i]]

def count_dupes_in_chash():
	c=0
	for key in chash:
		for i in range(len(chash[key])-1):
			for j in range(i+1,len(chash[key])):
				if chash[key][i] == chash[key][j]:
					print chash[key]
					c+=1
	return c

def CalcTarg_chash():
	print 'chash method'
	fill_chash()
	split=time.time()
	for xkey in chash:
		for x in chash[xkey]:
			ykeylist = [-xkey-2, -xkey-1, -xkey, -xkey+1]
			for ykey in ykeylist:
				if ykey in chash:
					for y in chash[ykey]:
						sum = x+y
						if sum<=10000 and x<>y:
							targ[sum]=1
	print 'len(targ.keys()) =',len(targ.keys()), '  time elapsed:',time.time()-split,'sec'

if len(sys.argv)==2:
  FileName = sys.argv[1]
else:
  FileName = 'algo1_programming_prob_2sum.txt'
Load( FileName )

CalcTarg_chash()
CalcTargBSearch()
#CalcTargNaive()
#print 'tcount =',tcount,
