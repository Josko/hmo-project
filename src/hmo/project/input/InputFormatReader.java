package hmo.project.input;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Producer;
import hmo.project.state.State;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InputFormatReader {
	private Scanner input;
	
	public InputFormatReader(File inputFile) {
		this.input = null;
		
		try {
			this.input = new Scanner(inputFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public State ReadAll() throws IOException {
		final int consumerNumber = Integer.parseInt(input.nextLine());		
		final int producerNumber = Integer.parseInt(input.nextLine());
		
		input.nextLine();
		
		final Producer[] producers = new Producer[producerNumber];
		
		for (int i = 0; i < producerNumber; ++i) {
			final String[] coordinates = input.nextLine().split("\t");
			
			final int x = Integer.parseInt(coordinates[0]);
			final int y = Integer.parseInt(coordinates[1]);
			
			producers[i] = new Producer(i, x, y); 
		}		
		
		input.nextLine();
		
		final Consumer[] consumers = new Consumer[consumerNumber];
		
		for (int i = 0; i < consumerNumber; ++i) {
			final String[] coordinates = input.nextLine().split("\t");
			
			final int x = Integer.parseInt(coordinates[0]);
			final int y = Integer.parseInt(coordinates[1]);
			
			consumers[i] = new Consumer(i, x, y);
		}		
		
		input.nextLine();
		
		final int deliveryVehicleCapacity = Integer.parseInt(input.nextLine());
		
		input.nextLine();
		
		for (int i = 0; i < producerNumber; ++i) {
			producers[i].SetCapacity(Integer.parseInt(input.nextLine()));
		}
		
		input.nextLine();
		
		for (int i = 0; i < consumerNumber; ++i) {
			consumers[i].SetResourcesNeeded(Integer.parseInt(input.nextLine()));
		}
		
		input.nextLine();
		
		for (int i = 0; i < producerNumber; ++i) {
			producers[i].SetCostOfOpening(Integer.parseInt(input.nextLine()));
		}
		
		input.nextLine();
		
		final int deliveryVehicleInitalCost = Integer.parseInt(input.nextLine());

		/* ignore the rest of the file */		
		
		this.input.close();
		
		return new State(producers, consumers, deliveryVehicleCapacity, deliveryVehicleInitalCost);
	}
}
