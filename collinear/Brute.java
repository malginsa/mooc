// Colinear four points detection. Bruteforse algorithm
import java.util.Arrays;

public class Brute {

	private static void prnPels(Point[] pels, int count) {
		// print out count pels
		for (int i = 0; i < count; i++)
			StdOut.println(pels[i]);
	}

	public static void main(String[] args) {
		In file = new In(args[0]);
		if (args.length != 1) {
			StdOut.println("usage Brute <points list file>");
//			System.exit(0);
		}
		int N = file.readInt(); // number of points
		Point[] pels = new Point[N];
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setScale(0, 32768);
		StdDraw.setPenRadius(0.015);
		StdDraw.clear(StdDraw.DARK_GRAY);
		for (int i = 0; i < N; i++) {
			pels[i] = new Point(file.readInt(), file.readInt());
			pels[i].draw();
		}
		StdDraw.setPenRadius(0.0025);
		StdDraw.setPenColor(StdDraw.GREEN);
		Arrays.sort(pels);
		for (int i = 0; i < N; i++)
			for (int j = i+1; j < N; j++)
				for (int k = j+1; k < N; k++)
					for (int l = k+1; l < N; l++) {
						double slope1 = pels[i].slopeTo(pels[j]);
						double slope2 = pels[j].slopeTo(pels[k]);
						double slope3 = pels[k].slopeTo(pels[l]);
						if (slope1 == slope2 && slope2 == slope3) {
							StdOut.println(pels[i] + " -> "
															+ pels[j] + " -> "
															+ pels[k] + " -> "
															+ pels[l]);
							pels[i].drawTo(pels[l]);
						}
					}
//		prnPels(pels, N);
	}
}
