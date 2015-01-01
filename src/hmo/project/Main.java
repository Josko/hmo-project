package hmo.project;

import hmo.project.ga.Algorithm;
import hmo.project.ga.Individual;
import hmo.project.ga.Solution;
import hmo.project.ga.SolutionDecoder;
import hmo.project.input.InputFormatReader;
import hmo.project.output.OutputFormatWriter;
import hmo.project.state.State;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: <inputfile> <outputfile>");
			return;
		}

		final InputFormatReader input = new InputFormatReader(new File(args[0]));

		final State state = input.ReadAll();

		state.CalculateDistances();

		Algorithm alg = new Algorithm(state.vehicles, state.consumers,
				state.distance, 100, 53, 500, 2000000, 0.3, 3);
		alg.init();
		alg.run();

		Individual best = alg.getBestIndividual();

		SolutionDecoder decoder = new SolutionDecoder(state.consumers,
				state.producers, state.distance);
		
		int[] warehouses = {1, 3, 4};
		Solution solution = decoder.decode(best, warehouses);

		System.out.println(solution.getPathAssignment().keySet().size() + "\n");
		
		for (Integer vehicle : solution.getPathAssignment().keySet()) {
			final List<Integer> cycle = solution.getPathAssignment().get(vehicle);
			
			String cycleString = "";
			for (Integer city : cycle) {
				cycleString += city + " ";
			}
			
			System.out.println(solution.getWarehouseAssignment().get(vehicle) + ":  " + cycleString.trim() + "\n");
		}
		
		System.out.println("\n\n" + solution.getTotalCost(state.distance,
				state.producers));

		final OutputFormatWriter output = new OutputFormatWriter(new File(
				args[1]));

		output.WriteAll(state);
	}
}
