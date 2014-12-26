package hmo.project.output;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Vehicle;
import hmo.project.state.State;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class OutputFormatWriter {
	private Writer output;
	
	public OutputFormatWriter(File outputFile) throws IOException {
		if (!outputFile.exists()) {
			outputFile.delete();
		}
		
		this.output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
	}
	
	public void WriteAll(final State state) throws IOException {
		int activeVehicles = 0;
		
		for (Vehicle vehicle : state.vehicles) {
			if (vehicle != null && vehicle.GetNumberOfDestinations() > 0)
				++activeVehicles;
		}
		
		output.write(Integer.toString(activeVehicles) + System.lineSeparator());
		
		output.write(System.lineSeparator());
		
		for (Vehicle vehicle : state.vehicles) {
			if (vehicle != null && vehicle.GetNumberOfDestinations() > 0) {
				output.write(Integer.toString(vehicle.GetOrigin().GetName()));
				output.write(": ");
				
				for (Consumer destination : vehicle.GetDestinations()) {
					if (destination == null)
						break;
					
					output.write(" " + Integer.toString(destination.GetName()));
				}
				
				output.write(System.lineSeparator());
			}			
		}
		
		output.write(System.lineSeparator());
		output.write(System.lineSeparator());
		output.write(System.lineSeparator());
		
		output.write(Integer.toString(state.GetCost()));
		output.write(System.lineSeparator());
		
		output.flush();
		output.close();
	}
}
