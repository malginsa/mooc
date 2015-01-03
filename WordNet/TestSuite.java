// Simple test suite

public class TestSuite {
  private int errors; // numbers of fails tests
  private int count; // total number of tests
  public void assertEQ(Comparable item1, Comparable item2, String message) {
    count++;
    if (!item1.equals(item2)) {
      errors++;
      StdOut.print("fails: "+message);
      StdOut.print("  item1: \""+item1);
      StdOut.println("\" item2: \""+item2+"\"");
    }
  }
  public void tally() {
    StdOut.println(
      (count-errors) + " tests passed, "
      + errors + " tests fails");
  }
}

