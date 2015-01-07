package hmo.project;

import hmo.project.ga.Algorithm;
import hmo.project.ga.Individual;
import hmo.project.ga.Solution;
import hmo.project.ga.SolutionDecoder;
import hmo.project.input.InputFormatReader;
import hmo.project.output.OutputFormatWriter;
import hmo.project.state.StartingState;

import java.io.File;
import java.io.IOException;

public final class Main {
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: <inputfile> <outputfile>");
			return;
		}

		final InputFormatReader input = new InputFormatReader(new File(args[0]));

		final StartingState startingState = input.ReadAll();
		startingState.CalculateDistances();

		final Algorithm alg = new Algorithm(startingState.consumers, startingState.distance, 100, 53, 1000, 3500000, 0.31, 4);
		alg.init();
		alg.run();

		final Individual best = alg.getBestIndividual();
		final SolutionDecoder decoder = new SolutionDecoder(startingState.consumers, startingState.producers, startingState.distance);
		
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
			solution.improve(startingState.distance, startingState.producers);
			final int cost = solution.getTotalCost(startingState.distance, startingState.producers);
			
			if (cost < bestCost) {
				bestSolution = solution;
				bestCost = cost;
			}
		}

		final OutputFormatWriter output = new OutputFormatWriter(new File(args[1]));
		output.WriteAll(startingState, bestSolution);
		output.WriteAllToStdout(startingState, bestSolution);
	}	
}
