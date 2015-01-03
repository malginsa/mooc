#!/usr/bin/env python

balance = 999999
annualInterestRate = 0.18
#monthlyPaymentRate = 0.04

def years_payment(Mpay):
	'''
	calculate pyment for credit
	Mpay - fixed montly payment
	return - remaining balance in under 1 year
	'''
	total_pay = 0
	local_balance = balance
	for i in range(1,13):
		pay = Mpay
		total_pay += pay
		local_balance -= pay
		interest = annualInterestRate / 12.0 * local_balance
		local_balance += interest
	return local_balance
	
lower = balance / 12.0
upper = balance * pow((1+annualInterestRate/12.0),12) / 12.0
Mpay = (upper+lower)/2.0

rest = years_payment(Mpay)
while abs(rest) >= 0.01:
#	print 'rest = ','%.2f'%rest
	if rest > 0:
		lower = Mpay
	else:
		upper = Mpay
	Mpay = (upper+lower)/2.0
	rest = years_payment(Mpay)

print 'Lowest Payment: ','%.2f'%Mpay

