package EPI.Arrays;

import java.util.*;

import EPI.Arrays.ArrayOperations;

public class RotatedMatrix
{
	int[][] _matrix;

	public RotatedMatrix(int[][] inputMatrix)
	{
		Optional maybeMatrix = Optional.ofNullable(inputMatrix);
		if(maybeMatrix.isPresent())
		{
			// get our own copy so no one else can modify this underneath us.
			_matrix = ArrayOperations.cloneArray(inputMatrix);
		}
		else
		{
			throw new MissingFormatArgumentException("An input array is required");
		}
	}

	public int read(int x, int y)
	{
		return _matrix[y][_matrix.length - x - 1];
	}

	public void write(int x, int y, int value)
	{
		_matrix[_matrix.length - y - 1][x] = value;
	}
}
