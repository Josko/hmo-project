package hmo.project.state;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Vehicle;
import hmo.project.datastruct.Producer;

public class State {
	public final Consumer[] consumers;
	public final Producer[] producers;
	public Vehicle[] vehicles;
	
	public double[][] distances;
	
	public State(final Producer[] producers, final Consumer[] consumers, final int vehicleCapacity, final int vehicleCost) {
		this.producers = producers;
		this.consumers = consumers;
		
		// calculate max possible number of vehicles
		
		int totalProducerCapacity = 0;
		
		for (final Producer producer : producers) {
			totalProducerCapacity += producer.GetCapacity();
		}
		
		final int maxVehicleNumber =  (int) Math.ceil((double)totalProducerCapacity / (double)vehicleCapacity);
		
		this.vehicles = new Vehicle[maxVehicleNumber];
	}
	
	public void FloydWarshall() {
		
	}
	
	public int GetCost() {
		double cost = 0;
		
		for (final Producer producer : producers) {
			for (final Vehicle vehicle : vehicles) {
				if (vehicle != null && vehicle.GetNumberOfDestinations() > 0 && producer == vehicle.GetOrigin()) {
					cost += producer.GetCostOfOpening();
					break;
				}
			}
		}
		
		for (final Vehicle vehicle : vehicles) {
			if (vehicle != null)
				cost += vehicle.GetCost();
		}		

		return (int) Math.floor(cost * 100);
	}
}
