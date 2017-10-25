package EPI.Strings;

/**
 * Created by clester on 10/24/2017.
 */
public class Hashing
{
	private final int _base;

	Hashing(int base)
	{
		_base = base;
	}

	public long computeRollingHash(long hash, char oldChar, char newChar, long powerS)
	{
		hash -= (powerS * oldChar);
		return (hash * _base + newChar) ;
	}

	public long computeHash(String s)
	{
		long hash = 0;
		for(int i = 0; i < s.length(); i++)
		{
			hash = hash * _base + s.charAt(i);
		}

		return hash;
	}
}
