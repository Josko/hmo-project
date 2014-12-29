package hmo.project.ga;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable<Individual> {

	protected int[] elements;
	protected int maxValue;
	
	private Double fitness;
	
	

	public Individual(int size, int maxValue) {
		this.elements = new int[size];
		this.maxValue = maxValue;
		
		final Random rng = new Random();

		for (int i = 0; i < size; i++) {
			this.elements[i] = rng.nextInt(maxValue + 1);
		}
	}

	public double getFitness() {
		if (this.fitness == null) {
			// TODO: Pravi izracun ovdje umjesto mock
			double sum = 0;

			for (int i = 0; i < this.elements.length; i++) {
				sum += this.elements[i];
			}

			this.fitness = sum;
		}

		return this.fitness;
	}

	@Override
	public int compareTo(Individual o) {
		return Double.compare(this.getFitness(), o.getFitness());
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.elements);
	}
}
