package hmo.project.ga;

import java.util.Random;

public final class OnePointCrossover implements CrossoverOperator {

	@Override
	public Individual doCrossover(final Individual parent1, final  Individual parent2) {
		final Individual child = new Individual(parent1.elements.length, parent1.maxValue, parent1.consumers, parent1.distance);

		final Random random = new Random();

		final int crossoverPoint = random.nextInt(parent1.elements.length);

		for (int i = 0; i < parent1.elements.length; i++) {
			if (i < crossoverPoint) {
				child.elements[i] = parent1.elements[i];
			} else {
				child.elements[i] = parent2.elements[i];
			}
		}

		return child;
	}

}
