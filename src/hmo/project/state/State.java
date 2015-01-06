package hmo.project.state;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Vehicle;
import hmo.project.datastruct.Producer;

public class State {
	public final Consumer[] consumers;
	public final Producer[] producers;
	public Vehicle[] vehicles;
	
	public double[][] distance;
	
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
	
	public void CalculateDistances() {
		final int vertices = consumers.length + producers.length;
		
		this.distance = new double[vertices][vertices];
		
		int xOffset = 0;
		
		for (final Producer producer : producers) {
			int yOffset = 0;
			
			for (final Producer producer2 : producers) {
				this.distance[xOffset][yOffset] = Math.sqrt(Math.pow(producer.x - producer2.x, 2) + Math.pow(producer.y - producer2.y, 2));
				++yOffset;
			}
			
			for (final Consumer consumer : consumers) {
				this.distance[xOffset][yOffset] = Math.sqrt(Math.pow(producer.x - consumer.x, 2) + Math.pow(producer.y - consumer.y, 2));
				++yOffset;
			}
			
			++xOffset;
		}
		
		for (final Consumer consumer : consumers) {
			int yOffset = 0;
			
			for (final Producer producer : producers) {
				this.distance[xOffset][yOffset] = Math.sqrt(Math.pow(producer.x - consumer.x, 2) + Math.pow(producer.y - consumer.y, 2));
				++yOffset;
			}
			
			for (final Consumer consumer2 : consumers) {
				this.distance[xOffset][yOffset] = Math.sqrt(Math.pow(consumer2.x - consumer.x, 2) + Math.pow(consumer2.y - consumer.y, 2));
				++yOffset;
			}
			
			++xOffset;
		}
	}
}
