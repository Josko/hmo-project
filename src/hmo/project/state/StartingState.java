package hmo.project.state;

import java.util.Arrays;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Producer;

public final class StartingState {
	public final Consumer[] consumers;
	public final Producer[] producers;
	
	public double[][] distance;
	
	public StartingState(final Producer[] producers, final Consumer[] consumers) {
		this.producers = producers;
		this.consumers = consumers;
		
		int totalProducerCapacity = 0;
		
		for (final Producer producer : producers) {
			totalProducerCapacity += producer.GetCapacity();
		}
		
		System.out.println("Producer capacity: " + totalProducerCapacity);
		
		int totalConsumerCapacity = 0;
		
		for (final Consumer consumer : consumers) {
			totalConsumerCapacity += consumer.getResourcesNeeded();
		}
		
		System.out.println("Consumer capacity: " + totalConsumerCapacity + " (minimum of " + (int)Math.ceil((double)totalConsumerCapacity / 70) +" vehicles)\n");
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
	
	@Override
	public String toString() {
		return Arrays.toString(producers) + "\n" + Arrays.toString(consumers);
	}
}
