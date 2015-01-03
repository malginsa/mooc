// array Queue implementation with random access

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] arr = (Item[]) new Object[1];
	private int N = 0;

	public RandomizedQueue()
		// construct an empty randomized queue
	{	}

	private void resize(int max)
		// resize array arr to max length
	{
		Item[] tmp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++)
			tmp[i] = arr[i];
		arr = tmp;
	}

	public boolean isEmpty()
		// is the queue empty?
		{ return N == 0; }

	public int size()
		// return the number of items on the queue
		{ return N; }

	public void enqueue(Item item)
		// add the item
	{
		if (item == null)
			throw new NullPointerException();
		if (N == arr.length)
			resize(arr.length*2);
		arr[ N++ ] = item;
	}

	public Item dequeue()
		// delete and return a random item
	{
		if (N == 0)
			throw new java.util.NoSuchElementException();
		int randind = StdRandom.uniform(N);
		Item ret = arr[randind];
		arr[randind] = arr[--N];
		arr[N] = null;
		if (N > 0 && N < arr.length/4)
			resize(arr.length/2);
		return ret;
	}

	public Item sample()
		// return (but do not delete) a random item
	{
		if (N == 0)
			throw new java.util.NoSuchElementException();
		int randind = StdRandom.uniform(N);
		return arr[randind];
	}

	public Iterator<Item> iterator()
		// return an independent iterator over items in random order
		{ return new RandomIterator(); }

	private class RandomIterator implements Iterator<Item>
		// random order iteration
	{
		private Item[] list = (Item[]) new Object[N];
		private int current = 0; // index of current element in list for iteration

		public RandomIterator()
			// filling up list with arr elements in random order
		{
			int border = N;
			for (int listind = 0; listind < N; listind++)
			{
				int randind = StdRandom.uniform(border);
				list[listind] = arr[randind];
				arr[randind] = arr[--border];
			}

		}
		public boolean hasNext()
			{ return current < N; }
		public Item next()
		{ 
			if (current >= N)
				throw new java.util.NoSuchElementException();
			return list[current++];
		}
		public void remove()
			{ throw new UnsupportedOperationException(); }
	}

	public static void main(String[] args)
		// unit testing
	{
//		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
//		for (int i = 0; i < 1000; i++)
//			rq.enqueue(i);
//		for (Integer val : rq)
//			StdOut.println(val);
//			StdOut.print(val + " ");
//		StdOut.println();

//		TestSuite test = new TestSuite();
//		Integer tmp;
//
//		test.assertEqual (rq.size(),0,"test #1");
//		test.assertEqual (rq.isEmpty(),true,"test #2");
//
//		rq.enqueue(1);
//		test.assertEqual (rq.size(),1,"test #3");
//		test.assertEqual (rq.isEmpty(),false,"test #4");
//
//		tmp = rq.dequeue();
//		test.assertEqual (rq.size(),0,"test #5");
//		test.assertEqual (rq.isEmpty(),true,"test #6");
//
//    test.tally();

//		rq.enqueue(1);
//		rq.enqueue(2);
//		rq.enqueue(3);
//		rq.enqueue(4);
//		StdOut.println(rq.dequeue());
//		StdOut.println(rq.dequeue());
//		StdOut.println(rq.dequeue());
//		StdOut.println(rq.dequeue());
	}
}
