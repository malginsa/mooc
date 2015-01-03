#!/usr/bin/env python

start = 0
end = 100
ans = (start + end) / 2
print 'Please think of a number between 0 and 100!'
print 'Is your secret number '+str(ans)+'?'
rep = raw_input ("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")
while rep != 'c':
	if rep == 'h':
		end = ans
		ans = (start + end) / 2
	elif rep == 'l':
		start = ans
		ans = (start + end) / 2
	else:
		print 'Sorry, I did not understand your input.'
	print 'Is your secret number '+str(ans)+'?'
	rep = raw_input ("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")
print 'Game over. Your secret number was: '+str(ans)
		

#x = 25
#epsilon = 0.01
#step = 0.1
#guess = 0.0
#
#while abs(guess**2-x) >= epsilon:
#    if guess <= x:
#        guess += step
#    else:
#        break
#
#if abs(guess**2 - x) >= epsilon:
#    print 'failed'
#else:
#    print 'succeeded: ' + str(guess)

#print 3/8.

#num = raw_input('Enter number : ')
#num = int(num)
#if num < 0:
#	isNeg = True
#	num = abs(num)
#else:
#	isNeg = False
#res = ''
#if num == 0:
#	res = '0'
#while num > 0:
#	res = str(num % 2) + res
#	num = num/2
#if isNeg:
#	res = '-' + res
#print res

#count = 0
#for letter in 'Snow!':
#    print 'Letter # ' + str(count) + ' is ' + str(letter)
#    count += 1
#    break
#print count 
#
#for variable in range(20):
#    if variable % 4 == 0:
#        print variable
#    if variable % 16 == 0:
#        print 'Foo!' 
#
#print 
#i = 7
#j = 3
## sign '/' discard fractional part
#print i / j
#j = -3
#print i / j
#print int(3.9)
#print int(-3.9)
#
#print 10.0 / 3.0
#
#print round(2.6)
#print int(2.6)
#
#print 'a' * 3
#print 'abc'[0]
#print 'abc'[-1]

