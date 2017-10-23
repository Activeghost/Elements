package EPI.Strings;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of the Rabin-Karpe string search algorithm
 */
public class RabinKarpe
{
	private final int NOT_FOUND = -1;

	private int _base;
	private int _modulus;
	private int _powerS = 1;

	/**
	 * Instances this class for English strings
	 */
	public RabinKarpe()
	{
		init(26);
	}

	/**
	 * Instances this class
	 * @param alphabetSize the number of characters in the alphabet being searched.
	 */
	public RabinKarpe(int alphabetSize)
	{
		init(alphabetSize);
	}

	private void init(int alphabetSize)
	{
		_base = alphabetSize;
		_modulus = BigInteger.probablePrime(31, new Random()).intValue();
	}

	public int indexOf(String source, String target)
	{
		// guard against poor inputs
		if(source == null || target == null || source.isEmpty() || target.isEmpty())
		{
			return NOT_FOUND;
		}

		// start at the length of the substring
		int targetLen = target.length();
		int sourceLen = source.length();

		if(sourceLen < targetLen)
		{
			return NOT_FOUND;
		}

		int targetHash = computeHash(target);
		int sourceHash = computeHash(source.substring(0, targetLen));

		int possibleMatchIndex;
		for(int i = targetLen; i < sourceLen; i++)
		{
			possibleMatchIndex = i - targetLen;
			if(sourceHash == targetHash && source.substring(possibleMatchIndex, i).equals(target))
			{
				return possibleMatchIndex;
			}

			sourceHash = computeRollingHash(
					sourceHash,
					source.charAt(possibleMatchIndex),
					source.charAt(i));
		}

		// possibly match at end of source.
		possibleMatchIndex = sourceLen - targetLen;
		if(sourceHash == targetHash && source
				.substring(possibleMatchIndex)
				.equals(target))
		{
			return possibleMatchIndex;
		}

		return NOT_FOUND;
	}

	private int computeRollingHash(int targetHash, char oldChar, char newChar)
	{
		targetHash -= oldChar * _powerS;
		return targetHash * _base + newChar % _modulus;
	}

	private int computeHash(String s)
	{
		int hash = 0;

		for(int i = 0; i < s.length(); i++)
		{
			_powerS = i > 0 ? _powerS * _base : 1;
			hash = hash * _base + s.charAt(i);
		}

		return hash % _modulus;
	}
}
