	import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GeneticAlgorithm {

	private final double mutationChance = .15;

	private Board[] population, children, selectedPop;

	private int N, K, searchCost = 0;

	private int maxFitness;

	public Board geneticAlgorithm(int N, int K, int itr) {

		this.K = K - (K % 2);
		
		this.N = N;

		this.maxFitness = getMaxFitness(N);

		Random rand = new Random();

		population = new Board[this.K];
		
		generatePopulation();

		for(int j = 0; j < itr; j++) {
			
			searchCost++;

			selectedPop = selection();
			
			children = new Board[selectedPop.length];

			for(int i = 0; i < selectedPop.length; i+=2) {

				children[i] = crossOver(selectedPop[i], selectedPop[i+1]);
				children[i+1] = crossOver(selectedPop[i+1], selectedPop[i]);
				
				if(getFitness(children[i]) == maxFitness ) {
					Board result = new Board(children[i].getBoard(), searchCost);
					return result;	
				}
				else if(getFitness(children[i+1]) == maxFitness ) {
					Board result = new Board(children[i+1].getBoard(), searchCost);
					return result;		
				}
				
				
			}	
			
			for(int i = 0; i < children.length; i++) {
				
				double prob = rand.nextDouble();
				
				if(prob >= mutationChance) {
					children[i].setBoard(mutate(children[i].getBoard()));

					if(getFitness(children[i]) == maxFitness){
						Board result = new Board(children[i].getBoard(), searchCost);
						return result;	
					}
				}
			}
			
			population = children;

		}
		
		Board result = new Board(population[0].getBoard(), searchCost);
		return result;

	}

	public void generatePopulation() {
	
		for(int i = 0; i < K; i++) {
			population[i] = randomPlacement();
		}

	}

	public int getMaxFitness(int N) {
		return N * (N - 1) / 2;
	}

	public int getFitness(Board state) {
		return getMaxFitness(N) - state.getStateCost();
	}

	private Board[] selection() {

		Arrays.sort(population, Collections.reverseOrder(new Comparator<Board>() {

			@Override
			public int compare(Board x, Board y) {

				return Integer.compare(getFitness(x), getFitness(y));	

			}

		}));	
		
		Board[] selected = new Board[population.length];
		int[] fitnessVal = new  int[population.length];
		
		int fitnessSum = 0;
		Random rand = new Random();
				
		for(int i = 0; i < population.length/2; i++) {
			fitnessVal[i] = getFitness(population[i]);
			fitnessSum += fitnessVal[i];
		}
		
		//Round robin choice of random selection
		//Selects two at once to lower chance that two of the same parents are chosen
		for(int i = 0; i < population.length; i+=2) {
			int index = 0;
			int randChoice = rand.nextInt(fitnessSum) + 1;
			
			while(true) {
				randChoice -= fitnessVal[index];
				if(randChoice <= 0) {
					break;
				}
				index++;
			}
			
			selected[i] = population[index];
			
			randChoice = rand.nextInt(fitnessSum - fitnessVal[index]) + 1;
			index = 0;
			
			while(true) {
				randChoice -= fitnessVal[index];
				if(randChoice <= 0) {
					break;
				}
				index++;
			}
			
			selected[i + 1] = population[index];
			
			
		}
				
		return selected;

	}

	private Board crossOver(Board x, Board y) {
		
		int[] xParent = x.getBoard();
		int[] yParent = y.getBoard();
		
		int[] child = new int[xParent.length];

		int crossOverRatio = (int) (Math.random() * child.length);
				
		for(int i = 0; i <= crossOverRatio; i++) {
			child[i] = xParent[i];
		}

		for(int i = crossOverRatio + 1; i < child.length; i++) {
			child[i] = yParent[i];
		}
		
		return new Board(child, 0);
	}
	
	private Board randomPlacement() {		
		Board state = new Board(N);
		
		state.randomPlacement();
		
		return state;
	}

	private int[] mutate(int[] x) {
		Random rand = new Random();
		int randomMutation = rand.nextInt(x.length);
		x[randomMutation] = rand.nextInt(x.length);

		return x;
	}

}
