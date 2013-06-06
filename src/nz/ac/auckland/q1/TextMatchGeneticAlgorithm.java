package nz.ac.auckland.q1;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class TextMatchGeneticAlgorithm implements Runnable{
	public static final double CrossoverProbability = 0.87;
	public static final double MutationProbability = 0.02;
	
	private String targetString ;
	private static char[] validChars;
	private boolean runParallel;
	private int populationSize;
	private TextMatchGenome[] currentPopulation;
	private int[] currentScore;
	private int generation;
	
	public TextMatchGeneticAlgorithm(boolean runParallel, String targetString, int populationSize) throws InterruptedException{
		validChars = this.generateStrings();
		this.runParallel = runParallel;
		this.targetString = targetString;
		this.populationSize = populationSize;
		this.generation = 0;
		if (currentPopulation == null){
			currentPopulation = this.createRandomGeneration();
		} else {
			currentPopulation = this.createNextGeneration();
		}
	}

	public void run() {
		if (currentPopulation == null) {
			this.currentPopulation = this.createRandomGeneration();
		} else {
			try {
				currentPopulation = this.createNextGeneration();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while (!Thread.interrupted()){
			try {
				currentPopulation = this.createNextGeneration();
				generation++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Thread.currentThread().interrupt();
	}
	public String findBestMatch(){
		if ( currentPopulation[0] != null) {
				TextMatchGenome bestMatch = currentPopulation[0];
			for (TextMatchGenome tmg : currentPopulation){
				if (tmg.getFitness() > bestMatch.getFitness()) bestMatch = tmg;
			}
			return bestMatch.getCurrentPop();
		} else {
			return "";
		}
	}
	public int getGeneration() {
		return generation;
	}

	public TextMatchGenome[] getCurrentPopulation() {
		return currentPopulation;
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
//		for (TextMatchGenome tm : textMatchGenome){
//			tm = createRandomPopulation();
//		}
		for (int i = 0; i < populationSize; i++){
			textMatchGenome[i] = createRandomPopulation();
//			System.out.println("createRandomGeneration: " + i);
		}
		return textMatchGenome;
	}
	public TextMatchGenome[] createNextGeneration() throws InterruptedException{
		
		int maxFitness = this.currentPopulation[0].getFitness();
		int minFitness = maxFitness;
		int sumOfWeights = 0;
		TextMatchGenome[] tmg = new TextMatchGenome[populationSize];
		for (int i = 0; i < populationSize; i++){
			if (maxFitness < currentPopulation[i].getFitness()){
				maxFitness = currentPopulation[i].getFitness();
			}
			if (minFitness > currentPopulation[i].getFitness()){
				minFitness = currentPopulation[i].getFitness();
			}
		}
//		System.out.println("max: min: " + maxFitness + " " + minFitness);
		this.currentScore = new int[populationSize]; 
		for (int i = 0; i < populationSize; i++){
			// current breeding weight = (largest difference – current difference + 1).
			currentScore[i] = (maxFitness - minFitness) - currentPopulation[i].getFitness() + 1;
			sumOfWeights += currentScore[i];
		}
		System.out.println("sumOfMaxMinusFitness: " + sumOfWeights);
		if (runParallel){

			List<Callable<Void>> callables = new LinkedList<Callable<Void>>();

			int chunk = 500;

			for (int low = 0; low < populationSize; low += chunk) {
				final int Low = low;
				final int High = Math.min(populationSize, low + chunk);
				final int sumOfWeightss = sumOfWeights, maxFitnesss = maxFitness;
				final TextMatchGenome[] tmgg = tmg;
				callables.add(new Callable<Void>() {
					public Void call() {
						for (int i = Low; i < High; i++) {
							for (int j = 0; j < populationSize / 2; j++){
								TextMatchGenome[] tmg2 = createChildren(
										currentPopulation[randomParent(sumOfWeightss, maxFitnesss, currentScore)], 
										currentPopulation[randomParent(sumOfWeightss, maxFitnesss, currentScore)]);
								//createChildren(randomParent(sumOfMaxMinusFitness, maxFitness, currentScore), randomParent(sumOfMaxMinusFitness, maxFitness, currentScore))[1];
								tmgg[i*2] = tmg2[0];
								tmgg[i*2 + 1] = tmg2[0];
							}
						}
						return null;
					}
				});
				int maxallowed_threads = 1 * Runtime.getRuntime()
						.availableProcessors();
				ExecutorService executor = new ForkJoinPool(
						maxallowed_threads);
				List<Future<Void>> futures = executor.invokeAll(callables);
			}

		} else {
			for (int i = 0; i < populationSize / 2; i++){
				TextMatchGenome[] tmg2 = createChildren(
						currentPopulation[randomParent(sumOfWeights, maxFitness, currentScore)], 
						currentPopulation[randomParent(sumOfWeights, maxFitness, currentScore)]);
				//createChildren(randomParent(sumOfMaxMinusFitness, maxFitness, currentScore), randomParent(sumOfMaxMinusFitness, maxFitness, currentScore))[1];
				tmg[i*2] = tmg2[0];
				tmg[i*2 + 1] = tmg2[0];
			}
		}
		return tmg;
	}
	private TextMatchGenome[] createChildren(TextMatchGenome p1, TextMatchGenome p2){
//		TextMatchGenome c1,c2;
		if (ThreadLocalRandom.current().nextDouble(1.0) < CrossoverProbability){
			return crossover(p1, p2);
		} else {
			return new TextMatchGenome[]{p1,p2};
		}
	}
	private TextMatchGenome[] crossover(TextMatchGenome p1, TextMatchGenome p2){
		int crossoverPoint1 = ThreadLocalRandom.current().nextInt(1, p1.getCurrentPop().length());
		int crossoverPoint2 = crossoverPoint1;
		TextMatchGenome c1 = mutate(new TextMatchGenome((p1.getCurrentPop().substring(0,crossoverPoint1) + p2.getCurrentPop().substring(crossoverPoint1)), p1.getTarget()));
		TextMatchGenome c2 = mutate(new TextMatchGenome((p2.getCurrentPop().substring(0,crossoverPoint1) + p1.getCurrentPop().substring(crossoverPoint1)), p2.getTarget()));
		return new TextMatchGenome[]{c1,c2};
	}
	private TextMatchGenome mutate(TextMatchGenome tmg){
		if ( ThreadLocalRandom.current().nextDouble(1.0) < MutationProbability){
//			System.out.print("change tmg from " + tmg.getCurrentPop() + " to ");
			int position = ThreadLocalRandom.current().nextInt(0,tmg.getCurrentPop().length());
			tmg.setCurrentPop(tmg.getCurrentPop().substring(0,position) + validChars[ThreadLocalRandom.current().nextInt(validChars.length)] + tmg.getCurrentPop().substring(position+1));
//			System.out.println(tmg.getCurrentPop());
		}
		return tmg;
	}
	// code from lab
	public int randomParent(int totalFitness, int maxUnfitness, int[] currentScore)
	{
		double prob = ThreadLocalRandom.current().nextDouble(1.0);
		int val = (int) (prob * totalFitness);

		for (int i = 0; i < currentScore.length; i++)
		{
			int currentFitness = maxUnfitness - currentScore[i];
			
			if (val < currentFitness)
				return i;
			
			val -= currentFitness;
		}

		throw new RuntimeException("Should never reach here here.");
	}
}
