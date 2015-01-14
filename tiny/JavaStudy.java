public class JavaStudy
{

	public static int mypow (int base, int exp)
	{
		if (exp < 0) throw new java.lang.IllegalArgumentException();
		int res = 1;
		while (exp != 0)
		{
			if ((exp & 1) == 1)
				res *= base;
			exp >>= 1;
			base *= base;
		}
		return res;
	}

	public static void chars()
	{
		System.out.println(Integer.parseInt("23"));
		System.out.println('\'' + '\t' + '\"');
		System.out.println((char)('\'' + '\t' + '\"'));
		System.out.println('\'' + "\t" + '\"');
	}

	public static void printchars(int start, int end)
	{
		if (start > end || start < 0 || end < 0) throw new java.lang.IllegalArgumentException();
		System.out.println();
		for (int i=start; i<end; i++)
		{
			if (i%100 == 0) System.out.format("%6d ", i);
			else if (i%10 == 0) System.out.print(' ');
			System.out.print((char)(i));
			if (i%100 == 99)	System.out.println();
		}
		System.out.println();
	}

	public static void bitwise()
	{
		int foo = 3;
		int bar = ~foo;
		System.out.format("%x\n", bar);
		
		foo = 5;
		bar = foo >> 1;
		System.out.format("%x  %x\n", foo, bar);
	}

	public static void IntegralBoundaries()
	{
		int foo = mypow(2,31);
		System.out.format("%d  %d  %d\n", foo-1, foo, foo+1);

		byte bb = -128;
		byte bbb = 127;
		System.out.format("%d  %d\n", bb, (byte)(bb-1));
		System.out.format("%d  %d\n", bbb, (byte)(bbb+1));
	}

	public static void PreciseOfCalculation(double theta)
	{
		double trig_one = Math.pow(Math.sin(theta), 2) + Math.pow(Math.cos(theta), 2);
		System.out.println(trig_one);
		System.out.println();
	}

	public static void Concatenation()
	{
		System.out.println(2 + "bc");
		System.out.println(2 + 3 + "bc");
		System.out.println((2+3) + "bc");
		System.out.println("bc" + (2+3));
		System.out.println("bc" + 2 + 3);

		System.out.println('b');
		System.out.println('b' + 'c');
		System.out.println((char) ('a' + 4));

		Integer a = Integer.MAX_VALUE;
		System.out.println();
		System.out.println(a);
		System.out.println(a+1);
		System.out.println(2-a);
		System.out.println(-2-a);
		System.out.println(2*a);
		System.out.println(4*a);

		double b = 3.14159;
		System.out.println();
		System.out.println(b);
		System.out.println(b+1);
		System.out.println(8/(int) b);
		System.out.println(8/b);
		System.out.println((int) (8/b));

		System.out.println((Math.sqrt(2) * Math.sqrt(2) == 2));
	}

	public static void main(String[] args)
	{
//		printchars(60000, (int)mypow(2,16)-1);
//		bitwise();
//		IntegralBoundaries();
//		PreciseOfCalculation(Double.parseDouble(args[0]));
		Concatenation();
	}
}
