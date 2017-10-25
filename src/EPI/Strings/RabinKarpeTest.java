package EPI.Strings;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import EPI.Math.Util;

/**
 * Created by clester on 10/23/2017.
 */
class RabinKarpeTest
{
	private Collection<IndexOfTestData> getTestData()
	{
		Collection<IndexOfTestData> params = new ArrayList<>();
		String s = "the blind fox goes in circles at the dawn";

		// natural case
		params.add(new IndexOfTestData(s, "fox", 10));
		params.add(new IndexOfTestData( s, " ", 3));

		// boundaries
		params.add(new IndexOfTestData(s, "the", 0 ));
		params.add(new IndexOfTestData(s, "dawn", s.length() - 4 ));

		// negative test cases
		params.add(new IndexOfTestData(s, "test", -1));
		params.add(new IndexOfTestData(s, "", -1 ));
		params.add(new IndexOfTestData(s, null, -1 ));
		params.add(new IndexOfTestData(s, null, -1 ));
		params.add(new IndexOfTestData(null, null, -1));
		params.add(new IndexOfTestData("", null, -1 ));
		params.add(new IndexOfTestData("", "", -1 ));

		return params;
	}

	private Collection<IndexOfCollectionTestData> getCollectionTestData()
	{
		Collection<IndexOfCollectionTestData> params = new ArrayList<>();
		String s = "the blind fox goes in circles at the dawn";
		ArrayList<String> emptyArray = new ArrayList<>();

		// natural case
		params.add(new IndexOfCollectionTestData(s, Arrays.asList("fox", "goes", "in"), 10 ));
		params.add(new IndexOfCollectionTestData(s, Arrays.asList(" ", "at", "test"), 3 ));

		// boundaries
		params.add(new IndexOfCollectionTestData(s, Arrays.asList("the"), 0 ));
		params.add(new IndexOfCollectionTestData(s, Arrays.asList("dawn"), s.length() - 4 ));

		// negative test cases
		final int NOT_FOUND = -1;
		params.add(new IndexOfCollectionTestData(s, Arrays.asList("Mustang", "BMW M6", "Corvette"), NOT_FOUND ));
		params.add(new IndexOfCollectionTestData(s, emptyArray, NOT_FOUND ));
		params.add(new IndexOfCollectionTestData(s, null, NOT_FOUND ));
		params.add(new IndexOfCollectionTestData(s, null, NOT_FOUND ));
		params.add(new IndexOfCollectionTestData(null, null, NOT_FOUND ));
		params.add(new IndexOfCollectionTestData("", null, NOT_FOUND ));
		params.add(new IndexOfCollectionTestData("", emptyArray, NOT_FOUND ));

		return params;
	}

	@Test
	void indexOfCollections()
	{
		for (IndexOfCollectionTestData params : getCollectionTestData())
		{
			assertEquals(params.ExpectedResult, new RabinKarpe(26).indexOf(params.Source, params.Target));
		}
	}

	@Test
	void indexOf()
	{
		for(IndexOfTestData params : getTestData())
		{
			assertEquals(params.ExpectedResult,
					new RabinKarpe(257).indexOf(params.Source, params.Target));
		}
	}
}

class IndexOfTestData
{
	public final String Source;
	public final String Target;
	public final int ExpectedResult;

	IndexOfTestData(String source, String target, int expectedResult)
	{
		Source = source;
		Target = target;
		ExpectedResult = expectedResult;
	}
}

class IndexOfCollectionTestData
{
	public final String Source;
	public final List<String> Target;
	public final int ExpectedResult;

	IndexOfCollectionTestData(String source, List<String> target, int expectedResult)
	{
		Source = source;
		Target = target;
		ExpectedResult = expectedResult;
	}
}