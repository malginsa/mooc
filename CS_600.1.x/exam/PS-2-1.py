#!/usr/bin/env python
x = [2,1]
y = [2,1]
z = False
if x == y:
    if sorted(x) == sorted(y):
         if x.sort() == y.sort():
               z =  x.sort() == sorted(y)
print z
