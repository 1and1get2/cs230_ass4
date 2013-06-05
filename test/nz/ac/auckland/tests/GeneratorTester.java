package nz.ac.auckland.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import nz.ac.auckland.q2.Generator;
import org.junit.Test;

public class GeneratorTester
{

	private Generator generator = new Generator();
	
	@Test
	public void generatorUniqueTest()
	{
		Set<Character> set = new HashSet<Character>();
		char[] chars = generator.generateStrings();
		
		for (char c : chars)
			set.add(c);
		
		assertEquals(chars.length, set.size());

	}
	
	@Test
	public void generatorValidCharTest()
	{
		for (char c : generator.generateStrings())
		{
			if (c >= 32 || c <= 126)
				continue;
			
			if (c == '\n' || c == '\r')
				continue;
			
			fail("Should never reach here!");
		}
	}
	
	@Test
	public void generatorMonkeys()
	{
		String monkey = generator.generateRandomMonkey(10, generator.generateStrings());
		
		assertNotNull(monkey);
		assertEquals(10, monkey.length());
	}
	
	

}
