package EPI.Stacks;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Created by clester on 10/19/2017.
 */
public class StackOperationsTest
{
	public StackOperationsTest()
	{
	}

	private Collection<Object[]> getRpnTestData()
	{
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "1729", 1729.0 });
		params.add(new Object[] { "3,4,+,2,x,1,+", 15.0 });
		params.add(new Object[] { "1,1,+,-2,x", -4.0 });
		params.add(new Object[] { "-641,6,/,28,/", -3.8154761904761902  });
		return params;
	}

	private Collection<Object[]> getPnTestData()
	{
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "1729", 1729.0 });
		params.add(new Object[] { "+,3,4,x,2,+,1", 15.0 });
		params.add(new Object[] { "+,1,1,x,-2", -4.0 });
		params.add(new Object[] { "/,-641,6,/,28", -3.8154761904761902  });
		return params;
	}

	private Iterable<? extends Object[]> getWellFormedTestData() {
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "({}){()}", true });
		params.add(new Object[] { "([]){()}", true });
		params.add(new Object[] { "[()[]{()()}]", true });
		params.add(new Object[] { "3,4,+,2,x,1,+", false });
		params.add(new Object[] { "{)", false });
		params.add(new Object[] { "[()[]{()()", false });
		return params;
	}

	private Collection<Object[]> getNormalizationTestData()
	{
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "/usr/bin/gcc", "/usr/lib/../bin/gcc"});
		params.add(new Object[] { "scripts/awkscripts", "scripts//./../scripts/awkscripts/././"});
		return params;
	}

	private Collection<Building> getBuildingCollection()
	{
		Collection<Building> buildings = new ArrayList<>();
		Random rand = new Random();
		int count = rand.nextInt(100);
		for(int i = 0; i < count; i++)
		{
			buildings.add(new Building(i, rand.nextInt(100)));
		}

		return buildings;
	}

	@Test
	void getBuildingsWithAViewEastToWest() {
		StackOperations stackOperations = new StackOperations();
		Deque<Building> buildings = stackOperations.getBuildingsWithAViewEastToWest(getBuildingCollection().iterator());

		Iterator<Building> iter = buildings.iterator();
		Building previous = iter.next();
		while(iter.hasNext())
		{
			assertTrue(iter.next().height > previous.height);
		}
	}

	@Test
	void getBuildingsWithAView() {
		StackOperations stackOperations = new StackOperations();
		Deque<Building> buildings = stackOperations.getBuildingsWithAView(getBuildingCollection().iterator());

		Iterator<Building> iter = buildings.descendingIterator();
		Building previous = iter.next();
		while(iter.hasNext())
		{
			assertTrue(iter.next().height > previous.height);
		}
	}

	@Test
	void computeJumpOrderRecursive()
	{
	}

	@Test
	void computeJumpOrder()
	{
	}

	@Test
	void normalizePath() {
		StackOperations stackOperations = new StackOperations();
		for(Object[] paramObject : getNormalizationTestData())
		{
			assertEquals(paramObject[0], stackOperations.normalizePath((String)paramObject[1]));
		}
	}

	@Test
	public void evalRpnExpression()
	{
		StackOperations stackOperations = new StackOperations();
		for(Object[] paramObject : getRpnTestData())
		{
			assertEquals(paramObject[1], stackOperations.evalRpnExpression((String)paramObject[0]));
		}
	}

	@Test
	public void evalPolishNotation()
	{
		StackOperations stackOperations = new StackOperations();

		for(Object[] paramObject : getPnTestData())
		{
			assertEquals(paramObject[1], stackOperations.evalPolishNotation((String)paramObject[0]));
		}
	}

	@Test
	public void isWellFormed()
	{
		StackOperations stackOperations = new StackOperations();

		for(Object[] params : getWellFormedTestData())
		{
			assertEquals(params[1], stackOperations.isWellFormed((String)params[0]));
		}
	}
}