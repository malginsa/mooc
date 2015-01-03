#!/usr/bin/env python

coins = [0, 1, 2, 3, 5]
res = []
i5 = [0 for x in range(10)]

def inc_base5_digit(i, digit):
    if i[digit] == 4:
        i[digit] = 0
        if digit == 0:
            return inc_base5_digit(i, 9)
        else:
            return inc_base5_digit(i, digit - 1)
    else:
        i[digit] += 1
        return i

def inc_base5(i):
    return inc_base5_digit(i, 9)

for i in range(9765625):
    i_set = [coins[x] for x in i5]
    i_set.sort()
    if (sum(i_set) == 10) and (i_set not in res):
        res.append(i_set)
        print i, '\t', len(res), '\t',  i_set
    inc_base5(i5)
