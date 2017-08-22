package EPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by clester on 8/16/2017.
 */
public class RotatedMatrixTest
{
	int[][] matrix;
	int[][] expectedResult;

	@Before
	public void setUp() throws Exception
	{
		matrix = new int[][]{
				{  1,  5,  9, 13 },
				{  2,  6, 10, 14 },
				{  3,  7, 11, 15 },
				{  4,  8, 12, 16 }
		};

		expectedResult = new int[][]{
				{ 13, 14, 15, 16 },
				{  9, 10, 11, 12 },
				{  5,  6,  7,  8 },
				{  1,  2,  3,  4 }
		};

	}

	@Test
	public void read() throws Exception
	{
		for(int i = 0; i < expectedResult.length; i++)
		{
			for(int j = 0; j < expectedResult.length; j++)
			{
				Assert.assertEquals(expectedResult[i][j], new RotatedMatrix(matrix).read(i,j));
			}
		}
	}

	@Test
	public void write() throws Exception
	{
		RotatedMatrix rotatedMatrix = new RotatedMatrix(matrix);
		Assert.assertEquals(13, rotatedMatrix.read(0,0));
		rotatedMatrix.write(0,0, 23);

		Assert.assertEquals(23, rotatedMatrix.read(0,0));
	}
}
