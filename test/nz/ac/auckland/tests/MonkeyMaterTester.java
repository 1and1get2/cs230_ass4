package nz.ac.auckland.tests;

import static org.junit.Assert.*;

import nz.ac.auckland.q4.MonkeyMater;
import org.junit.Test;

public class MonkeyMaterTester
{

	MonkeyMater mm = new MonkeyMater();
	
	@Test
	public void randomParent()
	{
		int[] currentScore = {200, 202, 203, 204, 202, 204, 201, 200, 203, 201};
		int x = mm.randomParent(30, 205, currentScore);
		
		assertTrue(x >= 0 && x < 10);
		System.out.println(x);
	}

}
