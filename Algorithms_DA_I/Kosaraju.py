#!/usr/bin/env python
#  Computing counts of Strongly Connected Components in directed graph
import sys
import time

class Vertex(object):
	def __init__(self):
		self.adj = []	# list of adjacent vertices
		self.visited=False
		self.leader=0 # leader id of SCC (Strongly Connected Component) for 2-nd phase

class Graph(object):
	def __init__(self):
		self.descr = ''	# description
		self.vertices = 0
		self.vertex = {}	# key = id of vertex
		self.arcs = []
		self.walk=[]
	def __str__(self):
		ret = 'descr:'+self.descr+'\n'+'arcs:'+str(self.arcs)+\
			'\n'+str(self.vertices)+' vertices:'+'\n'
		for id in self.vertex:
			ret=ret+'\t'+str(id)+' '+str(self.vertex[id].adj)+' '+\
				str(self.vertex[id].visited)+' '+\
				str(self.vertex[id].leader)+'\n'
		return ret

	def Load(self,FileName):
		print "loading arcs from",FileName,"...",
		inFile = open (FileName, 'r', 0)
		for line in inFile:
			if line[0] == '#':
				self.descr = line.strip()
				continue
			self.arcs.append( [int(s) for s in line.split()] )
		print "loaded"
		self.BuildGraphFromArcsList()

	def BuildGraphFromArcsList(self):
		print 'filling up vertex adj',
		carcs = len(self.arcs)/10
		i=0
		for [v1,v2] in self.arcs:	# for each pair of vertices
			if v1 not in self.vertex:	# is the firts a new vertex?
				self.vertex[v1]=Vertex()
			self.vertex[v1].adj.append(v2)
			if v2 not in self.vertex:	# is the second a new vertex?
				self.vertex[v2]=Vertex()
			i+=1
			if i >= carcs:
				print '.',
				i=0
		print 'done\nsorting arjacencies...',
		for id in self.vertex: # uniquely DFS traverse
			self.vertex[id].adj.sort()
		self.vertices = len(self.vertex)
		print 'done\nfilling up walk list...',
		self.walk = self.vertex.keys()
		self.walk.sort(reverse=True)
		print 'done'

	def fillSCC(self):
		self.SCC = {}
		for id in self.vertex:
			if self.vertex[id].leader in self.SCC:
				self.SCC[ self.vertex[id].leader ] +=1
			else:
				self.SCC[ self.vertex[id].leader ] = 1

	def prnSCC(self):
		print self.descr
		sortedSCC = self.SCC.values()
		sortedSCC.sort(reverse=True)
		print '5 biggest SCCs:', sortedSCC[:5]

def CreateRev(G):
	Grev=Graph()
	Grev.descr = '#reverse of graph '+G.descr[1:]
	carcs=len(G.arcs)/10
	i=0
	split = time.time()
	print 'creating arcs list of reverse graph',
	for [v1,v2] in G.arcs:	# copy arcs in reverse order
		Grev.arcs.append([v2,v1])
		i+=1
		if i>=carcs:
			print '.',
			i=0
	print ' done, took %.1f'%(time.time()-split),'sec'
	Grev.BuildGraphFromArcsList()
	return Grev

def DFSloop(G):
	global activeG
	global nextWalk
	global TeamLeader
	nextWalk = []
	activeG = G
	for id in G.walk:
		if not G.vertex[id].visited:
			TeamLeader = id
			DFS_stack(id)
#			print 'DFS called from DFSloop for',id,'vertex'
	G.walk = nextWalk[:]
	split = time.time()
	G.walk.reverse()
	G.fillSCC()
	print 'reverse walk & fillSCC took %.1f'%(time.time()-split),'sec'

def DFS_stack(v):
	stack = [v]
	activeG.vertex[v].visited = True
	activeG.vertex[v].leader = TeamLeader
	while len(stack) > 0:
		top = stack[len(stack)-1]
		try: 
			neib = activeG.vertex[top].adj.pop(0)	# a nightbour exists?
			if activeG.vertex[neib].visited:
				continue
			stack.append(neib)
			activeG.vertex[neib].visited = True
			activeG.vertex[neib].leader = TeamLeader
		except:	# no neighbours
			stack.pop()	# remove top element from stack
			nextWalk.append(top)

def DFS(v):
	activeG.vertex[v].visited = True
	activeG.vertex[v].leader = TeamLeader
	for neib in activeG.vertex[v].adj:
		if not activeG.vertex[neib].visited:
			DFS(neib)
	nextWalk.append(v)

G = Graph()
if len(sys.argv)==2:
	FileName = sys.argv[1]
else:
	FileName = 'SCCtest1.txt'

split = time.time()
G.Load(FileName)
print 'load & build graph took %.1f'%(time.time()-split),'sec'
split = time.time()
Grev=CreateRev(G)
print 'create & build revgraph took %.1f'%(time.time()-split),'sec'
split = time.time()
DFSloop(Grev)
G.walk=Grev.walk[:]
DFSloop(G)
G.prnSCC()
print '2 phases took %.1f'%(time.time()-split),'sec'

