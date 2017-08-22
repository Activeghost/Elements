package EPI;

import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by clester on 8/17/2017.
 */
class StringsTest
{
	@Test
	void reverseWordsOof1()
	{
		String input = new String("Able I was ere saw I Elba");
		String expected = new String("Elba I saw ere was I Able");

		final char[] charArray = input.toCharArray();
		Strings.reverseWordsOof1(charArray);
		Assert.assertArrayEquals(expected.toCharArray(), charArray);
	}

	@Test
	void reverseWords()
	{
		String input = new String("Able I was ere saw I Elba");
		String expected = new String("Elba I saw ere was I Able");
		Assert.assertArrayEquals(expected.toCharArray(),
				Strings.reverseWords(input.toCharArray()).toCharArray());
	}

	@Test
	void isPalindrome()
	{
		Character[] input = new Character[]{'a','b','c','c', 'b', 'a'};
		String palinDrome = new String("Able I was, ere saw I Elba");
		String notAPalinDrome = new String("Ray a Ray");
		Assert.assertTrue(Strings.isPalindrome(palinDrome));
		Assert.assertFalse(Strings.isPalindrome(notAPalinDrome));
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
		Assert.assertArrayEquals(expectedResult,input);
	}

	@Test
	void removeAndReplace()
	{
		Character[] input = new Character[]{'a','b','b','c', 'd', 'b', 'a'};
		Character[] expectedResult = new Character[]{'d','d', 'c', 'd', 'd', 'd' };

		int startRange = Strings.removeAndReplace(input);
		Assert.assertArrayEquals(expectedResult,
				java.util.Arrays.copyOfRange(input, startRange, input.length));
	}

	@Test
	void getSpreadSheetNumber()
	{
		Assert.assertEquals(1, Strings.getSpreadSheetNumber("A"));
		Assert.assertEquals(27, Strings.getSpreadSheetNumber("AA"));
		Assert.assertEquals(702, Strings.getSpreadSheetNumber("ZZ"));

	}

	@Test
	void baseConversion()
	{
		char[] base2ExpectedResult = new char[]{'1','1','0','1'};
		char[] input = new char[]{'1','3' };

		Assert.assertEquals(
				new String(base2ExpectedResult),
				Strings.baseConversion(new String(input), 10, 2));

		char[] base11ExpectedResult = new char[]{'1','2'};
		Assert.assertEquals(
				new String(base11ExpectedResult),
				Strings.baseConversion(new String(input), 10, 11));

		char[] base16ExpectedResult = new char[]{'D'};
		Assert.assertEquals(
				new String(base16ExpectedResult),
				Strings.baseConversion(new String(input), 10, 16));

		char[] base2Input = new char[]{'1','1','0','1'};
		Assert.assertEquals(
				new String(base16ExpectedResult),
				Strings.baseConversion(new String(input), 2, 16));

	}

	@Test
	void stringToInt()
	{
		Assert.assertEquals(
				-123,
				Strings.stringToInt(Integer.toString(-123)),
				0.000001);

		Assert.assertEquals(
				Integer.MAX_VALUE,
				Strings.stringToInt(Integer.toString(Integer.MAX_VALUE)));
	}

	@Test
	void intToString()
	{
		Assert.assertEquals(
				Integer.toString(Integer.MIN_VALUE),
				Strings.intToString(Integer.MIN_VALUE));
		Assert.assertEquals(
				Integer.toString(Integer.MAX_VALUE),
				Strings.intToString(Integer.MAX_VALUE));
	}

}