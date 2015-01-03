#!/usr/bin/env python

balance = 3926
annualInterestRate = 0.2
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
#		print 'Month: ',i
#		pay = balance * monthlyPaymentRate
		pay = Mpay
#		print 'Minimum monthly payment: ', '%.2f'%pay
		total_pay += pay
		local_balance -= pay
		interest = annualInterestRate / 12.0 * local_balance
		local_balance += interest
#		print 'Remaining balance: ', '%.2f'%balance
#	print 'Total paid: ', '%.2f'%total_pay
#	print 'Remaining balance: ', '%.2f'%balance
	return local_balance
	
Mpay = 10
while years_payment(Mpay) >= 0:
	Mpay += 10

print 'Lowest Payment: ',Mpay

