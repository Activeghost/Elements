package EPI.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by clester on 7/30/2017.
 */
public class ArrayOperationsTest
{
	private List<Integer> _stockPrices = new ArrayList();
	private List<Integer> _sortedIntegerList = new ArrayList();
	private List<Integer> _expectedRemoveResult = new ArrayList();
	private int[] _unsorted;

	@org.junit.Before
	public void setUp() throws Exception
	{
		_sortedIntegerList.add(2);
		_sortedIntegerList.add(3);
		_sortedIntegerList.add(5);
		_sortedIntegerList.add(5);
		_sortedIntegerList.add(7);
		_sortedIntegerList.add(11);
		_sortedIntegerList.add(11);
		_sortedIntegerList.add(11);
		_sortedIntegerList.add(13);

		_expectedRemoveResult.add(2);
		_expectedRemoveResult.add(3);
		_expectedRemoveResult.add(5);
		_expectedRemoveResult.add(7);
		_expectedRemoveResult.add(11);
		_expectedRemoveResult.add(13);

		_stockPrices.add(100);
		_stockPrices.add(95);
		_stockPrices.add(50);
		_stockPrices.add(52);
		_stockPrices.add(80);
		_stockPrices.add(74);
		_stockPrices.add(102);
		_stockPrices.add(150);
		_stockPrices.add(25);
		_stockPrices.add(1);
		_stockPrices.add(101);
		_stockPrices.add(152);
	}

	@Test
	public void computePascalsTriangle() throws Exception
	{
		int[][] expectedTriangle = {
				{  1 },
				{  1,  1 },
				{  1,  2, 1 },
				{  1,  3, 3, 1 },
				{  1,  4, 6, 4, 1 },
				{  1,  5, 10, 10, 5, 1 },
				{  1,  6, 15, 20, 15, 6, 1 }
		};

		Assert.assertArrayEquals(expectedTriangle, ArrayOperations.computePascalsTriangle(7));
	}

	@Test
	public void rotate2DMatrix()
	{
		int[][] matrix = {
				{  1,  5,  9, 13 },
				{  2,  6, 10, 14 },
				{  3,  7, 11, 15 },
				{  4,  8, 12, 16 }
		};

		int[][] expectedResult = {
				{ 13, 14, 15, 16 },
				{  9, 10, 11, 12 },
				{  5,  6,  7,  8 },
				{  1,  2,  3,  4 }
		};

		ArrayOperations.rotate2DMatrix(matrix);
		Assert.assertArrayEquals(expectedResult, matrix);
	}

	@Test
	public void computeSpiralOrderingNxM() throws Exception
	{
		List<List<Integer>> matrix4x3 = new ArrayList<>();
		List<Integer> col1 = new ArrayList<>();

		col1.add(1);
		col1.add(4);
		col1.add(7);
		col1.add(10);

		List<Integer> col2 = new ArrayList<>();
		col2.add(2);
		col2.add(5);
		col2.add(8);
		col2.add(11);

		List<Integer> col3 = new ArrayList<>();
		col3.add(3);
		col3.add(6);
		col3.add(9);
		col3.add(12);

		matrix4x3.add(0, col1);
		matrix4x3.add(1, col2);
		matrix4x3.add(2, col3);

		List<Integer> expectedOrder = new ArrayList<>();
		expectedOrder.add(1);
		expectedOrder.add(2);
		expectedOrder.add(3);
		expectedOrder.add(6);
		expectedOrder.add(9);
		expectedOrder.add(12);
		expectedOrder.add(11);
		expectedOrder.add(10);
		expectedOrder.add(7);
		expectedOrder.add(4);
		expectedOrder.add(5);
		expectedOrder.add(8);

		Assert.assertArrayEquals(expectedOrder.toArray(), ArrayOperations
				.computeSpiralOrdering(matrix4x3).toArray());
	}

	@Test
	public void computeSpiralOrderingNxN() throws Exception
	{
		List<List<Integer>> matrix = new ArrayList<>();
		List<Integer> col1 = new ArrayList<>();

		col1.add(1);
		col1.add(4);
		col1.add(7);

		List<Integer> col2 = new ArrayList<>();
		col2.add(2);
		col2.add(5);
		col2.add(8);

		List<Integer> col3 = new ArrayList<>();
		col3.add(3);
		col3.add(6);
		col3.add(9);

		matrix.add(0, col1);
		matrix.add(1, col2);
		matrix.add(2, col3);

		List<Integer> expectedOrder = new ArrayList<>();
		expectedOrder.add(1);
		expectedOrder.add(2);
		expectedOrder.add(3);
		expectedOrder.add(6);
		expectedOrder.add(9);
		expectedOrder.add(8);
		expectedOrder.add(7);
		expectedOrder.add(4);
		expectedOrder.add(5);

		Assert.assertArrayEquals(expectedOrder.toArray(), ArrayOperations
				.computeSpiralOrdering(matrix).toArray());
	}

	@Test
	public void generateNonUniformRandomNumber() throws Exception
	{
		Map<Integer, Double> input = new HashMap<>();
		input.put(1, .02);
		input.put(2, .08);
		input.put(3, .10);
		input.put(4, .15);
		input.put(5, .25);
		input.put(6, .20);
		input.put(7, .10);
		input.put(8, .06);
		input.put(9, .03);
		input.put(10, .01);

		for(int i = 0; i < 1000; i++)
		{
			int result = ArrayOperations.generateNonUniformRandomNumber(input);
			Assert.assertTrue(input.containsKey(result));
		}

		// A means to test that this is a non-uniform distribution:
		// https://en.wikipedia.org/wiki/Kolmogorov%E2%80%93Smirnov_test
		// and the Knuth: Chi-squared test
		// https://www.amazon.com/dp/0201896842/?tag=stackoverfl08-20
	}

	@Test
	public void getRandomPermutation() throws Exception
	{
	}

	@Test
	public void getAllPossibleSamples() throws Exception
	{
	}

	@Test
	public void getAllPossiblePermutations() throws Exception
	{
	}

	@Test
	public void getRandomSubset() throws Exception
	{
		// testing for randomness of a single number is a fairly difficult
		// https://stackoverflow.com/questions/2130621/how-to-test-a-random-generator

		// testing for bias in sampled subsets requires that we know the performance
		// of a subset.
		//
		// Testing for selection bias is also difficult: http://ftp.iza.org/dp8455.pdf

		int[] input = new int[10];
		for(int i = 0; i < 10; i++)
		{
			input[i] = i;
		}

		int sampleSize = 5;
		double[] subset = ArrayOperations
				.getRandomSubset(10,
								 sampleSize)
				.stream()
				.mapToDouble(s -> s)
				.toArray();

		Assert.assertTrue(subset.length == sampleSize);
	}

	@Test
	public void onlineSampling() throws Exception
	{
		ArrayOperations.onlineSampling();
		Thread.sleep(10000);
		Assert.assertTrue(ArrayOperations.samples.size() > 0);
	}

	@Test
	public void sample() throws Exception
	{
		int[] input = new int[] { 1, 3, 0, 5, 4, 2 };
		double[] totals = new double[3];
		double[] referenceTotal = new double[3];
		List<List<Integer>> allPossibleSamples = ArrayOperations.getAllPossibleSamples(input, 3);
		for(List<Integer> sample : allPossibleSamples)
		{
			int index = 0;
			for(int value : sample)
			{
				referenceTotal[index] += value;
				index++;
			}
		}

		for(int i = 1; i < 100000000; i++)
		{
			int index = 0;
			for(int value : ArrayOperations.sample(
					input,
					3))
			{
				totals[index] += value;
				index++;
			}
		}

		// the std deviation of the 100 million runs should be less than the deviation of all
		// possible combinations where n! is < run size
		boolean condition = relativeStandardDeviation(totals) - relativeStandardDeviation
				(referenceTotal) < 0;
		Assert.assertTrue(condition);
	}

	@Test
	public void getNextPermutation() throws Exception
	{
		int[] permutation = new int[] { 1, 3, 0, 5, 4, 2 };
		int[] expectedResult = new int[] { 1, 3, 2, 0, 4, 5 };
		int[] nextPermutation = ArrayOperations.getNextPermutation(permutation);
		Assert.assertArrayEquals(expectedResult, nextPermutation);
	}

	@Test
	public void permute() throws Exception
	{
		int[] permutation = new int[] { 1, 3, 0, 5, 4, 2 };

		Integer[] primes = new Integer[] { 2, 3, 5, 7, 11, 13 };

		Integer[] expectedResult = new Integer[] { 5, 2, 13, 3, 11, 7 };

		ArrayOperations.permute(primes, permutation);
		Assert.assertArrayEquals(expectedResult, primes);
	}

	@Test
	public void primeNumberSieve() throws Exception
	{
		Integer[] expectedPrimes = new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
				43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };

		List<Integer> primes = ArrayOperations.primeNumberSieve(100);

		Assert.assertArrayEquals(expectedPrimes, primes.toArray(new Integer[]{}));
	}

	@Test
	public void getPrimes() throws Exception
	{
		Integer[] expectedPrimes = new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
				43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };

		List<Integer> primes = ArrayOperations.getPrimes(100);

		Assert.assertArrayEquals(expectedPrimes, primes.toArray(new Integer[]{}));
	}

	@Test
	public void alternateArray()
	{
		// test an algorithm that takes in an unsorted array and returns an array
		// such that B[0] <= B[1] >= B[2] <= B[3] >= B[4]... etc.
		int[] expectedResult1 = {5, 9, 2, 3, 0, 7, 1, 6, 2};
		int[] input1 = {5, 9, 2, 3, 1, 0, 7, 2, 6};
		ArrayOperations.alternateArray(input1);
		Assert.assertArrayEquals(expectedResult1, input1);

		int[] expectedResult2 = {9, 10, 7, 8, 5, 6, 3, 4, 1, 2, 0};
		int[] input2 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		ArrayOperations.alternateArray(input2);
		Assert.assertArrayEquals(expectedResult2, input2);
	}

	@Test
	public void maxProfitNSellBuy() throws Exception
	{
		Assert.assertEquals(257, ArrayOperations.maxProfitNSellBuy(_stockPrices));

		// add some to the front of the array
		_stockPrices.add(0, 1);
		Assert.assertEquals(356, ArrayOperations.maxProfitNSellBuy(_stockPrices));
	}

	@Test
	public void maxProfit2() throws Exception
	{
		Assert.assertEquals(251, ArrayOperations.maxProfit2(_stockPrices));
	}

	@Test
	public void maxProfit() throws Exception
	{
		Assert.assertEquals(151, ArrayOperations.maxProfit2(_stockPrices));
	}

	@Test
	public void maxProfitBookSolution() throws Exception
	{
		Assert.assertEquals(151, ArrayOperations.maxProfitBookSolution(_stockPrices));
	}

	@Test
	public void removeDuplicates() throws Exception
	{
		_expectedRemoveResult.add(null);
		_expectedRemoveResult.add(null);
		_expectedRemoveResult.add(null);

		Object[] array = _sortedIntegerList.toArray();
		Object[] result = ArrayOperations.removeDuplicates(array);
		Assert.assertArrayEquals(_expectedRemoveResult.toArray(), result);
	}

	@org.junit.Test
	public void deleteDuplicatesUsingStream() throws Exception
	{
		List<Integer> result = ArrayOperations.deleteDuplicatesUsingStream(_sortedIntegerList);
		Assert.assertArrayEquals(_expectedRemoveResult.toArray(), result.toArray());
	}

	@org.junit.Test
	public void deleteDuplicates() throws Exception
	{
		List<Integer> original = new ArrayList<>(_sortedIntegerList);
		List<Integer> result = ArrayOperations.deleteDuplicates(_sortedIntegerList);

		Assert.assertArrayEquals(
				original.toArray(),
				_sortedIntegerList.toArray());
		Assert.assertArrayEquals(
				_expectedRemoveResult.toArray(),
				result.toArray());
	}

	private static double standardDeviation(double[] values, double average)
	{
		return Math.sqrt(
				java.util.Arrays.stream(values)
						.map(s -> Math.pow(s - average, 2.0))
						.average()
						.orElse(0));
	}

	private static double relativeStandardDeviation(double[] values)
	{
		Double average = java.util.Arrays.stream(values).average().orElse(0);
		double stdDev = standardDeviation(values, average);
		return (stdDev  * 100.0) / average;
	}


	@Test
	public void advanceAndWin() throws Exception
	{
		int[] winningState = new int[] {3,3,1,0,2,0,1};
		int[] losingState = new int[] {3,2,0,0,2,0,1};

		Assert.assertTrue(ArrayOperations.advanceAndWin(winningState));
		Assert.assertFalse(ArrayOperations.advanceAndWin(losingState));
	}
	@Test
	public void advanceAndWin2() throws Exception
	{
		int[] winningState = new int[] {3,3,1,0,2,0,1};
		int[] losingState = new int[] {3,2,0,0,2,0,1};

		Assert.assertTrue(ArrayOperations.advanceAndWin2(winningState));
		Assert.assertFalse(ArrayOperations.advanceAndWin2(losingState));
	}

	@Test
	public void multiply() throws Exception
	{
		List<Integer> multiplier = new ArrayList<Integer>();
		multiplier.add(9);
		multiplier.add(9);
		multiplier.add(9);
		multiplier.add(9);

		List<Integer> multiplicand = new ArrayList<Integer>();
		multiplicand.add(9);
		multiplicand.add(9);
		multiplicand.add(9);
		multiplicand.add(9);

		List<Integer> expected = new ArrayList<Integer>();
		expected.add(9);
		expected.add(9);
		expected.add(9);
		expected.add(8);
		expected.add(0);
		expected.add(0);
		expected.add(0);
		expected.add(1);

		List<Integer> total = ArrayOperations.multiply(multiplicand, multiplier);
		Assert.assertEquals(expected, total);
	}

	@Test
	public void plusOneFinite() throws Exception
	{
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(9);
		numbers.add(9);
		numbers.add(9);
		numbers.add(9);

		ArrayOperations.plusOneFinite(numbers);
		Assert.assertTrue(numbers.size() == 5);
		Assert.assertArrayEquals(numbers.toArray(), new Integer[] { 1, 0, 0, 0, 0 });
	}

	@org.junit.Test
	public void reOrder() throws Exception
	{
	}

	@org.junit.Test
	public void pivot() throws Exception
	{
		List<ArrayOperations.Color> colors = new ArrayList<ArrayOperations.Color>();
		colors.add(ArrayOperations.Color.RED);
		colors.add(ArrayOperations.Color.WHITE);
		colors.add(ArrayOperations.Color.RED);
		colors.add(ArrayOperations.Color.BLUE);
		colors.add(ArrayOperations.Color.RED);
		colors.add(ArrayOperations.Color.BLUE);
		colors.add(ArrayOperations.Color.BLUE);
		colors.add(ArrayOperations.Color.WHITE);
		colors.add(ArrayOperations.Color.WHITE);

		ArrayOperations.pivot(5, colors);
	}
}
