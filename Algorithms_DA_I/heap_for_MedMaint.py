# heap module for Median Maintanance alg
# keep track of minimum key value of elements
import array
import math

class MinHeap(object):
	def __init__(self, maxsize, inf):
		self.size = 0
		self.key = array.array('L',[0]*maxsize)

	def Parent(self, i):	# return id of parent
		return int( math.floor( (i-1)/2))

	def LeftChild(self, i):	# return id of left child
		return 2*i+1

	def RightChild(self, i):	# return id of right child
		return (i+1)*2

	def PopMinDist(self):
		rootkey = self.key[0]
		self.key[0] = self.key[ self.size-1 ]
		self.size -= 1
		self.BubbleDown(0)
#		c=0
#		for id in range(self.size):
#			if self.dist[id] == 1000000: c+=1
#		print c,
		return rootkey

	def BubbleDown(self,id):
		cid = id	# current id of bubble
		last = self.size-1	# last id
		while True:
			lc = self.LeftChild( cid )	# id of left child
			rc = self.RightChild( cid )	# id of right child
			if lc > last:	# vbubble doesn't have leaves
				break
			exch = lc	# left leaf is candidate to exchange
			if rc <= last:	# right leaf presented too
				if self.key[rc] < self.key[lc]:
					exch = rc
			if self.key[cid] <= self.key[exch]:	# exchange isn't needed
				break
			self.key[cid], self.key[exch] = self.key[exch], self.key[cid]
			cid = exch

	def BubbleUp(self,id):
		cid = id	# current id of bubble
		last = self.size-1	# last id
		while True:
			pid = self.Parent( cid )
			if pid < 0:
				break
			if self.key[pid] <= self.key[cid]:
				break
			self.key[cid], self.key[pid] = self.key[pid], self.key[cid]
			cid = pid
		
	def SetDist(self,v,newkey):
		id = self.vert.index(v)
		oldkey = self.key[id]
		self.key[id] = newkey
		if newkey > oldkey:
			self.BubbleDown(id)
		if newkey < oldkey:
			self.BubbleUp(id)

class NaiveHeap(object):
	def __init__(self,vlist,inf):
		self.heap = {}
		self.size = len( self.heap )
	def PopMin(self):	# return vert with min dist
		minkey = self.heap.keys()[0]
		for key in self.heap:
			if self.heap[key] < self.heap[minkey]:
				minkey = key
		self.heap.pop(minkey)
		self.size -= 1
		return minkey
	def Insert(self,key):
		self.heap[key] = key

