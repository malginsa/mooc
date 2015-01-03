#!/usr/bin/env python
#
# Random numbers generator (uniq, first n integers)
#
import math
import sys
from random import randrange

if (len(sys.argv) == 2):
	n = int(sys.argv[1])
else:
	n = 7
arr = [i for i in range(n)]
with open( 'Array'+str(n)+'.txt', 'w+') as f:
	for i in range(n):
		j = randrange(len(arr))
		f.write(str( arr[j] )+'\n')
		del arr[j]
f.close()

