package hmo.project.ga;

import java.util.Random;

public class SimpleMutation implements MutationOperator {

	private final double p;
	private final int hiBound;

	public SimpleMutation(double p, int hiBound) {
		this.p = p;
		this.hiBound = hiBound;
	}

	@Override
	public void mutate(Individual ind) {
		final Random rand = new Random();

		if (rand.nextDouble() > p) {
			return;
		}

		ind.elements[rand.nextInt(ind.elements.length)] = rand
				.nextInt(hiBound + 1);
	}

}
