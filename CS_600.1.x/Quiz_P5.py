#!/usr/bin/env python

def laceStrings(s1,s2):
	if len(s1) > len(s2):
		maxlen = len(s1)
	else:
		maxlen = len(s2)
	res = ''
	for i in range(maxlen):
		if i < len(s1):
			res += s1[i]
		if i < len(s2):
			res += s2[i]
	return res

print laceStrings('','')
print laceStrings('12','ab')
print laceStrings('1','ab')
print laceStrings('12','a')
print laceStrings('','a')
print laceStrings('1','')
