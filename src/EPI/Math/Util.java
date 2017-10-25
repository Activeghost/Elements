package EPI.Math;

/**
 * Created by clester on 10/24/2017.
 */
public class Util
{
	public static long computePow(long _Q, int _base, int y)
	{
		long powerS = 1;
		for(int i = 1; i < y; i++)
		{
			powerS = (_base * powerS) % _Q;
		}

		return powerS;
	}
}
