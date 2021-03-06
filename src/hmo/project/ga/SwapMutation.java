package hmo.project.ga;

import java.util.Random;

public final class SwapMutation implements MutationOperator{
	
	private final double p;

	public SwapMutation(final double p) {
		this.p = p;
	}
	
	@Override
	public void mutate(final Individual ind) {
		final Random random = new Random();
		
		if (random.nextDouble() > p) {
			return;
		}
		
		final int index1 = random.nextInt(ind.elements.length);
		int index2 = random.nextInt(ind.elements.length);
		
		while (index1 == index2 ) {
			index2 = random.nextInt(ind.elements.length);
		}
		
		final int tmp = ind.elements[index1];
		ind.elements[index1] = ind.elements[index2];
		ind.elements[index2] = tmp;
	}

}
