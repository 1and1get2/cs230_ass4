package nz.ac.auckland.q1;

public class TextMatchGenome {
	private String currentPop;
	private String target;
	private int fitness;

	public TextMatchGenome(String currentPop,String target){
		this.currentPop = currentPop;
		this.target = target;
		RecomputeFitness();
	}
	
	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
		RecomputeFitness();
	}
	public String getCurrentPop() {
		return currentPop;
	}

	public void setCurrentPop(String currentPop) {
		this.currentPop = currentPop;
		RecomputeFitness();
	}

	private void RecomputeFitness() {
		if (target.length() != currentPop.length())
			throw new RuntimeException("Strings need to be of equal length");
		if (currentPop != null && target != null) {
			int diffs = 0;
			for (int i = 0; i < target.length(); i++) {
				if (target.charAt(i) != currentPop.charAt(i))
					diffs++;
			}
			fitness = diffs;
		} else {
			// TODO: max
		}
	}

}
