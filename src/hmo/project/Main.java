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

public class Main {
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: <inputfile> <outputfile>");
			return;
		}

		final InputFormatReader input = new InputFormatReader(new File(args[0]));

		final State state = input.ReadAll();

		state.CalculateDistances();

		final Algorithm alg = new Algorithm(state.vehicles, state.consumers, state.distance, 100, 53, 1000, 2000000, 0.31, 4);
		alg.init();
		alg.run();

		final Individual best = alg.getBestIndividual();
		SolutionDecoder decoder = new SolutionDecoder(state.consumers, state.producers, state.distance);
		
		final int[][] warehouseSets = {	
								{0, 1, 2},
								{0, 1, 3},
								{0, 1, 4},
								{0, 2, 3},
								{0, 2, 4},
								{1, 2, 3},
								{1, 2, 4},
								{1, 3, 4}
							};
		
		Solution bestSolution = null;
		int bestCost = Integer.MAX_VALUE;
		
		for (final int[] warehouses : warehouseSets) {
			final Solution solution = decoder.decode(best, warehouses);
			solution.improve(state.distance, state.producers);
			final int cost = solution.getTotalCost(state.distance, state.producers);
			
			if (cost < bestCost) {
				bestSolution = solution;
				bestCost = cost;
			}
		}

		final OutputFormatWriter output = new OutputFormatWriter(new File(args[1]));

		output.WriteAll(state, bestSolution);
		output.WriteAllToStdout(state, bestSolution);
	}
}
