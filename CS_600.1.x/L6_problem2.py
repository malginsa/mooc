#!/usr/bin/env python

aTup = ('I', 'am', 'a', 'test', 'tuple')

res = ()
for i in range( len(aTup) ):
	if i%2 == 0:
		res += ( aTup[i], )
print res
