package EPI.BitManipulation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * BitOperations class tests
 */
public class BitOperationsTest
{
	@Test
	public void powerAlt() throws Exception
	{
		Assert.assertTrue(BitOperations.powerAlt(3, 2) == 9);
		Assert.assertTrue(BitOperations.powerAlt(4, -1) == .25);
		Assert.assertTrue(BitOperations.powerAlt(10, 4) == 10000);
		Assert.assertTrue(BitOperations.powerAlt(2, 8) == 256);
	}

	@Test
	public void power() throws Exception
	{
		Assert.assertTrue(BitOperations.power(3, 2) == 9);
		Assert.assertTrue(BitOperations.power(4, -1) == .25);
		Assert.assertTrue(BitOperations.power(10, 4) == 10000);
		Assert.assertTrue(BitOperations.power(2, 6) == 64);
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void powerTest() throws Exception
	{
		Assert.assertTrue(BitOperations.power(4, -1) == .25);
		Assert.assertTrue(BitOperations.power(10, 2) == 100);
		Assert.assertTrue(BitOperations.power(4, 4) == 256);
	}

	@Test
	public void multiply() throws Exception
	{
		Assert.assertTrue(BitOperations.multiply(10, 20) == 200);
		Assert.assertTrue(BitOperations.multiply(0, 20) == 0);
		Assert.assertTrue(BitOperations.multiply(10, 0) == 0);
		Assert.assertTrue(BitOperations.multiply(1, 0) == 1);
		Assert.assertTrue(BitOperations.multiply(112, 1) == 112);
	}

	@Test
	public void add() throws Exception
	{
		Assert.assertTrue(BitOperations.add(10, 20) == 30);
		Assert.assertTrue(BitOperations.add(0, 20) == 20);
		Assert.assertTrue(BitOperations.add(111, 1) == 112);
	}
}
