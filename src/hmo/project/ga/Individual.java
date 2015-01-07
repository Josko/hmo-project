package hmo.project.ga;

import hmo.project.datastruct.Consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class Individual implements Comparable<Individual> {

	final protected int[] elements;
	final protected int maxValue;

	private Double fitness;

	protected final Consumer[] consumers;
	protected double[][] distance;

	public Individual(final int size, final int maxValue, final Consumer[] consumers, final double[][] distance) {
		this.elements = new int[size];
		this.maxValue = maxValue;
		this.consumers = consumers;
		this.distance = distance;

		final Random rng = new Random();

		for (int i = 0; i < size; ++i) {
			this.elements[i] = rng.nextInt(maxValue + 1);
		}
	}

	public double getFitness() {
		if (this.fitness == null) {
			double cost = 0;

			final Map<Integer, List<Integer>> pathAssignment = new HashMap<Integer, List<Integer>>();
			
			for (int i = 0; i < this.elements.length; i++) {
				if (!pathAssignment.containsKey(this.elements[i])) {
					pathAssignment.put(this.elements[i], new LinkedList<Integer>());
				}

				pathAssignment.get(this.elements[i]).add(i);
			}

			for (final Integer vehicle : pathAssignment.keySet()) {
				cost += 7900;
				
				final double cycleCost = hamiltonCycleCost(pathAssignment.get(vehicle), vehicle);

				// Hard constraint
				if (cycleCost < 0) {
					cost += 40000;
				} else {
					cost += cycleCost;
				}
			}

			this.fitness = cost;
		}

		return this.fitness;
	}

	private int hamiltonCycleCost(final List<Integer> consumerIndices, final Integer vehicle) {
		int totalResourcesNeeded = 0;

		if (consumerIndices.size() < 2) {
			return 0;
		}

		for (final Integer index : consumerIndices) {
			totalResourcesNeeded += consumers[index].getResourcesNeeded();
		}

		if (totalResourcesNeeded > 70) {
			return -1;
		}

		final List<Integer> hamiltonCycle = new ArrayList<Integer>(consumerIndices.size());
		int vertex1Index = consumerIndices.get(0);
		int vertex2Index = consumerIndices.get(1);
		double minDistance = distance[vertex1Index + 5][vertex2Index + 5];

		for (int i = 2; i < consumerIndices.size(); i++) {
			final int vertex1 = consumerIndices.get(i);
			final int vertex2 = consumerIndices.get((i + 1) % consumerIndices.size());
			final double distanceCurrent = distance[vertex1 + 5][vertex2 + 5];

			if (distanceCurrent < minDistance) {
				vertex1Index = vertex1;
				vertex2Index = vertex2;
			}
		}

		int totalDistance = (int) (minDistance * 100);

		hamiltonCycle.add(vertex1Index);
		hamiltonCycle.add(vertex2Index);

		while (hamiltonCycle.size() < consumerIndices.size()) {
			final Integer leftEnd = hamiltonCycle.get(0);
			final Integer rightEnd = hamiltonCycle.get(hamiltonCycle.size() - 1);

			double minDistanceLeft = 10000;
			Integer minIndexLeft = null;
			double minDistanceRight = 10000;
			Integer minIndexRight = null;

			for (final Integer current : consumerIndices) {
				if (hamiltonCycle.contains(current)) {
					continue;
				}

				double distanceLeft = distance[current + 5][leftEnd + 5];
				if (minIndexLeft == null || distanceLeft < minDistanceLeft) {
					minDistanceLeft = distanceLeft;
					minIndexLeft = current;
				}
				
				double distanceRight = distance[current + 5][rightEnd + 5];
				if (minIndexRight == null || distanceRight < minDistanceRight) {
					minDistanceRight = distanceRight;
					minIndexRight = current;
				}
			}

			if (minDistanceLeft < minDistanceRight) {
				hamiltonCycle.add(0, minIndexLeft);
				totalDistance += (int) (minDistanceLeft * 100);
			} else {
				hamiltonCycle.add(minIndexRight);
				totalDistance += (int) (minDistanceRight * 100);
			}
 
		}

		return totalDistance;
	}

	@Override
	public int compareTo(final Individual o) {
		return Double.compare(this.getFitness(), o.getFitness());
	}

	@Override
	public String toString() {
		return Arrays.toString(this.elements);
	}
}
