#!/usr/bin/env python

def fib(x):
	''' assumes x is an int and >= 0
	returns Fibonacci of x'''
	assert type(x) == int and x >= 0
	if x == 0 or x == 1:
		return 1
	else:
		return fib(x-1) + fib(x-2)

print fib(7)
print fib(-2)
print fib(t)
