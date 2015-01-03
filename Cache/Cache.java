/*
The simple "Cache" implementation with FIFO and LRU page replacement algorithms
The Cache stores objects with keys of type Integer and arbitrary type of values

LRU uses Indexed Minimum Priority Queue,
	for details see http://algs4.cs.princeton.edu/code/javadoc/IndexMinPQ.html

Compiling: javac Cache.java
*/

import java.util.TreeMap;
import java.util.Queue;
import java.util.LinkedList;

public class Cache<Value>
{
	public enum Strategy { FIFO, LRU }

	private final Strategy strategy;
	private int capacity; // cache capacity
	private int amount; // amount of objects in the cache
	private TreeMap<Integer, Value> treemap; // data structure for objects

	private Queue<Integer> queue; // keystore (FIFO strategy)
	private long priority; // constantly increasing object's priority, used for LRU strategy only
	private IndexMinPQ minpq; // keystore (LRU strategy)
	private CrossTable ctable; // Auxiliary structure for binding cache keys and IndexMinPQ indicies

	// Initialize an empty cache with the capacity and the stratagy FIFO or LRU
	public Cache (int capacity, Strategy strategy)
	{
		this.strategy = strategy;
		this.amount = 0;
		this.capacity = capacity;
		treemap = new TreeMap<Integer, Value>();

		if (this.strategy == Strategy.FIFO)
			queue = new LinkedList<Integer>();
		else // LRU strategy
		{
			this.priority = 0;
			minpq = new IndexMinPQ<Integer>(this.capacity);
			ctable = new CrossTable(this.capacity);
		}
	}

	// put an object to the cache
	// overwrite the value if the key aleady exists
	public void put(int key, Value value)
	{
		if (this.amount == this.capacity)
		{ // cache is full, remove one object
			int displaced_key; // the object's key to be removed from the cache
			if (this.strategy == Strategy.FIFO)
				displaced_key = queue.remove();
			else // LRU strategy
				displaced_key = ctable.del(minpq.delMin());
			treemap.remove(displaced_key);
		}
		else
			this.amount++;

		treemap.put(key, value);

		if (this.strategy == Strategy.FIFO)
			queue.add(key);
		else // LRU strategy
		{
			int indx = ctable.add(key); // acquire index for minpq
			minpq.insert(indx, this.priority++);
		}
	}

	// return the Value of an object assosiated with the Key
	// return null if an object doesn't exist in the cache
	public Value get(int key)
	{
		Value value = treemap.get(key);
		if (value != null && this.strategy == Strategy.LRU)
			minpq.increaseKey(ctable.indx(key), this.priority++);
		return value;
	}

	// is there an object with the Key in the the cache?
	public boolean contains(int key)
	{
		return treemap.containsKey(key);
	}

	// unit tests the Cache data type.
	public static void main(String[] args)
	{
		// See TestFIFO and TestLRU libraries
	}
}
