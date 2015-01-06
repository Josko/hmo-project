package hmo.project.ga;

import hmo.project.datastruct.Consumer;
import hmo.project.datastruct.Vehicle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Algorithm {

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

	private final Vehicle[] vehicles;
	private final Consumer[] consumers; 

	private final double[][] distance;

	public Algorithm(final Vehicle[] vehicles, final Consumer[] consumers,
			  double[][] distance, int noCities,
			int noTrucks, int populationSize, int evaluations,
			double mutationProb, int tournamentSize) {
		this.vehicles = vehicles;
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
		this.mutationOperators.add(new SimpleMutation(this.mutationProb, this.noTrucks));
		this.mutationOperators.add(new SwapMutation(this.mutationProb));

		for (int i = 0; i < populationSize; i++) {
			this.population.add(new Individual(noCities, noTrucks, vehicles,
					consumers, distance));
			if (this.bestIndividual == null
					|| this.population.get(i).getFitness() < this.bestFitness) {
				this.bestFitness = this.population.get(i).getFitness();
				this.bestIndividual = this.population.get(i);
			}
		}
	}

	public void run() {
		final Random random = new Random();
		for (int eval = 0; eval < this.evaluations; eval++) {
			final List<Integer> candidates = new ArrayList<>(tournamentSize);
			while (candidates.size() < tournamentSize) {
				int newCandidate = random.nextInt(populationSize);
				if (!candidates.contains(newCandidate)) {
					candidates.add(newCandidate);
				}
			}

			int worstIndex = 0;
			double worstFitness = population.get(candidates.get(0))
					.getFitness();
			for (int i = 1; i < candidates.size(); i++) {
				if (population.get(candidates.get(i)).getFitness() > worstFitness) {
					worstIndex = i;
					worstFitness = population.get(candidates.get(i))
							.getFitness();
				}
			}
			worstIndex = candidates.remove(worstIndex);

			int bestIndex = 0;
			double bestFitness = population.get(candidates.get(0)).getFitness();
			int secondBestIndex = 1;
			double secondBestFitness = population.get(candidates.get(1))
					.getFitness();

			for (int i = 2; i < candidates.size(); i++) {
				if (population.get(candidates.get(i)).getFitness() < bestFitness) {
					bestIndex = i;
					bestFitness = population.get(candidates.get(i))
							.getFitness();
				} else if (population.get(candidates.get(i)).getFitness() < secondBestFitness) {
					secondBestIndex = i;
					secondBestFitness = population.get(candidates.get(i))
							.getFitness();
				}
			}
			final Individual firstParent = this.population.get(bestIndex);
			final Individual secondParent = this.population
					.get(secondBestIndex);

			// Izbaci najgoru
			this.population.remove(worstIndex);

			// Dodaj dijete
			final Individual child = crossoverOperators.get(
					random.nextInt(crossoverOperators.size())).doCrossover(
					firstParent, secondParent);
			mutationOperators.get(random.nextInt(mutationOperators.size()))
					.mutate(child);
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
