package EPI;

import java.util.*;

import javafx.util.Pair;

/**
 * Created by clester on 8/16/2017.
 */
public class Strings
{
	private static final String[] MAPPING = {
			"0",
			"1",
			"ABC",
			"DEF",
			"HIG",
			"JKL",
			"MNO",
			"PQRS",
			"TUV",
			"WXYZ"
	};

	public Strings()
	{
	}

	public static int find(String target, String s)
	{

	}

	public static String decode(String encodedText)
	{
		char c = 0;
		int count = 0;
		StringBuilder s = new StringBuilder();

		for(int i = 0; i < encodedText.length();)
		{
			// handle numbers greater than 9
			if(Character.isDigit(encodedText.charAt(i)))
			{
				count = count * 10 + encodedText.charAt(i++) - '0';
			}
			else
			{
				c = encodedText.charAt(i++);
			}

			// decode
			while(count != 0)
			{
				s.append(c);
				count--;
			}
		}

		return s.toString();
	}

	public static String encode(String text)
	{
		int count = 0;
		char prev = 0;
		char c = 0;
		StringBuilder s = new StringBuilder();

		for(int i = 0; i < text.length(); i++)
		{
			c = text.charAt(i);
			if (c == prev | prev == 0)
			{
				count++;
			}
			else
			{
				// encode
				s.append(Integer.toString(count));
				s.append(prev);

				// reset counter (we've seen one already)
				count = 1;
			}

			prev = c;
		}

		// handle the last char
		s.append(Integer.toString(count));
		s.append(c);

		return s.toString();
	}

	/**
	 * O(n^4) setup, O(n) run time thereafter ... which is better than the O(n^3) constant runtime
	 * from the book.
	 * @param candidate
	 * @return
	 */
	public static List<String> getValidIpAddresses(String candidate)
	{
		// This would be retrieved once per session/run in a production env.
		Map<Integer, List<String>> map = getIpPatternMap();

		List<String> ipAddresses = new ArrayList<>();
		List<String> matchedPatterns = map.get(candidate.length());
		for(String pattern : matchedPatterns)
		{
			StringBuilder ip = new StringBuilder();
			final int aLen = pattern.charAt(0) - '0';
			final int bLen = pattern.charAt(1) - '0';
			final int cLen = pattern.charAt(2) - '0';
			final int dLen = pattern.charAt(3) - '0';

			final String a = candidate.substring(
					0,
					aLen);

			final String b = candidate.substring(
					aLen,
					aLen + bLen);

			final String c = candidate.substring(
					aLen + bLen,
					aLen + bLen + cLen);

			final String d = candidate.substring(candidate.length() - dLen);

			boolean isValid = isValid(a) && isValid(b) && isValid(c) && isValid(d);
			if(!isValid)
			{
				continue;
			}

			ip.append(a);
			ip.append('.');

			ip.append(b);
			ip.append('.');

			ip.append(c);
			ip.append('.');

			ip.append(d);
			ipAddresses.add(ip.toString());
		}

		return ipAddresses;
	}

	private static boolean isValid(String mask)
	{
		return Integer.parseInt(mask) <= 255;
	}

	private static Map<Integer, List<String>> getIpPatternMap()
	{
		// Build a map of all possible patterns once
		Map<Integer, List<String>> map = new HashMap<>();
		for(int a = 1; a <= 3; a++)
		{
			for( int b = 1; b <= 3; b++)
			{
				for(int c = 1 ; c <= 3; c++)
				{
					for( int d = 1; d <= 3; d++)
					{
						final int patternCount = a + b + c + d;
						List<String> patterns;
						if(!map.containsKey(patternCount))
						{
							patterns = new ArrayList<>();
							map.put(patternCount, patterns);
						}
						else
						{
							patterns = map.get(patternCount);
						}

						patterns.add(String.format("%1$d%2$d%3$d%4$d", a,b,c,d));
					}
				}
			}
		}

		return map;
	}

	public static int romanToInt(String romanNumeral)
	{
		Map<Character, Integer> key = new HashMap<Character, Integer>()
		{
			{
				put('I', 1);
				put('V', 5);
				put('X', 10);
				put('L', 50);
				put('C', 100);
				put('D', 500);
				put('M', 1000);
			}
		};

		int sum = 0;
		for(int i = romanNumeral.length() - 1; i >= 0; i--)
		{
			int value = key.get(romanNumeral.charAt(i));
			int nextVal = 0;

			if(i > 0)
			{
				nextVal = key.get(romanNumeral.charAt(i - 1));
			}

			if (nextVal < value && i > 0)
			{
				value -= nextVal;
				i--;
			}

			sum += value;
		}

		return sum;
	}

	/**
	 * Return the nth digit from a look and say sequence. e.g.,
	 * {1} {11} {21} {1211} {111221} {312211} {132221} { 11133211}
	 * One, One One, Two One(s), One Two and Two One(s)...
	 * @param n
	 */
	public static char getLookAndSay(int n)
	{
		StringBuilder sequence = new StringBuilder("1");
		int count = 0;
		int index = 0;

		while(sequence.length() < n)
		{
			// sum the count of consecutive integers of the same value
			for(int i = 1; i < sequence.length(); i++)
			{
				int prev = sequence.charAt(i - 1) - '0';
				int curr = sequence.charAt(i) - '0';

				if(prev == curr)
				{
					continue;
				}
				else
				{
					sequence.setCharAt(index++, (char)(count - '0'));
					sequence.setCharAt(index++, (char)(curr - '0'));
				}
			}
		}

		return sequence.charAt(n - 1);
	}

	public static List<String> computePhoneNumberMnemonicsIterative(String phoneNumber)
	{
		List<String> mnemonics = new ArrayList<>();
		List<String> partial = new ArrayList<>();
		mnemonics.add(" ");

		for(int digit = 0; digit < phoneNumber.length(); digit++)
		{
			for (String str : mnemonics)
			{
				final String s = MAPPING[phoneNumber.charAt(digit) - '0'];
				for(int i = 0 ; i < s.length(); i++)
				{
					partial.add(str + s.charAt(i));
				}
			}

			mnemonics = partial;
			partial = new ArrayList<>();
		}

		return mnemonics;
	}

	public static List<String> computePhoneNumberMnemonics(String number)
	{
		List<String> mnemonics = new ArrayList<>();
		char[] partialMnemonic = new char[number.length()];
		getPhoneNumberMnemonic(number,0, partialMnemonic, mnemonics);
		return mnemonics;
	}

	private static void getPhoneNumberMnemonic(
			String phoneNumber,
			int digit,
			char[] partial,
			List<String> mnemonics)
	{
		// all digits processed so add the partial mnemonic as a new mnemonic
		if(digit == phoneNumber.length())
		{
			mnemonics.add(new String(partial));
		}
		else
		{
			// try all possible characters for this digit
			final String s = MAPPING[phoneNumber.charAt(digit) - '0'];
			for(int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				partial[digit] = c;
				getPhoneNumberMnemonic(phoneNumber, digit + 1, partial, mnemonics);
			}
		}
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
