package EPI;

import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static EPI.Arrays.timer;

import EPI.Dijkstra.DijkstraAlgorithm;
import EPI.Dijkstra.Node;
import javafx.util.Pair;

/**
 * Created by clester on 7/30/2017.
 */
public class Arrays
{
	enum Color
	{
		RED,
		WHITE,
		BLUE
	}

	public static final Timer timer = new Timer();
	public static final List<int[]> samples = new ArrayList<>();
	public static final int SAMPLE_SIZE = 5;

	/**
	 * Clones the provided array
	 *
	 * @param src
	 * @return a new clone of the provided array
	 */
	public static int[][] cloneArray(int[][] src) {
		int length = src.length;
		int[][] target = new int[length][src[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		}
		return target;
	}


	// reorder the array such that either even or odd is first
	public static void reOrder(int[] a, boolean even)
	{
		int beginning = 0;
		int end = a.length - 1;

		int nextEven;
		int nextOdd;

		if (even)
		{
			nextEven = beginning;
			nextOdd = end;
		}
		else
		{
			nextEven = end;
			nextOdd = beginning;
		}

		while (nextEven < nextOdd)
		{
			if (a[nextEven] % 2 == 0)
			{
				nextEven++;
			}
			else
			{
				int temp = a[nextEven];
				a[nextEven] = a[nextOdd];
				a[nextOdd] = temp;
			}
		}
	}

	public static void pivot(int pivot, List<Color> colors)
	{
		int smallerThanIndex = 0;
		int currentIndex = 0;
		int largerThanIndex = colors.size() - 1;

		Color pivotValue = colors.get(pivot);

		for (Color t : colors)
		{
			if (t.ordinal() < pivotValue.ordinal())
			{
				Collections.swap(colors, currentIndex, smallerThanIndex);
				smallerThanIndex++;
			}

			currentIndex++;
		}

		currentIndex = 0;
		for (Color t : colors.subList(smallerThanIndex, largerThanIndex))
		{
			if (t.ordinal() > pivotValue.ordinal())
			{
				Collections.swap(colors, currentIndex, largerThanIndex);
				largerThanIndex--;
			}

			currentIndex++;
		}

	}

	public static void reOrder(List<Color> colors)
	{
		int smallerThanIndex = 0;
		int currentIndex = 0;
		int largerThanIndex = colors.size() - 1;

		// choose arbitrary pivot value
		Color pivotValue = colors.get(largerThanIndex / 2);

		for (Color t : colors)
		{
			if (t.ordinal() < pivotValue.ordinal())
			{
				Collections.swap(colors, currentIndex, smallerThanIndex);
				smallerThanIndex++;
			}
			else if (t.ordinal() > pivotValue.ordinal())
			{
				Collections.swap(colors, currentIndex, largerThanIndex);
				largerThanIndex--;
			}

			currentIndex++;
		}
	}

	public static void plusOneFinite(List<Integer> numbers)
	{
		int index = numbers.size() - 1;
		numbers.set(index, numbers.get(index) + 1);

		for (int i = index; i > 0; --i)
		{
			if (numbers.get(i) == 10)
			{
				numbers.set(i, 0);
				numbers.set(i - 1, numbers.get(i - 1) + 1);
			}
		}

		// check if we have a 1 to carry forward with no more digits in the array to add it too.
		if (numbers.get(0) == 10)
		{
			numbers.set(0, 0);
			numbers.add(0, 1);
		}
	}

	/**
	 * Multiple two arbitrary precision integers
	 *
	 * @param m List representing integer one
	 * @param n List representing integer two
	 */
	public static List<Integer> multiply(List<Integer> m, List<Integer> n)
	{
		int totalSize = m.size() + n.size();
		Integer[] scratchPad = new Integer[totalSize];
		java.util.Arrays.fill(scratchPad, 0);

		// use grade school algorithm.

		// n.get(size()-1) * m[*]
		for (int nCol = n.size() - 1; nCol >= 0; nCol--)
		{
			int result;
			int multiplier = n.get(nCol);

			for (int mCol = m.size() - 1; mCol >= 0; --mCol)
			{
				int multiplicand = m.get(mCol);
				result = (multiplicand * multiplier) + scratchPad[nCol + mCol + 1];
				scratchPad[nCol + mCol] = scratchPad[nCol + mCol] + result / 10;
				scratchPad[nCol + mCol + 1] = result % 10;
			}
		}

		return java.util.Arrays.asList(scratchPad);
	}

	public static boolean advanceAndWin(int[] gameState)
	{
		int x = 0;
		while (x < gameState.length - 1)
		{
			if (x == 0)
			{
				return false;
			}
			x += gameState[x];
		}

		return true;
	}

	/**
	 * Each index in the incoming array is a node in the graph which contains the data, n = array[i]
	 * The vertex is a connection of weight N to the node of that ID (index)
	 *
	 * @param gameState
	 * @return
	 */
	public static boolean advanceAndWin2(int[] gameState)
	{
		int size = gameState.length - 1;
		List<Node> nodes = new ArrayList<>(size);
		final int endId = 9999;

		// walk backward to build the graph
		for (int index = size; index >= 0; index--)
		{
			nodes.add(new Node(index));
			int move = gameState[index];
			int furthestReach = move + index;

			// walk up the nodes adding edges until the next to last node
			for (int j = index; j <= furthestReach && j < size; j++)
			{
				// add the new nodes to the end of the list
				// and add this node as an adjacent node
				nodes
						.get(j)
						.addEdge(index, furthestReach - j);
			}
		}

		// use Djiktra's algorithm to return the shortest path to win.
		DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(nodes);
		dijkstraAlgorithm.execute(nodes.get(0));
		final LinkedList<Node> path = dijkstraAlgorithm.getPath(nodes.get(size));
		return true;
	}

	public static <T> List<T> deleteDuplicatesUsingStream(List<T> input)
	{
		return input.stream().distinct().collect(Collectors.toList());
	}

	public static <T> List<T> deleteDuplicates(List<T> input)
	{
		// copy input so we can operate without the array content changing underneath us
		List<T> copy = new ArrayList<>(input);
		removeDuplicates(copy);
		return copy;
	}

	private static <T> void removeDuplicates(
			List<T> input)
	{
		int i = input.size() - 1;
		while(i > 0)
		{
			T elem = input.get(i);
			if(elem.equals(input.get(i - 1))){
				input.remove(i);
			}

			i--;
		}
	}

	public static <T> T[] removeDuplicates(T[] input)
	{
		int lastUniqueIndex = 0;
		int fillIndex = input.length;
		for(int i = 1; i <= input.length - 1; i++)
		{
			T current = input[i];
			T lastUniqueValue = input[lastUniqueIndex];
			if(!lastUniqueValue.equals(current))
			{
				lastUniqueIndex++;
				input[lastUniqueIndex] = input[i];
			}
			else
			{
				fillIndex--;
			}
		}

		while(fillIndex < input.length)
		{
			input[fillIndex] = null;
			fillIndex++;
		}

		return input;
	}

	public static int maxProfitBookSolution(List<Integer> stockPrices)
	{
		int maxProfit = 0;
		int minValue = Integer.MAX_VALUE;

		for (int price : stockPrices)
		{
			maxProfit = Math.max(maxProfit, price - minValue);
			minValue = Math.min(price, minValue);
		}

		return maxProfit;
	}

	/**
	 * Modified dutch flag partitioning
	 * @param stockPrices
	 * @return
	 */
	public static long maxProfitNSellBuy(List<Integer> stockPrices)
	{
		// 1. Find all pairs of peaks and valleys in the array
		// 2. Calculate

		if(stockPrices.size() == 0) { return 0;}

		long profit = 0;

		// Find the max profit indices and use that to partition the array
		Stack<Pair<Integer, Integer>> buySellIndices = getBuySellIndicesExt(stockPrices);

		for(Pair<Integer, Integer> pair : buySellIndices)
		{
			profit += stockPrices.get(pair.getValue()) - stockPrices.get(pair.getKey());
		}

		return profit;
	}

	private static Stack<Pair<Integer, Integer>> getBuySellIndicesExt(List<Integer> stockPrices)
	{
		int minIndex = Integer.MAX_VALUE;
		int maxIndex = Integer.MIN_VALUE;

		Stack<Pair<Integer, Integer>> minmaxstack = new Stack<>();

		// test n-1, n and n+1 to identify mins and maxes as we walk the input.
		int terminatingIndex = stockPrices.size() - 1;
		for(int i = 1; i < terminatingIndex; i++)
		{
			int nextIndex = i + 1;
			int prevIndex = i - 1;

			Integer current = stockPrices.get(i);
			Integer nextPrice = stockPrices.get(nextIndex);
			Integer previousPrice = stockPrices.get(prevIndex);

			// exceptional conditions

			// handle start of array min
			if(previousPrice < current && i == 1)
			{
				minIndex = i - 1;
			}
			// handle end of array max
			else if(nextPrice > current && nextIndex == terminatingIndex)
			{
				maxIndex = nextIndex;
			}

			// normal handling

			// set min if we've found a new min and move the max
			if(previousPrice > current && current < nextPrice)
			{
				minIndex = i;
			}

			// set max if we've found a new max
			else if(previousPrice < current && current > nextPrice)
			{
				maxIndex = i;
			}

			// if we have a matching pair (buy low and sell high) add and reset the markers
			if (minIndex < maxIndex)
			{
				minmaxstack.push(new Pair<>(minIndex, maxIndex));
				maxIndex = Integer.MIN_VALUE;
				minIndex = Integer.MAX_VALUE;
			}
		}

		return minmaxstack;
	}

	public static long maxProfit2(List<Integer> stockPrices)
	{
		Pair<Integer, Integer> buySellIndices = getBuySellIndices(stockPrices);

		Pair<Integer, Integer> buySellIndicesPrev = getBuySellIndices(stockPrices.subList(0,
				buySellIndices.getKey()));

		Pair<Integer, Integer> buySellIndicesNext = getBuySellIndices(stockPrices.subList
				(buySellIndices.getValue(),
				stockPrices.size()));

		int beginning = stockPrices.get(buySellIndicesPrev.getValue()) -
				stockPrices.get(buySellIndicesPrev.getKey());

		int middle = stockPrices.get(buySellIndices.getValue()) -
				stockPrices.get(buySellIndices.getKey());

		int end = stockPrices.get(buySellIndicesNext.getValue()) -
				stockPrices.get(buySellIndicesNext.getKey());

		return beginning > end	? middle + beginning : middle + end;
	}

	private static Pair<Integer, Integer> getBuySellIndices(List<Integer> stockPrices)
	{
		Pair<Integer, Integer> currentSpan = new Pair<>(0,0);
		int maxProfit = 0;
		int minIndex = 0;
		int maxIndex = 0;

		for(int i = 0; i < stockPrices.size(); i++)
		{
			Integer current = stockPrices.get(i);
			Integer minValue = stockPrices.get(minIndex);
			Integer maxValue = stockPrices.get(maxIndex);

			// set min if we've found a new min and move the max
			if(current < minValue)
			{
				minIndex = i;
				maxIndex = i;
			}

			if(current > maxValue)
			{
				// calc best profit so far and save if it is better
				int potentialProfit = current - minValue;
				if(potentialProfit > maxProfit)
				{
					maxProfit = potentialProfit;
					maxIndex = i;
					currentSpan = new Pair<>(minIndex, maxIndex);
				}
			}
		}

		return currentSpan;
	}

	public static int maxProfit(List<Integer> stockPrices)
	{
		int maxProfit = 0;
		int minIndex = 0;
		int maxIndex = 0;

		for(int i = 0; i < stockPrices.size(); i++)
		{
			Integer current = stockPrices.get(i);
			Integer minValue = stockPrices.get(minIndex);
			Integer maxValue = stockPrices.get(maxIndex);

			// increment min/max if we've found a new min
			if(current < minValue)
			{
				minIndex = i;
				maxIndex = i;
			}

			// increment max if we are going up
			if(current > maxValue)
			{
				maxIndex = i;

				// calc best profit so far
				int potentialProfit = current - minValue;
				maxProfit = maxProfit < potentialProfit? potentialProfit : maxProfit;
			}
		}

		return maxProfit;
	}

	public static void alternateArray(int[] input)
	{
		int length = input.length;
		for(int i = 0; i < length - 1; i++)
		{
			int nextIndex = i + 1;
			if(i % 2 == 0)
			{
				if(input[i] > input[nextIndex])
				{
					swap(
							input,
							i,
							nextIndex);
				}
			}
			else
			{
				if(input[i] < input[nextIndex])
				{
					swap(
							input,
							i,
							nextIndex);
				}
			}

		}
	}

	public static void swap(
			int[] input,
			int i,
			int nextIndex)
	{
		int temp = input[i];
		input[i] = input[nextIndex];
		input[nextIndex] = temp;
	}

	public static List<Integer> primeNumberSieve(int terminus)
	{
		List<Integer> primes = new ArrayList<>();
		List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(terminus  + 1, true));

		isPrime.set(0, false);
		isPrime.set(1, false);

		for(int p = 2; p <= terminus; ++p)
		{
			if(isPrime.get(p))
			{
				primes.add(p);

				// sieve
				for(int i = p; i <= terminus; i += p)
				{
					isPrime.set(i, false);
				}
			}
		}

		return primes;
	}

	public static List<Integer> getPrimes(int terminus)
	{
		List<Integer> primes = new ArrayList<>();

		// Add list of small primes ... this could be a large list (thousands of known primes)
		// since pre-calculation and caching would be of better performance.
     	primes.add(2);
		primes.add(3);
		primes.add(5);
		primes.add(7);
		primes.add(11);
		primes.add(13);
		primes.add(17);

		if (terminus <=17)
		{
			primes.removeIf(s -> s.compareTo(terminus) == 1);
			return primes;
		}

		for(int i = 18; i <= terminus; i++)
		{
			if(i % 2 == 0)
			{
				continue;
			}
			else if(i % 3 == 0)
			{
				continue;
			}
			else if(i % 5 == 0)
			{
				continue;
			}
			else if(i % 7 == 0)
			{
				continue;
			}

			// maybe prime
			int finalI = i;
			if(primes
					.stream()
					.anyMatch(s -> finalI % s == 0))
			{
				continue;
			}
			else
			{
				primes.add(i);
			}
		}

		return primes;
	}

	/**
	 * Takes in an input list and a list describing the permutation to perform.
	 * @param input
	 * @param permutation
	 * @param <T>
	 */
	public static <T> void permute(T[] input, int[] permutation)
	{
		int permuteSize = permutation.length;
		for(int i = 0; i < permuteSize; i++)
		{
			int next = i;

			// cycle through while we are still swapping
			// swap (input[i] --> input[p[next]])
			// set next to p[next] as this is the index we just overwrote a
			// and stuffed into input[i]
			// make p[swapIndex] negative to mark it processed
			// loop while next is not negative
			while(permutation[next] >= 0)
			{
				// save off the value of p(next)
				int swapIndex = permutation[next];

				// swap i with p(next)

				// get value @location i
				T tempValue = input[i];

				// overwrite location i with the value from the indicated swap index
				input[i] = input[swapIndex];

				// save the old value of i to location input[swapIndex]
				input[swapIndex] = tempValue;

				// set our tracking marker (negate it)
				permutation[next] = swapIndex - permuteSize;

				// set the next index in the input array to the swap index location
				next = swapIndex;
			}
		}

		for(int i = 0; i < permuteSize; i++)
		{
			permutation[i] =  permuteSize + permutation[i];
		}
	}

	/**
	 * There exist exactly n! permutations of n elements. This function gives the next
	 * permutation in dictionary order. I.e., permutation p appears before q if the first place
	 * where p and q differ in the array representation starting from index 0, the corresponding
	 * index for p is less tha q. Ex: p <2, 0 ,1> < <2, 1, 0> as p < q at index 1. <0, 1,2> is the
	 * smallest permutation ordering of this set.
	 * @param permutation
	 * @return
	 */
	public static int[] getNextPermutation(int[] permutation)
	{
		int[] nextPermutation = java.util.Arrays.copyOf(permutation, permutation.length);
		int minValue = Integer.MAX_VALUE;
		int lastIndex = nextPermutation.length - 1;
		int minIndex = lastIndex;

		for(int i = lastIndex; i > 0; i--)
		{
			// track the min value index
			int currentValue = nextPermutation[i];
			if (minValue > currentValue)
			{
				minValue = currentValue;
				minIndex = i;
			}

			// using left / right for reading clarity
			int rightIndex = i;
			int leftIndex = i - 1;
			if(nextPermutation[rightIndex] > nextPermutation[leftIndex])
			{
				// use the smallest number to the right of the left index and swap that.
				Arrays.swap(nextPermutation, leftIndex, minIndex);
				java.util.Arrays.sort(nextPermutation, leftIndex + 1, lastIndex + 1);
				break;
			}
		}

		return nextPermutation;
	}

	public static int[] sample(int[] inputs, int sampleSize)
	{
		int lastIndex = inputs.length - 1;
		int[] sample = java.util.Arrays.copyOf(inputs, inputs.length);
		Random rand = new Random();

		for(int i = 0; i <= sampleSize; i++)
		{
			Arrays.swap(sample, i, rand.nextInt(lastIndex - i));
		}

		return java.util.Arrays.copyOfRange(sample, 0, sampleSize);
	}

	public static List<List<Integer>> getAllPossibleSamples(int[] inputs, int sampleSize)
	{
		Random random = new Random();
		List<List<Integer>> samples = new ArrayList<>();

		// The elements in the set
		final int setLength = inputs.length;

		double nFactorial = Math.pow(
				2,
				setLength);

		// allocate an array to hold our thing
		int[] binary = new int[(int) nFactorial];

		// while i < total combinations
		for (int i = 0; i < nFactorial; i++)
		{
			int b = 1;
			int num = i;
			int count = 0;

			binary[i] = 0;

			while (num > 0)
			{
				if (num % 2 == 1){ count++; }

				binary[i] += (num % 2) * b;
				num >>>= 1;
				b = b * 10;
			}
			if (count == sampleSize)
			{
				List<Integer> permutation = new ArrayList<>(5);
				for (int j = 0; j < setLength; j++)
				{
					if(binary[i] % 10 == 1)
					{
						permutation.add(inputs[j]);
					}
					binary[i] /= 10;
				}

				samples.add(permutation);
			}
		}

		return samples;
	}

	public static Set<Integer[]> getAllPossiblePermutations(int[] inputs)
	{
		if (inputs == null)
			return null;

		Set<Integer[]> perms = new HashSet<>();

		//base case
		if (inputs.length == 0){
			perms.add(new Integer[0]);
			return perms;
		}

		//shave off first int then get sub perms on remaining ints.
		//...then insertHead the first into each position of each sub perm..recurse

		int first = inputs[0];
		int[] remainder = java.util.Arrays.copyOfRange(inputs,1,inputs.length);
		Set<Integer[]> subPerms = getAllPossiblePermutations(remainder);
		for (Integer[] subPerm: subPerms){
			for (int i=0; i <= subPerm.length; ++i){ // '<='   IMPORTANT !!!
				Integer[] newPerm = java.util.Arrays.copyOf(subPerm, subPerm.length+1);
				for (int j=newPerm.length-1; j>i; --j)
					newPerm[j] = newPerm[j-1];
				newPerm[i]=first;
				perms.add(newPerm);
			}
		}

		return perms;
	}

	/**
	 * For each chunk, sample the chunk.
	 */
	public static void onlineSampling()
	{
		// options:
		// 1. determine chunk size based on a statistically significant sample size per population
		// 2. Fix samples at N samplings using an interval t. Then use a weighted least square
		// predictor algorithm to adjust sampling frequency as new data comes in.
		// 3. Time sampling based on poisson distribution

		// using poisson distribution

		timer.schedule(new PoissonTask(), 0);
	}

	/**
	 * Gets an permutation guaranteed to be uniformly random in distribution
	 *
	 * Design algorithm which creates uniformly random permutations of {0, 1, ...., n-1}
	 * You are given a random number generator that generates one number in the Set
	 * Minimize the number of calls to the generator to generate your random distribution
	 */
	public static int[] getRandomPermutation(int[] input)
	{
		int length = input.length;
		int[] permutation = new int[length];

		for(int i = 0; i < length; i++)
		{
			permutation[i] = i;
		}

		// use our random sampling algorithm to generate a uniform random sample the size of the
		// input array, the contents of which are the indexes into that array.
		return sample(permutation, length);
	}

	/**
	 * Generates a random subset of size k from a set of numbers 1...n-1
	 * @param n
	 * @param k
	 * @return
	 */
	public static List<Integer> getRandomSubset(int n, int k)
	{
		List<Integer> result = new ArrayList<>();
		Map<Integer, Integer> changedElements = new HashMap<>();
		Random rand = new Random();

		// store the *changed* elements in the hash table
		// we are recording only the swaps that would occur in the O(n) sampling algorithm
		// to reach O(k)
		for(int i = 0; i < k; i++)
		{
			int nextInt = rand.nextInt(n - 1);
			Optional<Integer> a = Optional.ofNullable(changedElements.get(nextInt));
			Optional<Integer> b = Optional.ofNullable(changedElements.get(i));

			if(!a.isPresent() && !b.isPresent())
			{
				changedElements.put(nextInt, i);
				changedElements.put(i, nextInt);
			}
			else if(a.isPresent() && !b.isPresent())
			{
				changedElements.put(nextInt, i);
				changedElements.put(i, a.get());
			}
			else if(!a.isPresent() && b.isPresent())
			{
				changedElements.put(nextInt, b.get());
				changedElements.put(i, nextInt);
			}
			else if(a.isPresent() && b.isPresent())
			{
				changedElements.put(nextInt, b.get());
				changedElements.put(i, a.get());
			}
		}

		for(int i = 0; i < k; i++)
		{
			result.add(changedElements.get(i));
		}

		return result;
	}

	/*
	Given a set of numbers N, and their probabilities
	 */
	public static int generateNonUniformRandomNumber(Map<Integer, Double> inputs)
	{
		List<Integer> values = new ArrayList<>(inputs.keySet());

		List<Double> prefixSumOfProbabilities = new ArrayList<>();
		prefixSumOfProbabilities.add(0.00);

		// Create the endpoints for the intervals corresponding to the probabilities
		for(Double value : inputs.values())
		{
			// basically p[i - 1] + value
			prefixSumOfProbabilities.add(
					prefixSumOfProbabilities.get(
							prefixSumOfProbabilities.size() - 1) + value);
		}

		Random rand = new Random();

		// returns next double between 0.00 and 1.0
		double nextRand = rand.nextDouble();

		// find the index of the interval that nextRand lies closest to
		int it = Collections.binarySearch(prefixSumOfProbabilities, nextRand);

		if(it < 0)
		{
			/*
			 We want the first index in the array (of probabilities) that is
			 greater than the key.

			 when a key is not present in the array, Collections.binarySearch returns
			 the negative of 1 plus the smallest number whose index is greater than the search term.

			 Therefore if we take the return of the search and add 1 to it we get the index of
			 the key.
			  */

			final int intervalIndex = (Math.abs(it) - 1) -1;
			return values.get(intervalIndex);
		}
		else
		{
			// the search term lies in the array. Because nextRand is a uniform double the
			// chance that it is equals an endpoint is very small, but should be handled.
			return values.get(it);
		}
	}

	public static List<Integer> computeSpiralOrdering(List<List<Integer>> matrix)
	{
		List<Integer> spiralOrder = new ArrayList<>();
		int m = matrix.size();
		int n = matrix.get(0).size();

		int startCol = 0;
		int startRow = 0;
		int colLength = n - 1;

		for(int rowLength = m - 1; rowLength >= 1; rowLength-=2)
		{
			// first row
			for(int j = 0; j < rowLength; j++)
			{
				spiralOrder.add(matrix.get(startCol + j).get(startRow));
			}

			// last column
			for(int j = 0; j < colLength; j++)
			{
				spiralOrder.add(matrix.get(rowLength).get(startRow + j));
			}

			// last row
			for(int j = rowLength; j > 0; --j)
			{
				spiralOrder.add(matrix.get(startCol + j).get(colLength));
			}

			// first column
			for(int j = colLength; j > 0; --j)
			{
				spiralOrder.add(matrix.get(startCol).get(startRow + j));
			}

			startCol++;
			startRow++;
		}

		// even matrices have a center column, even does not.
		if(n != m && n % 2 == 0)
		{
			for (int j = 0; j < m - 1; j++)
			{
				spiralOrder.add(matrix
						.get(startCol)
						.get(startRow +j));
			}
		}
		else if((n == m) && (n % 2 != 0))
		{
			// if this is a square matrix
			spiralOrder.add(matrix
					.get(startCol)
					.get(startRow));
		}

		return spiralOrder;
	}

	private static List<Integer> getColData(
			List<List<Integer>> matrix,
			int startCol,
			int startRow,
			int size)
	{
		List<Integer> data = new ArrayList<>();

		for(int i = 0; i < size; i++)
		{
			data.add(matrix.get(startCol).get(startRow + i));
		}

		return data;
	}

	private static List<Integer> getRowOrder(
			List<List<Integer>> matrix,
			int startCol,
			int startRow,
			int size)
	{
		List<Integer> rowData = new ArrayList<>();

		for(int i = 0; i < size; i++)
		{
			rowData.add(matrix.get(startCol + i).get(startRow));
		}

		return rowData;
	}

	public static void rotate2DMatrix(int[][] matrix)
	{
		int n = matrix[0].length;
		int m = matrix.length - 1;
		int layerIndex = 0;
		int startRow = 0;

		for(int i = 0; i < n/2; i++)
		{
			// reverse a layer
			for (int j = 0; j < (m - (layerIndex * 2)); j++)
			{
				int a = matrix[j + layerIndex][layerIndex];
				int b = matrix[m - layerIndex][j + layerIndex];
				int c = matrix[m - j - layerIndex][m - layerIndex];
				int d = matrix[layerIndex][m - j - layerIndex];

				matrix[m - layerIndex][j + layerIndex] = a;
				matrix[m - j - layerIndex][m - layerIndex] = b;
				matrix[layerIndex][m - j - layerIndex] = c;
				matrix[j + layerIndex][layerIndex] = d;
			}

			layerIndex++;
		}
	}

	public static int[][] computePascalsTriangle(int rows)
	{
		int rowSize = 2;
		int[][] triangle = new int[rows][];
		int[] initialRow = new int[]{1};
		triangle[0] = initialRow;

		for(int n = 1; n < rows; n++)
		{
			int[] row = new int[rowSize];
			for(int k = 0; k < rowSize; k++)
			{
				int left = (k - 1) >= 0 ? triangle[n - 1][k - 1] : 0;
				int right = k < triangle[n-1].length ? triangle[n - 1][k] : 0;

				row[k] = left + right;
			}

			triangle[n] = row;
			rowSize++;
		}

		return triangle;
	}
}

class PoissonTask extends TimerTask
{
	public static final int RAND_MAX = 32767;

	@Override
	public void run()
	{
		int[] buffer = getBuffer();

		// get the sample, then do something with it .. in this case stuff it in a global :)
		// in the real impl we'd add these to a queue we can age off old samples from.
		Arrays.samples.add(Arrays.sample(buffer, Arrays.SAMPLE_SIZE));

		// schedule next occurrence based on incoming rate.
		timer.schedule(new PoissonTask(), nextPeriod(getRate()).longValue());
	}

	private int getRate()
	{
		// replace with a real rate calculation
		return 40;
	}

	/**
	 * Gets the buffer ... in a real implementation this would grab the buffer from somewhere else.
	 * @return
	 */
	private int[] getBuffer()
	{
		Random rand = new Random();
		int[] buffer = new int[10000];

		for(int i = 0; i < buffer.length; i++)
		{
			buffer[i] =  new Integer(rand.nextInt());
		}

		return buffer;
	}

	private Double nextPeriod(float rate)
	{
		return floor(-1.0 * log(1.0 - random()*1.0/ RAND_MAX));
	}
}
