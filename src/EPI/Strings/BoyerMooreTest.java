package EPI.Strings;

import java.util.List;

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

		final BoyerMoore boyerMoore = new BoyerMoore();
		validateResult(source, target, boyerMoore.search(source, target));

		target = "sodales";
		validateResult(source, target, boyerMoore.search(source, target));
	}

	private void validateResult(String source, String target, List<Integer> indexes)
	{
		assertEquals(2, indexes.size());

		for(int index : indexes)
		{
			assertEquals(target, source.substring(index, index + target.length()));
		}
	}
}