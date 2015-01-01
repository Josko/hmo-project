package hmo.project.ga;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SolutionDecoder { 
	private final Consumer[] consumers;
	private final Producer[] producers;

	private final double[][] distance;

	public SolutionDecoder( 
			final Consumer[] consumers, final Producer[] producers,
			final double[][] distance) {
		super(); 
		this.consumers = consumers;
		this.producers = producers;

		this.distance = distance;
	}

	public Solution decode(final Individual ind, final int[] warehouses) {
		final Map<Integer, List<Integer>> pathAssignment = new HashMap<Integer, List<Integer>>();
		final Map<Integer, Integer> warehouseAssignment = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < ind.elements.length; i++) {
			if (!pathAssignment.containsKey(ind.elements[i])) {
				pathAssignment.put(ind.elements[i], new LinkedList<Integer>());
			}

			pathAssignment.get(ind.elements[i]).add(i);
		}

		final Map<Integer, Integer> usedProducers = new HashMap<Integer, Integer>();
		for (int i = 0; i < warehouses.length; i++) {
			usedProducers.put(warehouses[i], producers[warehouses[i]].GetCapacity());
		}
		
		for (Integer vehicle : pathAssignment.keySet()) {
			final List<Integer> cycle = getBestCycle(pathAssignment
					.get(vehicle));

			double minDistance = Double.MAX_VALUE;
			int minProducerIndex = -1;
			for (Integer producer : usedProducers.keySet()) {
				if (usedProducers.get(producer) < getCycleRequirement(cycle)) {
					continue;
				}

				Integer firstConsumer = cycle.get(0);
				Integer lastConsumer = cycle.get(cycle.size() - 1);
				double distance = (int) (this.distance[producer][firstConsumer + 5] * 100)
						+ (int) (this.distance[producer][lastConsumer + 5] * 100);

				if (minProducerIndex == -1 || distance < minDistance) {
					minProducerIndex = producer;
					minDistance = distance;
				}

			}

			usedProducers.put(minProducerIndex,
					usedProducers.get(minProducerIndex)
							- getCycleRequirement(cycle));
			

			warehouseAssignment.put(vehicle, minProducerIndex);
		}

		return new Solution(pathAssignment, warehouseAssignment);
	}

	private List<Integer> getBestCycle(List<Integer> path) {
		List<Integer> bestPath = path;

		final List<List<Integer>> permutations = new LinkedList<List<Integer>>();
		makePermutations(path, new LinkedList<Integer>(), permutations);

		double shortestPath = Double.MAX_VALUE;
		for (List<Integer> current : permutations) {
			if (getCycleCost(current) < shortestPath) {
				shortestPath = getCycleCost(current);
				bestPath = current;
			}
		}

		return bestPath;
	}

	private double getCycleCost(final List<Integer> cycle) {
		double cost = 0;

		for (int i = 0; i < cycle.size() - 1; i++) {
			cost += (int) (distance[cycle.get(i) + 5][cycle.get((i + 1)) + 5] * 100);
		}

		return cost;
	}

	private int getCycleRequirement(final List<Integer> cycle) {
		int reqs = 0;

		for (Integer consumer : cycle) {
			reqs += consumers[consumer].getResourcesNeeded();
		}

		return reqs;
	}

	private void makePermutations(final List<Integer> original,
			final List<Integer> permutation,
			final List<List<Integer>> permutations) {
		if (permutation.size() < original.size()) {
			for (int i = 0; i < original.size(); i++) {
				int current = original.get(i);

				if (!permutation.contains(current)) {
					final List<Integer> newPermutation = new ArrayList<Integer>(
							permutation);
					newPermutation.add(current);

					makePermutations(original, newPermutation, permutations);
				}
			}

		} else {
			permutations.add(permutation);
		}
	}
}
