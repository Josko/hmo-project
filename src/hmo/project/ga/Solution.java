package hmo.project.ga;

import hmo.project.datastruct.Producer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

	private Map<Integer, List<Integer>> pathAssignment;
	private Map<Integer, Integer> warehouseAssignment;

	public Solution(final Map<Integer, List<Integer>> pathAssignment,
			final Map<Integer, Integer> warehouseAssignment) {
		super();
		this.pathAssignment = pathAssignment;
		this.warehouseAssignment = warehouseAssignment;
	}

	public int getTotalCost(final double[][] distance, final Producer[] producers) {
		int cost = 0;
		
		final Set<Integer> warehouses = new HashSet<Integer>();
		for (Integer vehicle : pathAssignment.keySet()) {
			cost += 1000;
			
			final List<Integer> cycle = pathAssignment.get(vehicle);
			cost += getCycleCost(cycle, distance);
			
			int warehouse = warehouseAssignment.get(vehicle);
			cost += (int) (distance[warehouse][cycle.get(0) + 5] * 100) ;
			cost += (int) (distance[warehouse][cycle.get(cycle.size() - 1) + 5] * 100);
			
			warehouses.add(warehouse);
		}
		
		for (Integer warehouse : warehouses) {
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

}
