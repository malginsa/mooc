/*
Unit tests for cache with LRU page replacement algorithm

Compiling: javac TestLRU.java
Running: java TestLRU
*/

public class TestLRU
{
	public static void main(String[] args)
	{
		Cache<String> cache = new Cache<String>(3, Cache.Strategy.LRU);
		TestSuite ts = new TestSuite();
		ts.assertEQ(cache.get(1), null, "test 1.0");
		ts.assertEQ(cache.contains(1), false, "test 1.1");

		cache.put(1, "first");
		ts.assertEQ(cache.contains(1), true, "test 2.0");
		ts.assertEQ(cache.get(2), null, "test 2.1");
		ts.assertEQ(cache.get(1), "first", "test 2.2");

		cache.put(2, "second");
		cache.put(3, "third");
		ts.assertEQ(cache.get(1), "first", "test 3.0");
		ts.assertEQ(cache.get(2), "second", "test 3.1");
		ts.assertEQ(cache.get(3), "third", "test 3.2");
		ts.assertEQ(cache.get(4), null, "test 3.3");

		cache.put(4, "fourth");
		ts.assertEQ(cache.contains(1), false, "test 4.0");
		ts.assertEQ(cache.get(1), null, "test 4.1");
		ts.assertEQ(cache.get(2), "second", "test 4.2");
		ts.assertEQ(cache.get(3), "third", "test 4.3");
		ts.assertEQ(cache.get(4), "fourth", "test 4.4");

		cache.put(5, "fifth");
		cache.put(6, "sixth");
		ts.assertEQ(cache.get(5), "fifth", "test 5.0");
		cache.put(7, "seventh");
		cache.put(8, "eighth");
		ts.assertEQ(cache.get(5), "fifth", "test 5.1");
		ts.assertEQ(cache.get(8), "eighth", "test 5.2");
		ts.assertEQ(cache.get(7), "seventh", "test 5.3");
		ts.assertEQ(cache.get(6), null, "test 5.4");
		ts.tally();
	}
}
