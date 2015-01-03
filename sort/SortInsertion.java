public class SortInsertion extends SortTemplate{

	public static void sort(Comparable[] a)
	{
		int count = 0;
		for (int i = 1; i < a.length; i++)
		{
			for (int j = i; (j > 0) && SortTemplate.less(a[j], a[j-1]); j--) 
			{
				SortTemplate.exch(a, j, j-1);
				count ++;
				if (count == 6) 
				{
					StdOut.println("count = "+ count);
					SortTemplate.show(a);
				}
			}
		}
	}
	public static void main(String[] args)
	{
		int N = 10;
//		Integer a[] = new Integer[N];
		Integer a[] = {44,47,50,72,97,15,17,27,51,45};
		SortTemplate.show(a);
		N = a.length;
//		for (int i = 0; i < N; i++)
//   			a[i] = StdRandom.uniform(N); 
		sort(a);
		if (!isSorted(a))
			StdOut.println("ERROR in insertion sorting algorithm");
//		SortTemplate.show(a);
	}
}
