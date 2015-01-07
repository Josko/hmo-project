package hmo.project.ga;

import java.util.Random;

public final class SimpleMutation implements MutationOperator {

	private final double p;
	private final int hiBound;

	public SimpleMutation(final double p, final int hiBound) {
		this.p = p;
		this.hiBound = hiBound;
	}

	@Override
	public void mutate(final Individual ind) {
		final Random rand = new Random();

		if (rand.nextDouble() > p) {
			return;
		}

		ind.elements[rand.nextInt(ind.elements.length)] = rand.nextInt(hiBound + 1);
	}

}
