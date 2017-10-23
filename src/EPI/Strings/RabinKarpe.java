package EPI.Strings;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of the Rabin-Karpe string search algorithm
 */
public class RabinKarpe
{
	private final int NOT_FOUND = -1;

	private int _base;
	private int _modulus;

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

	public int indexOf(String source, Collection<String> targets)
	{
		// guard against poor inputs
		targets.removeIf(s-> isNullOrEmpty(s));
		if(isNullOrEmpty(source) || targets.isEmpty())
		{
			return NOT_FOUND;
		}


		// start at the length of the substring
		int sourceLen = source.length();

		if(targets.stream().allMatch(substr -> substr.length() > sourceLen))
		{
			return NOT_FOUND;
		}

		int powerS = 1;
		Set<Integer> targetHashes = new HashSet<>();
		Queue<Integer> rollingSourceHashes = new LinkedList<>();

		for(String target : targets)
		{
			targetHashes.add(computeHash(target, powerS));
			rollingSourceHashes.add(computeHash(source.substring(0, target.length()), powerS));
		}


		int possibleMatchIndex;
		int minTargetLen = targets
				.stream()
				.min((a, b) -> a.length() < b.length() ? -1 : 1)
				.get()
				.length();

		int sourceHash = computeHash(source.substring(0, minTargetLen), powerS);

		for(int i = minTargetLen; i < sourceLen; i++)
		{
			possibleMatchIndex = i - minTargetLen;
			if(targetHashes.contains(sourceHash)
			   && targets.contains(source.substring(possibleMatchIndex, i)))
			{
				return possibleMatchIndex;
			}

			rollingSourceHashes.
			sourceHash = computeRollingHash(
					sourceHash,
					source.charAt(possibleMatchIndex),
					source.charAt(i), powerS);
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

	public int indexOf(String source, String target)
	{
		// guard against poor inputs
		if(isNullOrEmpty(source) || isNullOrEmpty(target))
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

		int powerS = 1;
		int targetHash = computeHash(target, powerS);
		int sourceHash = computeHash(source.substring(0, targetLen), powerS);

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
					source.charAt(i), powerS);
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

	private boolean isNullOrEmpty(String source)
	{
		return source == null || source.isEmpty();
	}

	private int computeRollingHash(int targetHash, char oldChar, char newChar, int powerS)
	{
		targetHash -= oldChar * powerS;
		return targetHash * _base + newChar % _modulus;
	}

	private int computeHash(String s, int powerS)
	{
		int hash = 0;

		for(int i = 0; i < s.length(); i++)
		{
			powerS = i > 0 ? powerS * _base : 1;
			hash = hash * _base + s.charAt(i);
		}

		return hash % _modulus;
	}
}
