package EPI.Strings;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import EPI.Math.Util;

/**
 * Implementation of the Rabin-Karpe string search algorithm
 */
public class RabinKarpe
{
	private final int NOT_FOUND = -1;

	private int _base;
	private final long _Q  = BigInteger.probablePrime(31, new Random()).intValue();
	private Hashing _hashing;

	/**
	 * Instances this class for English strings
	 */
	public RabinKarpe()
	{
		init(256);
	}

	/**
	 * Instances this class
	 * @param base a prime greater than the number of characters in the alphabet being searched.
	 */
	public RabinKarpe(int base)
	{
		init(base);
	}

	private void init(int base)
	{
		_base = base;
		 _hashing = new Hashing(base);
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

		Set<Long> targetHashes = new HashSet<>();

		// <Hash, Hashed String Length>
		HashMap<Long, Integer> rollingSourceHashes = new HashMap<>();
		List<Long> powValues = new LinkedList<>();

		long powerS;
		for(String target : targets)
		{
			final long hash = _hashing.computeHash(target);
			targetHashes.add(hash);

			powerS = Util.computePow(_Q, _base, target.length());
			powValues.add(powerS);

			rollingSourceHashes.put(
					_hashing.computeHash(source.substring(0, target.length())),
					target.length());
		}


		int possibleMatchIndex;
		int maxTargetLen = targets
				.stream()
				.min((a, b) -> a.length() > b.length() ? -1 : 1)
				.get()
				.length();

		long sourceHash = _hashing.computeHash(source.substring(0, maxTargetLen));

		for(int i = maxTargetLen; i < sourceLen; i++)
		{
			possibleMatchIndex = i - maxTargetLen;
			if(!Collections.disjoint(targetHashes, rollingSourceHashes.keySet())
			   && targets.contains(source.substring(possibleMatchIndex, i)))
			{
				return possibleMatchIndex;
			}

			// update all source hashes.
			Set<Map.Entry<Long, Integer>> entries = new HashSet<>(rollingSourceHashes.entrySet());
			int index = 0;

			for(Map.Entry<Long, Integer> entry: entries)
			{
				Long prevHash = entry.getKey();
				Integer substrLen = entry.getValue();

				long hash = _hashing.computeRollingHash(
						prevHash,
						source.charAt(i - substrLen),
						source.charAt(i),
						powValues.get(index));

				rollingSourceHashes.put(hash, substrLen);
				rollingSourceHashes.remove(prevHash);
				index++;
			}
		}

		// possibly match at end of source.
		targetHashes.retainAll(rollingSourceHashes.keySet());
		if(!targetHashes.isEmpty())
		{
			possibleMatchIndex = sourceLen - rollingSourceHashes.get(targetHashes
																			 .stream()
																			 .findFirst()
																			 .orElse(0l));
			if (targets.contains(source.substring(possibleMatchIndex)))
			{
				return possibleMatchIndex;
			}
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

		long powerS = Util.computePow(_Q, _base, targetLen);
		long targetHash = _hashing.computeHash(target);
		long sourceHash = _hashing.computeHash(source.substring(0, targetLen));

		int possibleMatchIndex;
		for(int i = targetLen; i < sourceLen; i++)
		{
			possibleMatchIndex = i - targetLen;
			if(sourceHash == targetHash && source.substring(possibleMatchIndex, i).equals(target))
			{
				return possibleMatchIndex;
			}

			char oldChar = source.charAt(possibleMatchIndex);
			char newChar = source.charAt(i);
			sourceHash = _hashing.computeRollingHash(sourceHash, oldChar, newChar, powerS);
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
}
