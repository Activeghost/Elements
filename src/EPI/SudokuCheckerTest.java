package EPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by clester on 8/8/2017.
 */
public class SudokuCheckerTest
{
	final Integer[][] partialAssignment = new Integer[][]
			{
					{ 5, 6, null, 8, 4, 7, null, null, null },
					{ 3, null, 9, null, null, null,6, null, null },
					{ null, null, 8, null, null,null, null, null, null },
					{ null, 1, null, null, null,8, null, 4, null },
					{ 7, 9, null, 6, null, 2, null, 1, 8 },
					{ null, 5, null, null, 3, null, null, 9, null },
					{ null, null, null, null, null, null, 2, null, null },
					{ null, null, 6, null, null, null, 8, null, 7 },
					{ null, null, null, 3, 1, 6, null, 5, 9 },
			};

	final Integer[][] fullSolutionFalse = new Integer[][]
			{
					{ 5, 6, 1, 8, 4, 7, 9, 2, 3 },
					{ 3, 7, 9, 5, 2, 1, 6, 8, 4 },
					{ 4, 2, 8, 9, 6, 3, 1, 7, 5 },
					{ 6, 1, 3, 7, 3, 8, 5, 4, 2 },
					{ 7, 9, 4, 6, 5, 2, 3, 1, 8 },
					{ 8, 5, 2, 1, 3, 4, 7, 9, 6 },
					{ 9, 3, 5, 4, 7, 8, 2, 6, 1 },
					{ 1, 4, 6, 2, 9, 5, 8, 8, 7 },
					{ 2, 8, 7, 3, 1, 6, 4, 5, 9 },
			};

	final Integer[][] fullSolutionTrue = new Integer[][]
			{
					{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
					{ 6, 7, 2, 1, 9, 5, 3, 4, 8 },
					{ 1, 9, 8, 3, 4, 2, 5, 6, 7 },
					{ 8, 5, 9, 7, 6, 1, 4, 2, 3 },
					{ 4, 2, 6, 8, 5, 3, 7, 9, 1 },
					{ 7, 1, 3, 9, 2, 4, 8, 5, 6 },
					{ 9, 6, 1, 5, 3, 7, 2, 8, 4 },
					{ 2, 8, 7, 4, 1, 9, 6, 3, 5 },
					{ 3, 4, 5, 2, 8, 6, 1, 7, 9 },
			};
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void check() throws Exception
	{
		Assert.assertTrue(new SudokuChecker(9).check(partialAssignment));
		Assert.assertFalse(new SudokuChecker(9).check(fullSolutionFalse));
		Assert.assertTrue(new SudokuChecker(9).check(fullSolutionTrue));
	}
}
