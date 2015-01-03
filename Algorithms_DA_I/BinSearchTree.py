#!/usr/bin/env python
from collections import deque

class Node(object):
	def __init__(self,parent=None,left=None,right=None,subtreesize=1):
		self.parent = parent
		self.left = left
		self.right = right
		self.subtreesize = subtreesize
	def __str__(self):
		return str(self.parent)+'-'+str(self.left)+'-'+str(self.right)+'-'+str(self.subtreesize)

class BinSearchTree(object):
	def __init__(self):
		self.node = {}
		self.rootid = None
	def __str__(self):
		ret = ''
		for id in self.node:
			if id == self.rootid:
				ret += 'root '+str(id)+': '+str(self.node[id])+'\n'
			else:
				ret += str(id)+': '+str(self.node[id])+'\n'
		return ret

	def AddRoot(self,id):
		self.node[id] = Node()
		self.rootid = id
		self.node[id].subtreesize = 1

	def AddNode(self,id):
		candparent = self.rootid	# candidate to be parent node
		while True:
			self.node[candparent].subtreesize += 1
			if id < candparent:	# go to the left
				if self.node[candparent].left == None:
					self.node[candparent].left = id
					self.node[id] = Node(parent=candparent)
					break
				else:
					candparent = self.node[candparent].left
			else:	# go to the right
				if self.node[candparent].right == None:
					self.node[candparent].right = id
					self.node[id] = Node(parent=candparent)
					break
				else:
					candparent = self.node[candparent].right

	def Rank(self,key,idST):	# rank of key from id SubTree
		if idST == None:
			return 0
		if idST == key:
			if self.node[idST].left <> None:
				return self.node[ self.node[idST].left ].subtreesize + 1
			else:
				return 1
		if key < idST:	# go to the left
			return self.Rank(key, self.node[idST].left)
		else:
			return self.node[ self.node[idST].left ].subtreesize + 1 + self.Rank(key, self.node[idST].right )
			

	def OrderStat(self,ith,idST):	# i-th order statistic from id SubTree
		if self.node[idST].left == None:
			leftSTSize = 0
		else:
			leftSTSize = self.node[ self.node[idST].left ].subtreesize
		if ith == (leftSTSize+1):
			return idST
		if ith < (leftSTSize+1):
			return self.OrderStat( ith, self.node[idST].left)
		else:
			return self.OrderStat( ith-leftSTSize-1, self.node[idST].right)

	def MaxId(self,id):	# return max id of sub-tree from id node
		if self.node[id].right <> None:
			return self.MaxId( self.node[id].right )
		else:
			return id

	def Predessor(self,id):
		predid = None
		if self.node[id].left <> None:
			predid = self.MaxId( self.node[id].left )
		return predid

	def prnInOrder(self, id):
		if self.node[id].left <> None:
			self.prnInOrder( self.node[id].left )
		if id == self.rootid:
			print str(id)+'r',
		else:
			print id,
		if self.node[id].right <> None:
			self.prnInOrder( self.node[id].right )

	def prnTree(self):
		q = deque([self.rootid])
		clayer = 0
		while len(list(q)) <> 0:
			layer = list(q)
			q.clear()
			prn = ''
			fields = (2**(clayer)+1)
			fsize = (64-2**(clayer+1))/fields
			for id in layer:
				if id <> 0:
#					prn += ' '*fsize+str('%2d'%id)+'-'+str(self.node[id].subtreesize)
					prn += ' '*fsize+str('%2d'%id)
				else:
					prn += ' '*fsize + '  '
				if id <> 0:
					if self.node[id].left <> None:
						q.append( self.node[id].left )
					else:
						q.append(0)
					if self.node[id].right <> None:
						q.append( self.node[id].right )
					else:
						q.append(0)
				else:
					q.append(0)
					q.append(0)
			print prn
			if q.count(0) == len(list(q)):
				break
			clayer += 1

	def DecreaseSTSize(self,id):	# decrease SubTreeSize of id and all ancesory
		if id == None:
			return
		else:
			self.node[id].subtreesize -= 1
			self.DecreaseSTSize( self.node[id].parent )

	def Delete(self, did):	# did = id of node to be deleted
		pid = self.node[did].parent
		# no childs
		if self.node[did].left == self.node[did].right == None:
			# remove parent's link to deleted node
			if pid == None:
				self.rootid = None
			else:
				if did < pid:
					self.node[pid].left = None
				else:
					self.node[pid].right = None
			self.DecreaseSTSize(pid)
			del self.node[did]
			return
		# only one child exists
		if (self.node[did].left == None) or (self.node[did].right == None):
			cid = self.node[did].left	# which one?
			if self.node[did].right <> None:
				cid = self.node[did].right
			if pid == None:
				self.rootid = cid
			else:
				if did < pid:	# did is left child for parent or right?
					self.node[pid].left = cid
				else:
					self.node[pid].right = cid
			self.node[cid].parent = pid
			self.DecreseSTSize(pid)
			del self.node[did]
			return
		# both children are exist
		predid = self.Predessor(did)
		self.Delete(predid)
		lid = self.node[did].left
		rid = self.node[did].right
		self.node[ predid ] = self.node[did]
		if pid == None:
			self.rootid = predid
		else:
			if did < pid:	# did is left child for parent or right?
				self.node[pid].left = predid
			else:
				self.node[pid].right = predid
		if lid <> None:
			self.node[lid].parent = predid
		if rid <> None:
			self.node[rid].parent = predid
		del self.node[did]
		return

bst = BinSearchTree()
bst.AddRoot(7)
bst.AddNode(4)
bst.AddNode(5)
bst.AddNode(11)
bst.AddNode(9)
bst.AddNode(14)
bst.AddNode(8)
bst.AddNode(2)
bst.AddNode(10)

#bst.Delete(2)
#bst.prnInOrder(bst.rootid)
#print
#print bst
bst.prnTree()
#print bst.OrderStat(6,bst.rootid)
#print bst.Rank(9,bst.rootid)
