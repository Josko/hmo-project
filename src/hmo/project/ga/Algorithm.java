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
		
		for (int eval = 0; eval < this.evaluations; ++eval) {			
			final List<Integer> candidates = new ArrayList<Integer>(tournamentSize);
			
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

			int bestIndex = 0;
			double bestFitness = population.get(candidates.get(0)).getFitness();
			int secondBestIndex = 1;
			double secondBestFitness = population.get(candidates.get(1)).getFitness();

			for (int i = 2; i < candidates.size(); ++i) {
				final double fitness = population.get(candidates.get(i)).getFitness();
				
				if (fitness < bestFitness) {
					bestIndex = i;
					bestFitness = fitness;
				} else if (fitness < secondBestFitness) {
					secondBestIndex = i;
					secondBestFitness = fitness;
				}
			}
			
			final Individual firstParent = this.population.get(bestIndex);
			final Individual secondParent = this.population.get(secondBestIndex);

			this.population.remove(worstIndex);

			final Individual child = crossoverOperators.get(random.nextInt(crossoverOperators.size())).doCrossover(firstParent, secondParent);
			mutationOperators.get(random.nextInt(mutationOperators.size())).mutate(child);
			this.population.add(child);

			if (child.getFitness() < this.bestFitness) {
				this.bestFitness = child.getFitness();
				this.bestIndividual = child;
			}
		}
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

}
