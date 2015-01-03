// Supplementary class for Cache.java
// binding cache keys and minpq indicies

import java.util.TreeMap;
import java.util.Queue;
import java.util.LinkedList;

public class CrossTable
{
	private TreeMap<Integer, Integer> cross; // 
	private int[] keys; //
	private Queue<Integer> vacant; //

	public CrossTable(int capacity)
	{
		keys = new int[capacity];
		cross = new TreeMap<Integer, Integer>();
		vacant = new LinkedList<Integer>();
		for (int i = 0; i < capacity; i++)
			vacant.add(i);
	}

	public int add(int key)
	{
		int indx = vacant.remove();
		keys[indx] = key;
		cross.put(key, indx);
		return indx;
	}

	public int del(int indx)
	{
		vacant.add(indx);
		cross.remove(keys[indx]);
		return keys[indx];
	}

	public int indx(int key)
	{
		return cross.get(key);
	}

	public String toString()
	{
		return "vacant: " + vacant + "\n" 
			+ "cross: " + cross + "\n";
	}

	public static void main(String[] args)
	{
		TestSuite ts = new TestSuite();
		CrossTable ctable = new CrossTable(4);
		ts.assertEQ( ctable.add(5), 0, "test 1.0");
		ts.assertEQ( ctable.add(3), 1, "test 1.1");
		ts.assertEQ( ctable.add(1), 2, "test 1.2");
		ts.assertEQ( ctable.add(12), 3, "test 1.3");

		ts.assertEQ( ctable.del(2), 1, "test 2.0");
		ts.assertEQ( ctable.add(4), 2, "test 2.1");

		ts.assertEQ( ctable.del(0), 5, "test 3.0");
		ts.assertEQ( ctable.del(2), 4, "test 3.1");
		ts.assertEQ( ctable.add(7), 0, "test 3.2");
		ts.assertEQ( ctable.add(8), 2, "test 3.3");

		ts.assertEQ( ctable.del(3), 12, "test 4.0");
		ts.assertEQ( ctable.del(0), 7, "test 4.1");

		ts.tally();

		System.out.println(ctable);
	}
}
