// linked list version of Deque. add/remove to/from beginning/end.
// Constant time operation
// linear memory consumption

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private int N;
	private Node first;
	private Node last;

	private class Node 
	{ // nested class to define nodes
		Item item;
		Node next;
		Node prev;
	}

	public boolean isEmpty()
	// is the deque empty?
	{ return N == 0; }

	public int size()
	// return the number of items on the deque
	{ return N; }

	public void addFirst(Item item)
	{ // insert the item at the front
		if (item == null)
			throw new NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		if (N == 0) last = first;
		else { 
			first.next = oldfirst;
			oldfirst.prev = first;
		}
		N++;
	}

	public void addLast(Item item)
	{ // insert the item at the end
		if (item == null)
			throw new NullPointerException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		if (N == 0) first = last;
		else {
			oldlast.next = last;
			last.prev = oldlast;
		}
		N++;
	}

	public Item removeFirst()
	{	// delete and return the item at the front
		if (size() < 1)
			throw new java.util.NoSuchElementException();
		Item val = first.item;
		if (size() == 1)
		{
			first = null;
			last = null;
			N = 0;
		}
		else
		{
			first = first.next;
			first.prev = null;
			N--;
		}
		return val;
	}

	public Item removeLast()
	{ // delete and return the item at the end
		if (size() < 1)
			throw new java.util.NoSuchElementException();
		Item val = last.item;
		if (size() == 1)
		{
			first = null;
			last = null;
			N = 0;
		}
		else
		{
			last = last.prev;
			last.next = null;
			N--;
		}
		return val;
	}

	public Iterator<Item> iterator()
	// return an iterator over items in order from front to end
	{ return new ListIterator(); }

	private class ListIterator implements Iterator<Item>
	{
		private Node current = first;
		public boolean hasNext() { return current != null; }
		public Item next() 
		{ 
			if (current == null)
				throw new java.util.NoSuchElementException();
			Item val = current.item;
			current = current.next;
			return val;
		}
		public void remove() 
		{ throw new UnsupportedOperationException(); }
	}
	
	public static void main(String[] args) { // unit testing
		Integer tmp;
		Deque<Integer> deq = new Deque<Integer>();
		TestSuite test = new TestSuite();
		test.assertEqual (deq.size(),0,"test #1");
		test.assertEqual (deq.isEmpty(),true,"test #2");

		deq.addFirst(1);
		test.assertEqual (deq.size(),1,"test #3");
		test.assertEqual (deq.isEmpty(),false,"test #4");
		test.assertEqual (deq.first == deq.last,true,"test #5");
		
		deq.addLast(2);
		test.assertEqual (deq.size(),2,"test #6");
		test.assertEqual (deq.first == deq.last,false,"test #7");
		
		tmp = deq.removeLast();
		StdOut.println("last removed: " + tmp);
		test.assertEqual (deq.size(),1,"test #8");
		test.assertEqual (deq.isEmpty(),false,"test #9");
		test.assertEqual (deq.first == deq.last,true,"test #10");

		tmp = deq.removeLast();
		StdOut.println("last removed: " + tmp);
		test.assertEqual (deq.size(),0,"test #11");
		test.assertEqual (deq.isEmpty(),true,"test #12");

		test.tally();

		for (Integer val : deq) StdOut.println(val);
	}
}

