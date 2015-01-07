package hmo.project.output;

import hmo.project.ga.Solution;
import hmo.project.state.StartingState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.List;

public final class OutputFormatWriter {
	private Writer output;
	
	public OutputFormatWriter(File outputFile) throws IOException {
		if (!outputFile.exists()) {
			outputFile.delete();
		}
		
		this.output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
	}
	
	public void WriteAll(final StartingState state, final Solution solution) throws IOException {		
		output.write(solution.getPathAssignment().keySet().size() + System.lineSeparator() + System.lineSeparator());
		
		for (Integer vehicle : solution.getPathAssignment().keySet()) {
			final List<Integer> cycle = solution.getPathAssignment().get(vehicle);
			
			String cycleString = "";
			
			for (Integer city : cycle) {
				cycleString += city + " ";
			}
			
			output.write(solution.getWarehouseAssignment().get(vehicle) + ":  " + cycleString.trim() + System.lineSeparator() + System.lineSeparator());
		}
		
		output.write(System.lineSeparator() + System.lineSeparator() + solution.getTotalCost(state.distance, state.producers) + System.lineSeparator());
		
		output.flush();
		output.close();
	}
	
	public void WriteAllToStdout(final StartingState state, final Solution solution) {		
		System.out.print(solution.getPathAssignment().keySet().size() + System.lineSeparator() + System.lineSeparator());
		
		for (Integer vehicle : solution.getPathAssignment().keySet()) {
			final List<Integer> cycle = solution.getPathAssignment().get(vehicle);
			
			String cycleString = "";
			
			for (Integer city : cycle) {
				cycleString += city + " ";
			}
			
			System.out.print(solution.getWarehouseAssignment().get(vehicle) + ":  " + cycleString.trim() + System.lineSeparator() + System.lineSeparator());
		}
		
		System.out.print(System.lineSeparator() + System.lineSeparator() + solution.getTotalCost(state.distance, state.producers) + System.lineSeparator());
	}
}
