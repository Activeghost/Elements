package EPI.Strings;

import static java.lang.Integer.max;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by clester on 9/11/2017.
 */
public class BoyerMoore
{
	private final int ALPHABETSIZE = 256;
	private int[] _delta1;
	private int[] _delta2;

	public static final int STRING_NOT_FOUND = -1;
	private int[] _suffixWidths;
	private int[] _suffixJumpTable;

	public List<Integer> search(String source, String pattern)
	{
		List<Integer> indexes = new ArrayList<>();

		// setup our skip tables
		preProcess(pattern);

		int sourceLen = source.length();
		int patternLen = pattern.length();

		if(sourceLen < patternLen)
		{
			return indexes;
		}

		if(sourceLen == 0 | patternLen == 0)
		{
			return indexes;
		}

		int i = 0;
		int j;

		// no need to continue if pattern is greater than remaining chars
		while(i <= sourceLen - patternLen)
		{
			j = patternLen - 1;

			while(j >= 0 && pattern.charAt(j) == source.charAt(i + j))
			{
				j--;
			}

			if(j < 0)
			{
				// found an instance, add it.
				indexes.add(i);
				i += _suffixJumpTable[0];
			}
			else
			{
				// jump by the max of either the bad character index or the suffix boundary
				i += Math.max(_suffixJumpTable[j+1], j -_delta1[source.charAt(i + j)]);
			}
		}

		return indexes;
	}

	/**
	 * Boyer Moore pre processing for bad character and good suffix heuristics
	 * @param pattern
	 */
	private void preProcess(String pattern)
	{
		_suffixWidths = new int[pattern.length() + 1];
		_suffixJumpTable = new int[pattern.length() + 1];

		calculateDelta1(pattern);
		findShortestBorderSuffixes(pattern, _suffixJumpTable, _suffixWidths);
		findWidestBorderSuffixes(pattern, _suffixJumpTable, _suffixWidths);
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
			_delta1[a] = j;
		}
	}

	/**
	 *
	 * @param pattern
	 * @param suffixJumpTable
	 * @param suffixWidths
	 */
	private void findShortestBorderSuffixes(String pattern, int[] suffixJumpTable, int[] suffixWidths)
	{
		int m = pattern.length() - 1;
		int i = m;
		int j = m + 1;

		/*
	    * Each entry f[i]contains the starting position of the widest border of the suffix of the
		* pattern beginning at position i. The suffix ε beginning at position m has no border,
	 	* therefore f[m] is set to m+1.
		*/
		suffixWidths[i] = j;
		while(i > 0)
		{
			while (j <= m &&
				   j > 0 &&
				   pattern.charAt(i - 1) != pattern.charAt(j - 1))
			{
				if(suffixJumpTable[j] == 0) {
					suffixJumpTable[j] = j - i;
				}

				j = suffixWidths[j];
			}

			i--;
			j--;

			suffixWidths[i] = j;
		}
	}

	/**
	 *
	 * @param pattern
	 * @param suffixSkipTable
	 * @param suffixWidths
	 */
	private void findWidestBorderSuffixes(String pattern, int[] suffixSkipTable, int[] suffixWidths)
	{
		int n = pattern.length();
		int m = n - 1;
		int j = suffixWidths[0];

		// suffix border preprocessing step 2
		// where the pattern occurs somewhere else in the pattern
		for(int i = 0; i < m; i++)
		{
			if(suffixSkipTable[i] ==0)
			{
				suffixSkipTable[i] = j;
			}

			if(i == j)
			{
				j = suffixWidths[j];
			}
		}
	}
}
