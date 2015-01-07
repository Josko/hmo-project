package hmo.project.ga;

import hmo.project.datastruct.Consumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class Algorithm {

	private final int noCities;
	private final int noTrucks;

	private final List<Individual> population;

	private final List<CrossoverOperator> crossoverOperators;
	private final List<MutationOperator> mutationOperators;

	private final int populationSize;
	private final int evaluations;
	private final double mutationProb;
	private final int tournamentSize;

	private double bestFitness = Double.MAX_VALUE;
	private Individual bestIndividual;
	
	private final Consumer[] consumers;
	private final double[][] distance;

	public Algorithm(final Consumer[] consumers, final double[][] distance, final int noCities, final int noTrucks, final int populationSize, final int evaluations, final double mutationProb, final int tournamentSize) {
		this.consumers = consumers; 
		this.distance = distance;
		this.noCities = noCities;
		this.noTrucks = noTrucks;
		this.populationSize = populationSize;
		this.evaluations = evaluations;
		this.mutationProb = mutationProb;
		this.tournamentSize = tournamentSize;
		this.population = new ArrayList<Individual>(populationSize);
		this.crossoverOperators = new LinkedList<CrossoverOperator>();
		this.mutationOperators = new LinkedList<MutationOperator>();
	}

	public void init() {
		this.crossoverOperators.add(new OnePointCrossover());
		this.crossoverOperators.add(new MultiPointCrossover());
		this.crossoverOperators.add(new AlphaCrossover(0.6));
		this.mutationOperators.add(new SimpleMutation(this.mutationProb, this.noTrucks));
		this.mutationOperators.add(new SwapMutation(this.mutationProb));

		for (int i = 0; i < populationSize; ++i) {
			final Individual individual = new Individual(noCities, noTrucks, consumers, distance);
			
			this.population.add(individual);
			
			if (this.bestIndividual == null || individual.getFitness() < this.bestFitness) {
				this.bestFitness = individual.getFitness();
				this.bestIndividual = individual;
			}
		}
	}

	public void run() {
		final Random random = new Random();		
		final List<Integer> candidates = new ArrayList<Integer>(tournamentSize);
		
		for (int eval = 0; eval < this.evaluations; ++eval) {
			candidates.clear();
			
			while (candidates.size() < tournamentSize) {
				final int newCandidate = random.nextInt(populationSize);
				
				if (!candidates.contains(newCandidate)) {
					candidates.add(newCandidate);
				}
			}

			int worstIndex = 0;			
			double worstFitness = population.get(candidates.get(0)).getFitness();
			
			for (int i = 1; i < candidates.size(); ++i) {
				final double fitness = population.get(candidates.get(i)).getFitness();
				
				if (fitness > worstFitness) {
					worstIndex = i;
					worstFitness = fitness;
				}
			}
			
			worstIndex = candidates.remove(worstIndex);

			Individual bestParent = population.get(candidates.get(0));
			double bestFitness = bestParent.getFitness();
			Individual secondBestParent = population.get(candidates.get(1));
			double secondBestFitness = secondBestParent.getFitness();

			for (int i = 2; i < candidates.size(); ++i) {
				final Individual individual = population.get(candidates.get(i));
				final double fitness = individual.getFitness();
				
				if (fitness < bestFitness) {
					bestParent = individual;
					bestFitness = fitness;
				} else if (fitness < secondBestFitness) {
					secondBestParent = individual;
					secondBestFitness = fitness;
				}
			}

			this.population.remove(worstIndex);

			final Individual child = crossoverOperators.get(random.nextInt(crossoverOperators.size())).doCrossover(bestParent, secondBestParent);
			mutationOperators.get(random.nextInt(mutationOperators.size())).mutate(child);
			this.population.add(child);
			
			final double childFitness = child.getFitness();

			if (childFitness < this.bestFitness) {
				this.bestIndividual = child;
				this.bestFitness = childFitness;
			}
		}
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

}
