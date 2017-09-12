package EPI.Strings;

import static java.lang.Integer.max;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by clester on 9/11/2017.
 */
public class BoyerMoore
{
	private final int ALPHABETSIZE = 256;
	private int[] _delta1;
	private int[] _delta2;

	public static final int STRING_NOT_FOUND = -1;

	public int search(String source, String pattern)
	{
		int sourceLen = source.length();
		int patternLen = pattern.length();
		int targetIndex = patternLen - 1;

		if(sourceLen < patternLen)
		{
			return STRING_NOT_FOUND;
		}

		if(sourceLen == 0 | patternLen == 0)
		{
			return STRING_NOT_FOUND;
		}

		// start at source[pattern length] and check backwards
		for(int i = targetIndex; i < sourceLen - 1;)
		{
			final char c = source.charAt(i);
			if(c == pattern.charAt(targetIndex))
			{
				i--;
				targetIndex--;
				continue;
			}

			i += max(_delta1[targetIndex], _delta2[targetIndex]);
			if(i > sourceLen)
			{
				return -1;
			}

			targetIndex = patternLen - 1;
		}

		return STRING_NOT_FOUND;
	}

	public void preCalculateDelta(String pattern)
	{
		calculateDelta1(pattern);
		calculateDelta2(pattern);
	}

	/**
	 * Fills a table of the alphabet size with the jump values for a character mismatch.
	 * If the char in a pattern of size N is not the in the pattern then jump N spaces
	 * otherwise jump N - J.
	 * @param pattern
	 */
	private void calculateDelta1(String pattern)
	{
		char a = 0;
		int m = pattern.length();

		_delta1 = new int[ALPHABETSIZE];
		Arrays.fill(_delta1, -1);

		for(int j = 0; j < m; j++)
		{
			a = pattern.charAt(j);
			_delta1[a] = m - j - 1;
		}
	}

	/**
	 * Case 1:
	 *
	 * Case 2:
	 *
	 * Otherwise: Jump to the next space over (N + 1)
	 * @param pattern
	 */
	private List<Integer> calculateDelta2(String pattern)
	{
		char a = 0;
		int m = pattern.length();
		int i = 0;
		int j = m + 1;

		List<Integer> f = new ArrayList<>();
		List<Integer> suffix = new ArrayList();

		// calculate the proper prefixes
		while(i > 0)
		{
			while (j < m && pattern.charAt(i - 1) != pattern.charAt(j - 1))
			{
				if(suffix.get(j) == 0) {
					suffix.set(j, j - i);
					j = f.get(j);
				}
			}

			i--;
			j--;

			f.set(i, j);
		}

		// calculate the proper suffixes

		// find the borders (common between the two)
	}
}
