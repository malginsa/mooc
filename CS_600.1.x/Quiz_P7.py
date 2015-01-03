#!/usr/bin/env python

def McNuggets(n):
	res = False
	for a in range(n/6+1):
		for b in range(n/9+1):
			for c in range(n/20+1):
				if (6*a + 9*b + 20*c) == n:
					res = True
	return res

print McNuggets(15)
print McNuggets(16)
print McNuggets(6)
print McNuggets(9)
print McNuggets(20)
print McNuggets(35)
#print McNuggets(16)

