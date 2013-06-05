package nz.ac.auckland.q3;

public class ScoreMatcher
{
	
	public int diff(String target, String currentPop)
	{
		if (target.length() != currentPop.length())
			throw new RuntimeException("Strings need to be of equal length");
		
		int diff = 0;

		for (int i = 0; i < target.length(); i++)
		{
			if (target.charAt(i) != currentPop.charAt(i))
				diff++;
		}

		return diff;
	}


}
