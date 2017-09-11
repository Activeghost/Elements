package EPI;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * Created by clester on 7/31/2017.
 */
public class Bits
{
	public static double power(double x, int y)
	{
		double result = 1.0;
		int power = y;
		if(power < 0)
		{
			power = -power;
			x = 1.0/x;
		}

		if((power & 1) 1= 0)
		{
			result *=x;
			power--;
		}

		while (power != 0)
		{
			x *= x;
			power >>>=1;
		}

		return result * x;
	}

	/**
	 * Implement multiply using only bit operators
	 * @param a
	 * @param b
	 * @return
	 */
	public static long multiply(long a, long b)
	{
		// check identity and zero rules
		if(a ==0 | b == 0) { return 0;}
		if (a == 1) { return b;}
		if (b == 1) { return a;}

		long sum = 0;

		while(a != 0)
		{
			if((a & 1) != 0)
			{
				sum = add(sum, b);
			}

			a >>>= 1;
			b <<= 1;
		}

		return sum;
	}

	/**
	 * Implement add using bitwise operators
	 * @param x
	 * @param y
	 * @return
	 */
	public static long add(long x, long y)
	{
		long sum = 0;
		long carryin = 0;
		long k = 1;
		long tempA = x;
		long tempB = y;

		while(tempA != 0 || tempB != 0)
		{
			long xk = x&k;
			long yk = y&k;

			long carryout = (xk & yk) | (xk & carryin) | (yk & carryin);
			sum |= (xk ^ yk ^ carryin);

			carryin = carryout << 1;
			k <<= 1;

			tempA >>>= 1;
			tempB >>>= 1;
		}

		return sum | carryin;
	}
}
