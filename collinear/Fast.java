// Colinear four points detection. Fast algorithm
import java.util.Arrays;

public class Fast {

	private static void prnPels(Point[] pels)
	{
		// print out pels
		for (int i = 0; i < pels.length; i++)
			StdOut.println(pels[i]);
	}

	private static Point[] readPels(String fileName)
	{
		In file = new In(fileName);
		int N = file.readInt(); // number of pels
		Point[] pels = new Point[N];
		for (int i = 0; i < N; i++)
			pels[i] = new Point(file.readInt(), file.readInt());
		return pels;
	}

	private static void drawPicture(Point[] pels)
	{
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setScale(0, 32768);
		StdDraw.setPenRadius(0.015);
		StdDraw.clear(StdDraw.DARK_GRAY);
		for (int i = 0; i < pels.length; i++) {
			pels[i].draw();
		}
		StdDraw.setPenRadius(0.0025);
		StdDraw.setPenColor(StdDraw.GREEN);
	}

	private static void prnSlopes(Point[] pels, Point origin)
	{
		for (int i = 0; i < pels.length; i++)
			StdOut.println(i + "  " + pels[i] + " : " 
				+ origin.slopeTo(pels[i]));
	}

	private static void prnShowSegment(Point[] pels, int lo, int hi)
	{
		Point[] colinears = new Point[hi-lo+2];
		colinears[0] = pels[0];
		for (int i = lo; i <= hi; i++)
			colinears[i-lo+1] = pels[i];

		Arrays.sort(colinears);
		// prn and draw only one time: min is first
		if (colinears[0] != pels[0]) return;

		for (int i = 0; i < colinears.length-1; i++)
			StdOut.print(colinears[i] + " -> ");
		StdOut.println(colinears[colinears.length-1]);
		colinears[0].drawTo(colinears[colinears.length-1]);
	}

	private static void digSlopes(Point[] pels, Point origin)
	{
//			prnSlopes(pels, origin);
		Arrays.sort(pels, origin.SLOPE_ORDER);
//			System.out.println("  slopes after sorting:");
//			prnSlopes(pels, origin);
		
		int eqcnt = 0;
		int first = 1;
		int i = 1;
		while (i < (pels.length - 1))
		{
			if (origin.slopeTo(pels[i]) == origin.slopeTo(pels[i+1]))
				eqcnt++;
			else
			{
				if ((i - first) >= 2)
					prnShowSegment(pels, first, i);
				eqcnt = 0;
				first = i + 1;
			}
			i++;
		}
		if ((i - first) >= 2)
			prnShowSegment(pels, first, i);
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			StdOut.println("usage: Fast <file, containing points list>");
//			System.exit(0);
		}
		Point[] pels = readPels(args[0]);
		drawPicture(pels);

		Point[] cppels = new Point[pels.length];
		for (int i = 0; i < pels.length; i++)
		{
			for (int j = 0; j < pels.length; j++)
				cppels[j] = pels[j];
			digSlopes(cppels, cppels[i]);
		}
//		prnPels(pels);

	}
}
