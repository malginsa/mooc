#!/usr/bin/env python

import math

def deviat(x_list):
	''' return standart deviation of variables in x_list '''
	s = .0	# sum of variables
	ss = .0	# sum of quadrat's variables
	for x in x_list:
		s += x
		ss += x*x
	n = len( x_list )
	return math.sqrt( (ss - s*s/n) / n )

def deviat_p(x_list, p_list):
	''' return standart deviation of variables in x_list '''
	M = .0
	MM = .0
	for i in range(len(x_list)):
		M += x_list[i] * p_list[i]
		MM += x_list[i]**2 * p_list[i]
	return math.sqrt( MM - M**2 )

class SI:

#	def __init__(self, [name, metro, white, hs_grad, poverty, female_house]):
	def __init__(self, var):
		self.name = var[0]
		self.metro = float(var[1])
		self.white = float(var[2])
		self.hs_grad = float(var[3])
		self.poverty = float(var[4])
		self.female_house = float(var[5])

	def __str__(self):
		return self.name + '\t' + \
			self.metro + '\t' + \
			self.white + '\t' + \
			self.hs_grad + '\t' + \
			self.poverty + '\t' + \
			self.female_house

si = []
f = open('states.csv')
f.readline()	# header
content = f.readlines()
for line in content:
	si.append(SI(line.split(',')))

M_poverty = .0
M_hs_grad = .0
for info in si:
	M_poverty += info.poverty
	M_hs_grad += info.hs_grad
M_poverty = M_poverty / len(si)
M_hs_grad = M_hs_grad / len(si)

print M_poverty, M_hs_grad

poverty = []
for info in si:
	poverty.append(info.poverty)
print deviat(poverty)

hs_grad = []
for info in si:
	hs_grad.append(info.hs_grad)
print deviat(hs_grad)

print deviat_p([37./15, 2.8], [0.3, 0.7])
