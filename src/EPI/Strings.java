package EPI;

import java.util.*;

/**
 * Created by clester on 8/16/2017.
 */
public class Strings
{
	public Strings()
	{
	}

	public static List<String> computePhoneNumberMnemonics(String number)
	{
		List<String> mnemonics = new ArrayList<>();

		return mnemonics;
	}

	/**
	 * O(1) size
	 * @param s
	 * @return
	 */
	public static void reverseWordsOof1(char[] s)
	{
		// reverse the whole string.
		int j = s.length -1;
		for(int i = 0; i < j; i++, j--)
		{
			swap(s, i, j);
		}

		// reverse each word in the string
		int wordEnd = s.length - 1;
		boolean hasMoreWords = true;
		while(hasMoreWords)
		{
			int wordStart = findNextWord(s, wordEnd);
			if(wordStart == 0)
			{
				hasMoreWords = false;
			}

			for(int i = wordStart, k = wordEnd; i < k; i++, k--)
			{
				swap(s, i, k);
			}

			// skip the space character and set the end of the next word
			wordEnd = wordStart - 2;
		}
	}

	/**
	 * O(n) size
	 * Alterative to this algorithm is one that reverses the string then reverses the words in
	 * the string.
	 * @param s
	 * @return
	 */
	public static String reverseWords(char[] s)
	{
		int wordEnd = s.length - 1;
		StringBuilder string = new StringBuilder();

		boolean hasMoreWords = true;
		while(hasMoreWords)
		{
			int wordStart = findNextWord(s, wordEnd);
			if(wordStart == 0)
			{
				hasMoreWords = false;
			}

			char[] word = java.util.Arrays.copyOfRange(s, wordStart, wordEnd + 1);
			string.append(word);

			if(hasMoreWords)
			{
				string.append(' ');
			}

			// skip the space character and set the end of the next word
			wordEnd = wordStart - 2;
		}

		return string.toString();
	}

	private static int findNextWord(
			char[] s,
			int prevIndex)
	{
		int wordStart = 0;

		for(int i = prevIndex; i >= 0; i--)
		{
			if(s[i] == ' ')
			{
				wordStart = i + 1;
				break;
			}
		}

		return wordStart;
	}

	public static boolean isPalindrome(String s)
	{
		int idxB = s.length() - 1;
		for(int idxA = 0; idxA < idxB;)
		{
			final char chA = s.charAt(idxA);
			final char chB = s.charAt(idxB);

			if(!Character.isLetterOrDigit(chA))
			{
				idxA++;
			}

			else if(!Character.isLetterOrDigit(chB))
			{
				idxB--;
			}

			else if(Character.toLowerCase(chA) != Character.toLowerCase(chB))
			{
				return false;
			}
			else
			{

				idxA++;
				idxB--;
			}
		}

		return true;
	}

	public static void zip(Character[] first, Character[] second)
	{
		int firstReadIndex = 0;
		int writeIndex = first.length - 1;

		// find the end of the valid first set.
		for(Character c : first)
		{
			if(c == null) {continue;}
			if(c > '0' && c < '9')
			{
				firstReadIndex++;
			}
		}

		// back off to last valid char in A
		firstReadIndex--;

		int secondIndex = second.length - 1;

		// merge the two back into the first array starting at the end
		for(int i = writeIndex; i >= 0; i--)
		{
			int firstC = firstReadIndex >= 0 ? first[firstReadIndex] - '0' : -1;
			int secondC = secondIndex >= 0 ? second[secondIndex] - '0' : -1;

			if (firstC > secondC)
			{
				first[i] = first[firstReadIndex--];
			} else
			{
				first[i] = second[secondIndex--];
			}
		}
	}

	/**
	 * Remove all 'b''s from the string and replace all 'a's with 'dd' with O(1) space
	 * return the index of the start of valid characters in the array
	 * @param input
	 * @return
	 */
	public static int removeAndReplace(Character[] input)
	{
		int writeIndex = input.length-1;
		int readIndex = removeChar(input, 'b');

		// walk backwards writing into the end of the array
		while(readIndex >= 0)
		{
			Optional<Character> maybeChar = Optional.ofNullable(input[readIndex--]);
			if(!maybeChar.isPresent())
			{
				continue;
			}

			final Character c = maybeChar.get();
			if(c == 'a')
			{
				input[writeIndex--] = 'd';
				input[writeIndex--] = 'd';
			}
			else
			{
				input[writeIndex--] = c;
			}
		}

		// return the index of the last write
		return writeIndex + 1;
	}

	private static int removeChar(
			Character[] input,
			char target)
	{
		int readIndex = 0;
		int writeIndex = 0;

		// remove a char from the string.
		for(char c : input)
		{
			if(c == target)
			{
				// remove the 'b'
				input[readIndex] = null;
			}
			else if(writeIndex != readIndex)
			{
				// if it is not a 'b' and we have an out of sync set of indices then copy over
				// to the write index the current location and increment.
				swap(input, readIndex, writeIndex);
				writeIndex++;
			}
			else
			{
				// normal case, not 'b' and writeIndex is in sync. Keep going.
				writeIndex++;
			}

			// always increment read index.
			readIndex++;
		}

		return writeIndex;
	}

	public static int getSpreadSheetNumber(String s)
	{
		int sheetNumber = 0;
		int column = 0;
		final char[] chars = s.toCharArray();

		for(int i = chars.length - 1; i >= 0; i--)
		{
			sheetNumber += (chars[i] - 'A' + 1) * Math.pow(26, column);
			column++;
		}

		return sheetNumber;
	}

	public static String baseConversion(String A, int bI, int bK)
	{
		// check if the base conversion is to the same base
		if(bI == bK) { return A; }

		boolean isNegative = (A.charAt(0) == '-');
		final char[] chars = isNegative ? A.substring(1).toCharArray() : A.toCharArray();

		StringBuilder baseK = new StringBuilder();
		int column = 0;
		int baseTenValue = 0;

		// convert to base 10
		for(int i = chars.length - 1; i >=0; i--)
		{
			baseTenValue += getNumericValue(chars[i]) * Math.pow(10, column);
			column++;
		}

		// convert from base 10 to K
		while(baseTenValue !=0)
		{
			int x = baseTenValue % bK;
			baseTenValue /= bK;
			baseK.append(getCharReprentation(x));
		}

		if(isNegative) { baseK.append('-'); }

		baseK.reverse();
		return baseK.toString();
	}

	private static char getCharReprentation(int x)
	{

		if(x > 36)
		{
			throw new IllegalArgumentException(
					"This function cannot handle conversion greater than base 36");
		}

		if(x < 10)
		{
			return (char)('0' + x);
		}
		else
		{
			return (char)('A' + x - 10);
		}
	}

	private static int getNumericValue(char digit)
	{
		if(Character.isDigit(digit))
		{
			return Character.getNumericValue(digit);
		}
		else
		{
			switch (digit)
			{
				case 'A':
					return 11;
				case 'B':
					return 12;
				case 'C':
					return 13;
				case 'D':
					return 14;
				case 'E':
					return 15;
				case 'F':
					return 16;
				default:
					throw new IllegalArgumentException("The character to convert to a base 10 must"
							+ "be a digit must of 0-9 or a letter of A-F");
			}
		}
	}

	public static int stringToInt(String integer)
	{
		double value = 0;
		boolean isNegative = (integer.charAt(0) == '-');
		final char[] chars = isNegative ? integer.substring(1).toCharArray() : integer.toCharArray();

		int column = 0;
		for(int i = 0; i < chars.length; i++)
		{
			// our calc below will hand the decimal placement via exponents
			if(chars[i] == '.')
			{
				continue;
			}

			// never happen with an Integer ... for Double his is a harder problem
			else if(chars[i] == 'E')
			{
				String exponent = integer.substring(i + 1);
				int e = 0;

				// end of the string. IMul by the power here and break
				for(int j = 0; j < exponent.length(); j++)
				{
					e += getDoubleValueFromCharArray(
							exponent.toCharArray(),
							j,
							j);
				}

				// results in wrong answer ... unsure how to fix as the
				// answer is value / 10 *= Math.pow(10, e - getEValue(value)).
				value *= Math.pow(10, e);

				break;
			}

			value += getDoubleValueFromCharArray(
					chars,
					column,
					i);
			column++;
		}

		return new Double(isNegative ? -value : value).intValue();
	}

	private static double getDoubleValueFromCharArray(
			char[] chars,
			int column,
			int i)
	{
		return (chars[i] - '0') * (Math.pow(10, chars.length - column - 1));
	}

	public static String intToString(int value)
	{
		StringBuilder string = new StringBuilder();
		boolean isNegative = value < 0;

		do
		{
			string.append((char)('0' + Math.abs(value % 10)));
			value /= 10;

		} while (value != 0);

		if(isNegative)
		{
			string.append('-');
		}

		string.reverse();
		return string.toString();
	}

	private static void swap(
			char[] input,
			int i,
			int nextIndex)
	{
		char temp = input[i];
		input[i] = input[nextIndex];
		input[nextIndex] = temp;
	}

	public static <T> void swap(
			T[] input,
			int i,
			int nextIndex)
	{
		T temp = input[i];
		input[i] = input[nextIndex];
		input[nextIndex] = temp;
	}

}
