package hmo.project.ga;

import hmo.project.datastruct.Producer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Solution {

	final private Map<Integer, List<Integer>> pathAssignment;
	final private Map<Integer, Integer> warehouseAssignment;

	public Solution(final Map<Integer, List<Integer>> pathAssignment, final Map<Integer, Integer> warehouseAssignment) {
		super();
		this.pathAssignment = pathAssignment;
		this.warehouseAssignment = warehouseAssignment;
	}

	public int getTotalCost(final double[][] distance, final Producer[] producers) {
		int cost = 0;
		final Set<Integer> warehouses = new HashSet<Integer>();
		
		for (final Integer vehicle : pathAssignment.keySet()) {
			cost += 1000;

			final List<Integer> cycle = pathAssignment.get(vehicle);
			cost += getCycleCost(cycle, distance);

			final int warehouse = warehouseAssignment.get(vehicle);
			cost += (int) (distance[warehouse][cycle.get(0) + 5] * 100);
			cost += (int) (distance[warehouse][cycle.get(cycle.size() - 1) + 5] * 100);

			warehouses.add(warehouse);
		}

		for (final Integer warehouse : warehouses) {
			cost += producers[warehouse].GetCostOfOpening();
		}

		return cost;
	}

	public Map<Integer, List<Integer>> getPathAssignment() {
		return pathAssignment;
	}

	public Map<Integer, Integer> getWarehouseAssignment() {
		return warehouseAssignment;
	}

	private int getCycleCost(final List<Integer> cycle, final double[][] distance) {
		int cost = 0;

		for (int i = 0; i < cycle.size() - 1; i++) {
			cost += (int) (distance[cycle.get(i) + 5][cycle.get((i + 1)) + 5] * 100);
		}

		return cost;
	}

	public void improve(double[][] distance, Producer[] producers) {
		for (final Integer vehicle : pathAssignment.keySet()) {
			List<Integer> bestCycle = pathAssignment.get(vehicle);

			final List<List<Integer>> permutations = new LinkedList<List<Integer>>();
			makePermutations(bestCycle, new LinkedList<Integer>(), permutations);

			int bestCost = getTotalCost(distance, producers);
			for (List<Integer> cycle : permutations) {
				pathAssignment.put(vehicle, cycle);
				int currentCost = getTotalCost(distance, producers);
				if (currentCost < bestCost) {
					bestCost = currentCost;
					bestCycle = cycle;
				}
			}

			pathAssignment.put(vehicle, bestCycle);
		}
	}

	private void makePermutations(final List<Integer> original, final List<Integer> permutation, final List<List<Integer>> permutations) {
		if (permutation.size() < original.size()) {
			for (int i = 0; i < original.size(); i++) {
				int current = original.get(i);

				if (!permutation.contains(current)) {
					final List<Integer> newPermutation = new ArrayList<Integer>(permutation);
					newPermutation.add(current);
					makePermutations(original, newPermutation, permutations);
				}
			}
		} else {
			permutations.add(permutation);
		}
	}

}
