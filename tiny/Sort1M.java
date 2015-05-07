// sort one million 32-bit integers
import java.io.*;
import java.util.Random;

public class Sort1M
{
	public static void main(String[] args)
	{
		if (!(new File("1Mintegers.txt").exists())) {
			Out out = new Out("1Mintegers.txt");
			Random rnd = new Random();
			for (int i = 0; i < 1000000; i++)
				out.println(rnd.nextInt());
			out.close();
		}
	}
}

