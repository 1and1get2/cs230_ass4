package nz.ac.auckland.q2;

import java.util.concurrent.ThreadLocalRandom;

public class Generator
{
	String string ;
	char[] validChars;
	
	public Generator(){
		string = new String();
		validChars = this.generateStrings();
	}

	public char[] generateStrings()
	{
		char[] validChars = new char[2 + 95]; // NL, CR, printable ascii characters
		validChars[0] = '\n'; // NL
		validChars[1] = '\r'; // CR

		for (int i = 2; i < validChars.length; i++)
			validChars[i] = (char) (i + 30); // printable

		return validChars;
	}

	public String generateRandomMonkey(final int size)
	{
		return this.generateRandomMonkey(size, validChars);
	}
	
	public String generateRandomMonkey(final int size, final char[] validChars)
	{
		StringBuilder sb = new StringBuilder(size);

		for (int j = 0; j < size; j++)
		{
			sb.append(validChars[ThreadLocalRandom.current().nextInt(0, validChars.length)]);
		}

		return sb.toString();
	}

}
