package EPI.Strings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Created by clester on 9/11/2017.
 */
class BoyerMooreTest
{
	@Test
	void search()
	{
		String source = "sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum";
		String target = "in";

		int index = new BoyerMoore().search(source, target);
		assertEquals(85, index);
	}
}