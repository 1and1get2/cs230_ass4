package nz.ac.auckland.tests;

import static org.junit.Assert.*;

import nz.ac.auckland.q3.ScoreMatcher;
import org.junit.Test;

public class DiffTester
{
	ScoreMatcher sm = new ScoreMatcher();

	@Test
	public void differentStringsTest()
	{	
		String t1 = "test";
		String t2 = "Test";
		assertFalse( sm.diff(t1, t2) == 0 );
	}
	
	@Test
	public void differentStringsTest1()
	{	
		String t1 = "Test";
		String t2 = "test";
		assertTrue( sm.diff(t1, t2) == 1 );
	}

	
	@Test
	public void sameStringsTest()
	{	
		String t1 = "Test";
		String t2 = "Test";
		assertTrue( sm.diff(t1, t2) == 0 );
	}
	
	@Test(expected = RuntimeException.class)
	public void diffException()
	{	
		String t1 = "Test";
		String t2 = "Test1";
		sm.diff(t1, t2);
		fail("Should have thrown exception");
	}

}
