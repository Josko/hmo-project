package hmo.project.ga;

import java.util.Random;

public final class AlphaCrossover implements CrossoverOperator {
	
	private final double alpha;
	
	public AlphaCrossover(final double alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public Individual doCrossover(final Individual parent1, final Individual parent2) {
		final Individual child = new Individual(parent1.elements.length, parent1.maxValue, parent1.consumers, parent1.distance);

		final Random random = new Random();
		
		for (int i = 0; i < parent1.elements.length; ++i) {
			child.elements[i] = random.nextDouble() < alpha ? parent1.elements[i] : parent2.elements[i];
		}

		return child;
	}
}
