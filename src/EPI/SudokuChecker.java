package EPI;

import java.util.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class SudokuChecker
{
	final int _size;
	private int _subGridLength;

	public SudokuChecker(int size)
	{
		_size = size;
		_subGridLength = (int) Math.sqrt(size);
	}

	/**
	 * Rules:
	 * 1. Each column must contain only unique numbers
	 * 2. Each row must contain only unique numbers
	 * 3. Each subgrid (square) must contain unique numbers
	 * @param grid
	 * @return
	 */
	public boolean check(Integer[][] grid)
	{
		// 1. Check columns (this impl is O(n^2))
		for(Integer[] column : grid)
		{
			int lastValue = Integer.MIN_VALUE;
			Arrays.sort(column, Comparator.nullsLast(Comparator.naturalOrder()));

			for(Integer value : column)
			{
				if(!Optional.ofNullable(value).isPresent())
				{
					// exit early since these are sorted as last.
					break;
				}

				else if(value == lastValue)
				{
					return false;
				}
			}
		}

		// 2. Check rows
		for(int y = 0; y < grid.length; y++)
		{
			// numbers are always 0-9 in Sudoku
			boolean[] row = new boolean[10];
			java.util.Arrays.fill(row, false);

			for(int x = 0; x < grid.length; x++)
			{
				 Optional<Integer> value = Optional.ofNullable(grid[y][x]);
				 if(!value.isPresent())
				 {
				 	continue;
				 }

				if(row[value.get()]){ return false;}
				else{
					row[value.get()] = true;
				}
			}
		}

		// 3. Check subgrids
		int startRow = 0;
		int startCol = 0;

		// Start @0,0. then 0,3 --> 0,6
		// Start @3,0. then 3,3 --> 3,6
		// Start @6,0. then 6,3 --> 6,6
		double gridStepSize = Math.sqrt(grid.length);
		for(int I = 0; I < grid.length; I+= gridStepSize)
		{
			for(int J = 0; J < grid.length; J+= gridStepSize)
			{
				if (!processSubGrid(
						grid,
						J,
						I))
				{
					return false;
				}
			}
		}

		return true;
	}

	private boolean processSubGrid(
			Integer[][] grid,
			int startRow,
			int startCol)
	{
		for(int i = startCol; i < _subGridLength; i++)
		{
			boolean[] row = new boolean[10];
			java.util.Arrays.fill(row, false);

			for(int j = startRow; j < _subGridLength; j++)
			{
				Optional<Integer> value = Optional.ofNullable(grid[i][j]);
				if(!value.isPresent()) { continue;}

				if(row[value.get()]){
					return false;
				}
				else{
					row[value.get()] = true;
				}
			}
		}

		return true;
	}
}
