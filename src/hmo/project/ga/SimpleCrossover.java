package hmo.project.ga;

import java.util.Random;

public class SimpleCrossover implements CrossoverOperator {

	@Override
	public Individual doCrossover(Individual parent1, Individual parent2) {
		Individual child = new Individual(parent1.elements.length,
				parent1.maxValue);

		Random random = new Random();

		int crossoverPoint = random.nextInt(parent1.elements.length);

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
