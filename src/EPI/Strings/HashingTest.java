package EPI.Strings;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import EPI.Math.Util;

/**
 * Hashing functions test class
 */
class HashingTest
{
	private final long _Q  = BigInteger
			.probablePrime(31, new Random()).intValue();
	private int BASE;

	@Test
	void computeRollingHash()
	{
		BASE = 256;
		Hashing rk = new Hashing(BASE);
		long hash1 = rk.computeHash("fox");
		long hash2 = rk.computeHash("afo");

		int powerS = 1;
		for (int i = 1; i <= 3-1; i++)
		{
			powerS = (BASE * powerS) % BigInteger
					.probablePrime(21, new Random())
					.intValue();
		}

		long newHash = rk.computeRollingHash(hash2, 'a', 'x', powerS);
		assertEquals(hash1, newHash);
	}

	@Test
	void computeRollingHashInLoop()
	{
		Hashing rk = new Hashing(BASE);

		String search = "the";
		String s = "the blind fox goes in circles at the dawn";

		long hash1 = rk.computeHash(search);
		long sourceHash = rk.computeHash("the");
		long powerS = Util.computePow(_Q, BASE, search.length());

		int searchStrLen = search.length();
		for (int i = searchStrLen; i < s.length(); i++)
		{
			assertEquals(hash1, sourceHash);

			int prevIndex = i - searchStrLen;
			char oldChar = s.charAt(prevIndex);
			char newChar = s.charAt(i);
			sourceHash = rk.computeRollingHash(sourceHash, oldChar, newChar, powerS);

			search = s.substring(prevIndex + 1, i + 1);
			hash1 = rk.computeHash(search);
		}
	}

	@Test
	void computeHash()
	{
		Hashing rk = new Hashing(BASE);
		long hash1 = rk.computeHash("fox");
		long hash2 = rk.computeHash("fox");

		assertEquals(hash1, hash2);
	}

}