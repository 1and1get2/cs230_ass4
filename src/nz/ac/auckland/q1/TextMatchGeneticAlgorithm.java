package nz.ac.auckland.q1;

import java.util.concurrent.ThreadLocalRandom;

public class TextMatchGeneticAlgorithm {
	public static final double CrossoverProbability = 0.87;
	public static final double MutationProbability = 0.02;
	
	private String targetString ;
	private static char[] validChars;
	private boolean runParallel;
	private int populationSize;
	private TextMatchGenome[] currentPopulation;
	
	public TextMatchGeneticAlgorithm(boolean runParallel, String targetString, int populationSize){
		validChars = this.generateStrings();
		this.runParallel = runParallel;
		this.targetString = generateRandomMonkey(targetString.length());
		this.populationSize = populationSize;
		
		if (currentPopulation == null){
			currentPopulation = this.createRandomGeneration();
		} else {
			currentPopulation = this.createNextGeneration();
		}
	}

	public String getTargetString() {
		return targetString;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

	public boolean isRunParallel() {
		return runParallel;
	}

	public void setRunParallel(boolean runParallel) {
		this.runParallel = runParallel;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
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
	
	public TextMatchGenome createRandomPopulation(){
		return new TextMatchGenome(this.generateRandomMonkey(targetString.length()), targetString);
//		TextMatchGenome[] textMatchGenome = new TextMatchGenome[1];
//		textMatchGenome[0] = new TextMatchGenome(this.generateRandomMonkey(targetString.length()), targetString);
//		return textMatchGenome;
	}
	public TextMatchGenome[] createRandomGeneration(){
		TextMatchGenome[] textMatchGenome = new TextMatchGenome[populationSize];
		for (TextMatchGenome tm : textMatchGenome){
			tm = createRandomPopulation();
		}
		return textMatchGenome;
	}
	public TextMatchGenome[] createNextGeneration(){
		int maxFitness = this.currentPopulation[0].getFitness();
		int sumOfMaxMinusFitness = 0;
		for (int i = 0; i < populationSize; i++){
			if (maxFitness < currentPopulation[i].getFitness()){
				maxFitness = currentPopulation[i].getFitness();
			}
		}
	}
}
