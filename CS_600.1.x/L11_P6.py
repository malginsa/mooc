#!/usr/bin/env python

class Queue():
  ''' FIFO queue '''
  def __init__(self):
    self.vals = []
  def insert(self, e):
    self.vals.append(e)
  def remove(self):
    try:
      ret = self.vals[0]
#      print 'I popped ' + str(self[0]) + ' from ' + str(self)
      self.vals = self.vals[1:]
    except:
      raise ValueError()
    return ret


