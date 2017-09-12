package EPI.Strings;

/**
 * Created by clester on 9/11/2017.
 */
public class BoyerMoore
{

	public static final int STRING_NOT_FOUND = -1;

	public int search(String source, String target)
	{
		int sourceLength = source.length();
		int targetLength = target.length();

		if(sourceLength < targetLength)
		{
			return STRING_NOT_FOUND;
		}

		if(sourceLength == 0 | targetLength == 0)
		{
			return STRING_NOT_FOUND;
		}

		// start at source[target length] and check backwards
		for(int i = targetLength - 1; i < sourceLength - 1;)
		{
			int targetIndex = targetLength - 1;

			if(source.charAt(i) == target.charAt(targetIndex))
			{
				// found a match, check the rest of the string.
				int sourceIndex = i - 1;
				boolean found = true;
				while(targetIndex >= 0)
				{
					if(source.charAt(sourceIndex--) != target.charAt(targetIndex))
					{
						found = false;
						break;
					}

					targetIndex--;
				}

				if(found )
				{
					return sourceIndex;
				}
			}

			i += targetIndex;
		}

		return STRING_NOT_FOUND;
	}
}
