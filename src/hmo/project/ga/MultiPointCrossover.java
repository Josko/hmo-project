package hmo.project.ga;

import java.util.Random;

public final class MultiPointCrossover implements CrossoverOperator {

	@Override
	public Individual doCrossover(final Individual parent1, final Individual parent2) {
		final Individual child = new Individual(parent1.elements.length, parent1.maxValue, parent1.consumers, parent1.distance);

		final Random random = new Random();
		
		final int crossoverPartitionSize = random.nextInt(parent1.elements.length) + 1;
		
		Individual currentParent = random.nextInt(2) == 0 ? parent1 : parent2;
		
		int lastPartitionIndex = 0;

		for (int i = 0; i < parent1.elements.length; i++) {			
			final int partitionIndex = i / crossoverPartitionSize;
			
			if (partitionIndex != lastPartitionIndex) {
				if (currentParent == parent1)
					currentParent = parent2;
				else
					currentParent = parent1;
			}
			
			child.elements[i] = currentParent.elements[i];
			
			lastPartitionIndex = partitionIndex;
		}

		return child;
	}

}