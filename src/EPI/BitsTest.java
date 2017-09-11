package EPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by clester on 7/31/2017.
 */
public class BitsTest
{
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void powerTest() throws Exception
	{
		Assert.assertTrue(Bits.power(4,-1) == .25);
		Assert.assertTrue(Bits.power(10,2) == 100);
		Assert.assertTrue(Bits.power(4,4) == 256);
	}

	@Test
	public void multiply() throws Exception
	{
		Assert.assertTrue(Bits.multiply(10,20) == 200);
		Assert.assertTrue(Bits.multiply(0,20) == 0);
		Assert.assertTrue(Bits.multiply(10,0) == 0);
		Assert.assertTrue(Bits.multiply(1,0) == 1);
		Assert.assertTrue(Bits.multiply(112,1) == 112);
	}

	@Test
	public void add() throws Exception
	{
		Assert.assertTrue(Bits.add(10,20) == 30);
		Assert.assertTrue(Bits.add(0,20) == 20);
		Assert.assertTrue(Bits.add(111,1) == 112);
	}
}
