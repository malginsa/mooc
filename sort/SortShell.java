public class SortShell extends SortTemplate{

	public static void sort(Comparable[] a)
	{
		int h = 1;
		while (h < a.length/3)
			h = 3*h + 1;
		while (h >= 1)
		{ // h-sort the array
			for (int i = h; i < a.length; i++)
				// Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]...
				for (int j = i; j >= h && SortTemplate.less(a[j], a[j-h]); j -= h)
					SortTemplate.exch(a, j, j-h);
			h = h/3;
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
			StdOut.println("ERROR in shell sorting algorithm");
		SortTemplate.show(a);
	}
}
