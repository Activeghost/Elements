package EPI;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by clester on 8/17/2017.
 */
class StringsTest
{
	@Test
	void repeatedWords() {
		// normal testing would test boundary conditions for our paragraphs
		// this is one of them, a string of 7 words, and all but one of them unique.
		// also a palindrome.
		String input = "Able I was ere saw I Elba";

		List<Map.Entry<String, Integer>> list = Strings.repeatedWords(input);
		assertEquals(6, list.size());

		int count = Integer.MAX_VALUE;
		for(Map.Entry<String, Integer> entry : list)
		{
			assertTrue(count >= entry.getValue());
			count = entry.getValue();
		}
	}

	@Test
	void rabinKarp() {
	}

	@Test
	void computeHash() {
		String text = "abr";
		assertEquals(999509,Strings.computeHash(text));
	}

	@Test
	void computeRollingHash() {
	}

	@Test
	void decode()
	{
		String expectedDecodedText = "aaaabbcccd";
		String encodedText = "4a2b3c1d";
		Assert.assertEquals(expectedDecodedText, Strings.decode(encodedText));
	}

	@Test
	void encode()
	{
		String plainText = "aaaabbcccd";
		String expectedEncoding = "4a2b3c1d";
		Assert.assertEquals(expectedEncoding, Strings.encode(plainText));

		plainText = "aaaabbcccdddddddddddddddddddd";
		expectedEncoding = "4a2b3c20d";
		Assert.assertEquals(expectedEncoding, Strings.encode(plainText));
	}

	@Test
	void getValidIpAddresses()
	{
		List<String> ipAddresses = Strings.getValidIpAddresses("19216811");
		assertTrue(ipAddresses.size() == 9);
	}

	@Test
	void romanToInt()
	{
		assertEquals(3, Strings.romanToInt("III"));
		assertEquals(4, Strings.romanToInt("IIII"));
		assertEquals(4, Strings.romanToInt("IV"));
		assertEquals(5, Strings.romanToInt("V"));
		assertEquals(9, Strings.romanToInt("IX"));
		assertEquals(10, Strings.romanToInt("X"));
		assertEquals(13, Strings.romanToInt("XIII"));
		assertEquals(14, Strings.romanToInt("XIV"));
		assertEquals(40, Strings.romanToInt("XL"));
		assertEquals(50, Strings.romanToInt("L"));
		assertEquals(90, Strings.romanToInt("XC"));
		assertEquals(100, Strings.romanToInt("C"));
		assertEquals(400, Strings.romanToInt("CD"));
		assertEquals(500, Strings.romanToInt("D"));
		assertEquals(900, Strings.romanToInt("CM"));
		assertEquals(1000, Strings.romanToInt("M"));
	}

	@Test
	void getLookAndSay()
	{
		assertEquals('2', Strings.getLookAndSay(5));
		assertEquals('1', Strings.getLookAndSay(8));
	}

	@Test
	void computePhoneNumberMnemonicsIterative()
	{
		String input = new String("2218677");
		List<String> mnemonics = Strings.computePhoneNumberMnemonicsIterative(input);
		assertTrue(mnemonics.size() == 1296);
	}

	@Test
	void computePhoneNumberMnemonics()
	{
		String input = new String("2218677");
		List<String> mnemonics = Strings.computePhoneNumberMnemonics(input);
		assertTrue(mnemonics.size() == 1296);
	}

	@Test
	void reverseWordsOof1()
	{
		String input = "Able I was ere saw I Elba";
		String expected = "Elba I saw ere was I Able";

		final char[] charArray = input.toCharArray();
		Strings.reverseWordsOof1(charArray);
		assertArrayEquals(expected.toCharArray(), charArray);
	}

	@Test
	void reverseWords()
	{
		String input = "Able I was ere saw I Elba";
		String expected = "Elba I saw ere was I Able";
		assertArrayEquals(expected.toCharArray(),
				Strings.reverseWords(input.toCharArray()).toCharArray());
	}

	@Test
	void isPalindrome()
	{
		Character[] input = new Character[]{'a','b','c','c', 'b', 'a'};
		String palinDrome = "Able I was, ere saw I Elba";
		String notAPalinDrome = "Ray a Ray";
		assertTrue(Strings.isPalindrome(palinDrome));
		assertFalse(Strings.isPalindrome(notAPalinDrome));
	}

	@Test
	void swap()
	{
	}

	@Test
	void zip()
	{
		Character[] input = new Character[]
				{
						'1', '2', '3', '3', '4', '5', '5', null, null, null, null, null, null
				};

		Character[] input2 = new Character[]{'0', '3', '4', '6', '7', '8' };

		Character[] expectedResult = new Character[]
				{
						'0', '1', '2', '3', '3', '3', '4', '4', '5', '5', '6', '7', '8'
				};

		Strings.zip(input, input2);
		assertArrayEquals(expectedResult,input);
	}

	@Test
	void removeAndReplace()
	{
		Character[] input = new Character[]{'a','b','b','c', 'd', 'b', 'a'};
		Character[] expectedResult = new Character[]{'d','d', 'c', 'd', 'd', 'd' };

		int startRange = Strings.removeAndReplace(input);
		assertArrayEquals(expectedResult,
				java.util.Arrays.copyOfRange(input, startRange, input.length));
	}

	@Test
	void getSpreadSheetNumber()
	{
		assertEquals(1, Strings.getSpreadSheetNumber("A"));
		assertEquals(27, Strings.getSpreadSheetNumber("AA"));
		assertEquals(702, Strings.getSpreadSheetNumber("ZZ"));

	}

	@Test
	void baseConversion()
	{
		char[] base2ExpectedResult = new char[]{'1','1','0','1'};
		char[] input = new char[]{'1','3' };

		assertEquals(
				new String(base2ExpectedResult),
				Strings.baseConversion(new String(input), 10, 2));

		char[] base11ExpectedResult = new char[]{'1','2'};
		assertEquals(
				new String(base11ExpectedResult),
				Strings.baseConversion(new String(input), 10, 11));

		char[] base16ExpectedResult = new char[]{'D'};
		assertEquals(
				new String(base16ExpectedResult),
				Strings.baseConversion(new String(input), 10, 16));

		char[] base2Input = new char[]{'1','1','0','1'};
		assertEquals(
				new String(base16ExpectedResult),
				Strings.baseConversion(new String(input), 2, 16));

	}

	@Test
	void stringToInt()
	{
		assertEquals(
				-123,
				Strings.stringToInt(Integer.toString(-123)),
				0.000001);

		assertEquals(
				Integer.MAX_VALUE,
				Strings.stringToInt(Integer.toString(Integer.MAX_VALUE)));
	}

	@Test
	void intToString()
	{
		assertEquals(
				Integer.toString(Integer.MIN_VALUE),
				Strings.intToString(Integer.MIN_VALUE));
		assertEquals(
				Integer.toString(Integer.MAX_VALUE),
				Strings.intToString(Integer.MAX_VALUE));
	}

}