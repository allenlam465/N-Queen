import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		String input;
		boolean exit = false;
		Scanner s = new Scanner(System.in);

		System.out.println("N-Queens with Simulated Annealing and Genetic Algorithms");
		while(!exit) {

			System.out.print("1. Simulated Annealing\n2. Genetic Algorithm\n3. Test Cases\n4. Exit \n>");

			input = s.nextLine();

			switch(input) {
			case "1":

				long startTimeSA = System.nanoTime();
				Board boardSA = new SimulatedAnnealing(21).simulatedAnnealing(1000, 150.00, .95);
				long endTimeSA = System.nanoTime() - startTimeSA;

				if(boardSA.getStateCost() != 0) {
					System.out.println("Failed to obtain solution within set iterations.");
					System.out.println("Forged Search Costs: " + boardSA.getSearchCost());
					System.out.println("Time Taken: " + endTimeSA + " nano seconds");
				}
				else {
					System.out.println("Solved!" + "\nAble to obtain solution within set iterations.");
					System.out.println("Current Board");
					System.out.println(boardSA.getPlacement());
					System.out.println("Iterations Search Costs: " + boardSA.getSearchCost());
					System.out.println("Time Taken: " + endTimeSA + " nano seconds");
				}

				break;
			case "2":

				long startTimeGA = System.nanoTime();
				Board boardGA = new GeneticAlgorithm().geneticAlgorithm(21, 100, 1000);
				long endTimeGA = System.nanoTime() - startTimeGA;

				if(boardGA.getStateCost() != 0) {
					System.out.println("Failed to obtain solution within set iterations.");
					System.out.println("Iterations Search Costs: " + boardGA.getSearchCost());
					System.out.println("Time Taken: " + endTimeGA + " nano seconds");

				}
				else {
					System.out.println("Solved!" + "\nAble to obtain solution within set iterations.");
					System.out.println("Current Board");
					System.out.println(boardGA.getPlacement());
					System.out.println("Iterations Search Costs: " + boardGA.getSearchCost());
					System.out.println("Time Taken: " + endTimeGA + " nano seconds");
				}

				break;
			case "3":
				System.out.println("Test Cases Run");
				testCaseSA(1000);
				testCaseGA(1000);

				break;
			case "4":
				System.out.println("Exiting...");
				exit = true;
				break;
			default:
				System.out.println("Invalid input. Choose a menu option to proceed.\n");
				break;
			}
		}

		s.close();

	}

	public static void testCaseSA(int cases) {

		double success = 0, failure = 0;
		int costs = 0;
		long time = 0;

		System.out.println("Simulated Annealing");

		for(int i = 0; i < cases; i++) {

			long startTime = System.nanoTime();
			Board board = new SimulatedAnnealing(21).simulatedAnnealing(1000, 150.00, .95);
			long endTime = System.nanoTime() - startTime;

			if(board.getStateCost() != 0)
				failure++;
			else
				success++;

			time += endTime;
			costs += board.getSearchCost();
		}

		System.out.println("Successes: " + success);
		System.out.println("Failures: " + failure);

		double annealingSuccess = success / cases;

		time = time / cases;
		costs = costs / cases;

		System.out.println(cases + " Cases Sucesses: %" + annealingSuccess * 100);
		System.out.println(cases + " Cases Average Search Costs : " + costs);	
		System.out.println(cases + " Cases Average Time : " + time + " nano seconds");	

	}

	public static void testCaseGA(int cases) {

		double success = 0, failure = 0;
		int costs = 0;
		long time = 0;

		System.out.println("Genetic Algorithm");

		for(int i = 0; i < cases; i++) {

			long startTime = System.nanoTime();
			Board board = new GeneticAlgorithm().geneticAlgorithm(21, 100, 2000);
			long endTime = System.nanoTime() - startTime;

			if(board.getStateCost() != 0)
				failure++;
			else
				success++;

			time += endTime;
			costs += board.getSearchCost();
		}

		System.out.println("Successes: " + success);
		System.out.println("Failures: " + failure);

		double geneticSuccess = success / cases;

		time = time / cases;
		costs = costs / cases;

		System.out.println(cases + " Cases Sucesses: %" + geneticSuccess * 100);
		System.out.println(cases + " Cases Average Search Costs : " + costs);	
		System.out.println(cases + " Cases Average Time : " + time + " nano seconds");	

	}



}
