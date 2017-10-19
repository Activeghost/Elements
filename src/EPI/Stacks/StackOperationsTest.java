package EPI.Stacks;

import java.util.ArrayList;
import java.util.Collection;

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

	public static Collection<Object[]> getParameters()
	{
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "1729", 1729.0 });
		params.add(new Object[] { "3,4,+,2,x,1,+", 15.0 });
		params.add(new Object[] { "1,1,+,-2,x", -4.0 });
		params.add(new Object[] { "-641,6,/,28,/", -3.8154761904761902  });
		return params;
	}

	@Test
	public void evalRpnExpression()
	{
		for(Object[] paramObject : getParameters())
		{
			assertEquals(paramObject[1], StackOperations.evalRpnExpression((String)paramObject[0]));
		}
	}

}