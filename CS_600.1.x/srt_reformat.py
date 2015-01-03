#!/usr/bin/env python
import glob
import string
import re

for ifiname in glob.glob('*.srt'):
	ifi = open (ifiname, 'r')
	ofi = open ( string.split(ifiname,'.srt')[0]+'.txt', 'w+')
	for s in ifi:
		if s=='\n':
			continue
		match = re.search('^\d+$',s)
		if match:
			continue
		match = re.search('^\d\d:\d\d:\d\d,\d\d\d --> \d\d:\d\d:\d\d,\d\d\d$',s)
		if match:
			continue
		ofi.write(s)
	ofi.close()

