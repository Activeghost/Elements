package EPI.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Boyer-Moore implementation.
 */
public class BoyerMoore
{
	private final int ALPHABETSIZE = 256;
	private int[] _badCharTable;
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
				i += Math.max(_suffixJumpTable[j+1], j - _badCharTable[source.charAt(i + j)]);
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

		calcBadCharacterHeuristic(pattern);
		findShortestBorderSuffixes(pattern, _suffixJumpTable, _suffixWidths);
		findWidestBorderSuffixes(pattern, _suffixJumpTable, _suffixWidths);
	}

	/**
	 * Fills a table of the alphabet size with the jump values for a character mismatch.
	 * If the char in a pattern of size N is not the in the pattern then jump N spaces
	 * otherwise jump N - J.
	 * @param pattern
	 */
	private void calcBadCharacterHeuristic(String pattern)
	{
		char a = 0;
		int m = pattern.length();

		_badCharTable = new int[ALPHABETSIZE];
		Arrays.fill(_badCharTable, -1);

		for(int j = 0; j < m; j++)
		{
			a = pattern.charAt(j);
			_badCharTable[a] = j;
		}
	}

	/**
	 * Case 1 of strong suffix heuristic. Calculate the jumps for the shortest border suffixes
	 * @param pattern
	 * @param suffixShiftTable
	 * @param suffixBorders
	 */
	private void findShortestBorderSuffixes(String pattern, int[] suffixShiftTable, int[] suffixBorders)
	{
		int m = pattern.length() - 1;
		int i = m;
		int j = m + 1;

		/*
	    * Each entry f[i]contains the starting position of the widest border of the suffix of the
		* pattern beginning at position i. The suffix ε beginning at position m has no border,
	 	* therefore f[m] is set to m+1.
		*/
		suffixBorders[i] = j;
		while(i > 0)
		{
			/*
			   If the character at position i - 1 is !== j -1, continue searching to the right
			   of the pattern for the border.
			 */
			while (j <= m &&
				   j > 0 &&
				   pattern.charAt(i - 1) != pattern.charAt(j - 1))
			{
				/*
				   if the character preceding the occurrence of t in the patter P is different than
				   a character mismatch then shift the pattern from i to j.

				   check here to prevent the modification of the shift value from a suffix having
				   the same border such that we jump the max amount instead of a suffix border shift.
				*/
				if(suffixShiftTable[j] == 0) {
					suffixShiftTable[j] = j - i;
				}

				// update the position of the next border
				j = suffixBorders[j];
			}

			/*
			  p[i - 1] matched with p[j - 1] so we found a border. Store it's beginning position.
			 */
			i--;
			j--;

			suffixBorders[i] = j;
		}
	}

	/**
	 * Case 2 for the strong suffix heuristic, find the jumps for the widest border suffixes.
	 * @param pattern
	 * @param suffixShiftTable
	 * @param suffixWidths
	 */
	private void findWidestBorderSuffixes(String pattern, int[] suffixShiftTable, int[] suffixWidths)
	{
		int n = pattern.length();
		int m = n - 1;
		int j = suffixWidths[0];

		// suffix border pre-processing step 2
		// where the pattern occurs somewhere else in the pattern
		for(int i = 0; i < m; i++)
		{
			// set all indices in the shift table having a shift[i] = 0
			// to the border position of first character of the pattern
			if(suffixShiftTable[i] ==0)
			{
				suffixShiftTable[i] = j;
			}

			// if the suffix has become shorter than the widest border (index 0)
			// use the next widest border as value of j
			if(i == j)
			{
				j = suffixWidths[j];
			}
		}
	}
}
