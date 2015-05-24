// Simple test suite

public class TestSuite {

  private int errors; // numbers of fails tests
  private int count; // total number of tests

  // compare two items
  public void assertEQ(Comparable item1, Comparable item2, String message) {
    count++;
    if (item1 == null && item2 == null) return;
    if (!item1.equals(item2)) {
      errors++;
      System.out.println("fails: " + message 
        + "  item1: \"" + item1
        + "\" item2: \"" + item2 + "\"");
    }
  }

  // result of tests
  public void tally() {
    System.out.println( (count-errors) + " tests passed, "
      + errors + " tests fails");
  }
}

