package EPI.Strings;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Created by clester on 10/23/2017.
 */
class RabinKarpeTest
{
	private Collection<Object[]> getTestData()
	{
		Collection<Object[]> params = new ArrayList<>();
		String s = "the blind fox goes in circles at the dawn";

		// natural case
		params.add(new Object[] { s, "fox", 10 });
		params.add(new Object[] { s, " ", 3 });

		// boundaries
		params.add(new Object[] { s, "the", 0 });
		params.add(new Object[] { s, "dawn", s.length() - 4 });

		// negative test cases
		params.add(new Object[] { s, "test", -1 });
		params.add(new Object[] { s, "", -1 });
		params.add(new Object[] { s, null, -1 });
		params.add(new Object[] { s, null, -1 });
		params.add(new Object[] { null, null, -1 });
		params.add(new Object[] { "", null, -1 });
		params.add(new Object[] { "", "", -1 });

		return params;
	}

	@Test
	void indexOf()
	{
		for(Object[] params : getTestData())
		{
			assertEquals(params[2],
					new RabinKarpe(26).indexOf((String)params[0],(String)params[1]));
		}
	}

}