#!/usr/bin/env python
# Dijkstra alg
import sys

class Graph(object):
	def __init__(self, descr='', adj={}, source=1):
		self.descr = descr
		self.adj = dict.copy(adj)	# {{vert:lenght,...},...}
		self.vertices = len(adj)
		self.src = source	# source vert from which SP i calculated
		self.dist = {}
		self.inf = 1000000	# infinity
	def __str__(self):
		return str(self.adj)
	def Load(self, FileName):
		print "loading from",FileName,"...",
		inFile = open (FileName, 'r', 0)
		for line in inFile:
			if line[0] == '#':
				self.descr = line.strip()
				continue
			col = line.split()
			tail = int(col[0])
			neighb = {}
			for i in range(1,len(col)):
				[head,l] = col[i].split(',')
				neighb[int(head)] = int(l)
			self.adj[tail] = neighb
		self.vertices = len(self.adj)
		print "loaded,  ",self.vertices, "vertices "
		for v in self.adj: self.dist[v] = self.inf
	def StoreSmallEdges(self):
		f = open ('SmallEdges.txt', 'w', 0)
		for v in self.adj:
			f.write( str(v)+' ' )
			for n in self.adj[v]:
				if self.adj[v][n] < 1000:
					f.write( str(n)+','+str(self.adj[v][n])+' ' )
			f.write('\n')
		f.close()
	def chkConsist(self):	# chk rev edges if G is undirected by definition
		for v in self.adj:
			for neib in self.adj[v]:
				self.IsExistRevEdge(v,neib,self.adj[v][neib])
	def IsExistRevEdge(self,v,neib,l):
		if v not in self.adj[neib]:
			print 'not exists rev edge for ',v,'->',neib
		if l<>self.adj[neib][v]:
			print 'len mismach',v,'<->',neib
	def prnDist(self):
		for v in self.dist:
			print str(v)+'-'+str(self.dist[v]),
		print
	def prnAskDist(self):
		AskL = [7,37,59,82,99,115,133,165,188,197]
		for i in AskL:
			print self.dist[i],
		print

class Heap(object):
	def __init__(self,vlist,inf):
		self.heap = {}
		for v in vlist: # initially distance for vertices
			self.heap[v]=inf
	def PopNearest(self):
		minkey=self.heap.keys()[0]
		for key in self.heap:
			if self.heap[key]<self.heap[minkey]:
				minkey = key
		self.heap.pop(minkey)
		return minkey
	def SetDist(self,v,dist):
		self.heap[v] = dist

def Dijkstra(g):
	H = Heap(g.adj.keys(),g.inf)
	g.dist[g.src] = 0
	H.SetDist(g.src,0)
	while H.heap <> {}:
		nearest = H.PopNearest()
		for v in g.adj[nearest]:	# for every neighb of nearest
			if g.dist[v] > g.dist[nearest] + g.adj[nearest][v]:
				g.dist[v] = g.dist[nearest] + g.adj[nearest][v]
				H.SetDist(v,g.dist[v])

G = Graph(source=1)
if len(sys.argv)==2:
	FileName = sys.argv[1]
else:
	FileName = 'dijkstraData.txt'
G.Load(FileName)
#G.chkConsist()

#print G.descr
Dijkstra(G)
G.prnAskDist()
#G.prnDist()

