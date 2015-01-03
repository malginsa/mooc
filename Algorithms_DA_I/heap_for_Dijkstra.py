# heap module for Dijkstra alg
# keep track of minimum key value of elements
import array
import math

class MinHeap(object):
	def __init__(self, vlist, inf):
		self.size = len(vlist)
		self.dist = array.array('L',[inf]*self.size)
		self.vert = array.array('L',vlist)

	def Parent(self, i):	# return id of parent
		return int( math.floor( (i-1)/2))

	def LeftChild(self, i):	# return id of left child
		return 2*i+1

	def RightChild(self, i):	# return id of right child
		return (i+1)*2

	def PopMinDist(self):
		vroot = self.vert[0]
		self.dist[0] = self.dist[ self.size-1 ]
		self.vert[0] = self.vert[ self.size-1 ]
		self.size -= 1
		self.BubbleDown(0)
#		c=0
#		for id in range(self.size):
#			if self.dist[id] == 1000000: c+=1
#		print c,
		return vroot

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
				if self.dist[rc] < self.dist[lc]:
					exch = rc
			if self.dist[cid] <= self.dist[exch]:	# exchange isn't needed
				break
			self.dist[cid], self.dist[exch] = self.dist[exch], self.dist[cid]
			self.vert[cid], self.vert[exch] = self.vert[exch], self.vert[cid]
			cid = exch

	def BubbleUp(self,id):
		cid = id	# current id of bubble
		last = self.size-1	# last id
		while True:
			pid = self.Parent( cid )
			if pid < 0:
				break
			if self.dist[pid] <= self.dist[cid]:
				break
			self.dist[cid], self.dist[pid] = self.dist[pid], self.dist[cid]
			self.vert[cid], self.vert[pid] = self.vert[pid], self.vert[cid]
			cid = pid
		
	def SetDist(self,v,newdist):
		id = self.vert.index(v)
		olddist = self.dist[id]
		self.dist[id] = newdist
		if newdist > olddist:
			self.BubbleDown(id)
		if newdist < olddist:
			self.BubbleUp(id)

class NaiveHeap(object):
	def __init__(self,vlist,inf):
		self.heap = {}
		for v in vlist: # initially distance for vertices
			self.heap[v] = inf
		self.size = len( self.heap )
	def PopMinDist(self):	# return vert with min dist
		minkey = self.heap.keys()[0]
		for key in self.heap:
			if self.heap[key] < self.heap[minkey]:
				minkey = key
		self.heap.pop(minkey)
		self.size -= 1
#		c=0
#		for id in self.heap.keys():
#			if self.heap[id] == 1000000: c+=1
#		print c,
		return minkey
	def SetDist(self,v,dist):
		self.heap[v] = dist

