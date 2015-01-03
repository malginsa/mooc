#!/usr/bin/env python

def genPrimes():
	primes=[2]
	yield 2
	while True:
		candidate = primes[-1]
		candidateIsPrime = False
		while not candidateIsPrime:
			candidate += 1
			candidateIsPrime = True
			for prime in primes:
				if (candidate % prime) == 0:
					candidateIsPrime = False
					break
		yield candidate
		primes.append( candidate )

prime = genPrimes()
for i in range(10):
	print prime.next()
