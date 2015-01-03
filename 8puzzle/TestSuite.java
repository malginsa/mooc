// Simple test suite

public class TestSuite {
	private int errors; // numbers of fails tests
	private int count; // total number of tests
	public void assertEqual ( Comparable item1, Comparable item2, String message ) {
		count ++;
		if (item1 != item2) {
			errors++;
			StdOut.println("fails: "+message);
		}
	}
	public void tally () {
		StdOut.println((count-errors) + " tests passed, " + errors + " tests fails");
	}
}

