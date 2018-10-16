import java.util.Random;

public class SimulatedAnnealing {

	private Board board;
	
	private int searchCost;

	public SimulatedAnnealing(int N) {
		board = new Board(N);
		searchCost = 0;
	}

	// Run SA up to number of desired iterations
	// movedBoards obtains boards of randomly placed queens based on desired temperature
	// movedBoard state costs are then calculated and temperature updated with desired cooling 
	// as temperature cools down the number of bad choices will be reduced
	// return board if costs calculated is 0
	public Board simulatedAnnealing(int itr, double temp, double cool){	
		board.randomPlacement();
		int stateCost = board.getStateCost();

		Board movedBoard = board;

		for(int i = 0; i < itr; i++) {
			movedBoard = moveQueens(movedBoard, stateCost, temp);
			stateCost = movedBoard.getStateCost();
			temp = temp * cool;
			
			searchCost++;
			
			if(stateCost == 0)
				break;	
		}

		return movedBoard;
	}

	// continue to "forge" board till requirements are met
	// random choice of of queen is chosen and placed random location in chosen column
	// obtain costs of new board and compare with previous board return if current board is less
	// if not calculate deltaE and obtain a probability to decided whether to continue or return board
	// if any if statement not met revert board to previous state and try again
	private Board moveQueens(Board state, int stateCost, double temp) {
		Random rand = new Random();
		
		int N = state.getBoard().length;
		int[] board = state.getBoard();

		while(true) {

			int row = rand.nextInt(N);
			int col = rand.nextInt(N);

			int saveQueenPos = board[col];

			board[col] = row;
			
			Board newState = new Board(board, searchCost);

			int newStateCost = newState.getStateCost();

			if(newStateCost < stateCost) 
				return newState;

			int dE = stateCost - newStateCost;
			double prob = Math.exp(dE/temp);
						
			if(prob > rand.nextDouble())
				return newState;

			board[col] = saveQueenPos;
			
		}

	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getSearchCost() {
		return searchCost;
	}

}

