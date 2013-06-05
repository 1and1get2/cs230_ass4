package nz.ac.auckland.q4;

import java.util.concurrent.ThreadLocalRandom;

public class MonkeyMater
{

	public int randomParent(int totalFitness, int maxUnfitness, int[] currentScore)
	{
		double prob = ThreadLocalRandom.current().nextDouble(1.0);
		int val = (int) (prob * totalFitness);

		for (int i = 0; i < currentScore.length; i++)
		{
			int currentFitness = maxUnfitness - currentScore[1];
			
			if (val < currentFitness)
				return i;
			
			val -= currentFitness;
		}

		throw new RuntimeException("Should never reach here here.");
	}

}
