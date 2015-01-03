public class ConnectedComponents
{

	public Site site;

	private class Neib
	{
		int site;
		Neib next;
	}

	private class Site
	{
		int component; // number of Connnected Component site belong to
		boolean visited;
//		Neib neib;
	}

	public static void main(String[] args)
	{
		if (args.length != 1)
			throw new java.lang.IllegalArgumentException("\n\nparams: " +
				"<file name with edges>\n");
		In in = new In(args[0]);
		int N = in.readInt(); // number of sites
		int M = 0; // number of edges
		while (!in.isEmpty())
		{
			int site1 = in.readInt();
			int site2 = in.readInt();
			M++;
		}
		Site site = new Site();
	}
}

