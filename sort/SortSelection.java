public class SortSelection extends SortTemplate{

	public static void sort(Comparable[] a)
	{
		for (int i = 0; i < (a.length - 1); i++)
		{
			int minind = i;
			for (int j = i + 1; j < a.length; j++)
				if (SortTemplate.less (a[j], a[minind]))
					minind = j;
			SortTemplate.exch(a, i, minind);
		}
	}
	public static void main(String[] args)
	{
		int N = 10;
		Integer a[] = new Integer[N]; 
		for (int i = 0; i < N; i++)
   		a[i] = StdRandom.uniform(N); 
		sort(a);
		if (!SortTemplate.isSorted(a))
			StdOut.println("ERROR in selection sorting algorithm");
		SortTemplate.show(a);
	}
}
