#!/usr/bin/env python

def isIn(char, aStr):
    if aStr == '':
    	return False
    if char == aStr[ len(aStr)/2 ]:
    	return True
    elif len(aStr) == 1:
    	return False
    elif char > aStr[ len(aStr)/2 ]:
    	return isIn (char, aStr[ len(aStr)/2+1 : ] )
    else:
    	return isIn (char, aStr[ : len(aStr)/2 ] )

print isIn ('m', '')
print isIn ('m', 'm')
print isIn ('m', 'mq')
print isIn ('m', 'ym')
print isIn ('m', 'dfgdfd')
print isIn ('m', 'mm m')
print isIn ('m', 'jnciecieiqcmyv')

