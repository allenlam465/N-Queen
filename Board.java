import java.util.Random;

public class Board {
	
	private int board[];
	private int N;
	private int searchCost;
	
	public Board(int n) {
		board = new int[n];
		N = n;
		searchCost = 0;
	}
	
	public Board(int[] board, int costs) {
		this.board = board;
		N = board.length;
		searchCost = costs;
	}
	
	public void setBoard(int[] board) {
		this.board = board;
	}
	
	public int[] getBoard() {
		return board;
	}
	
	public void setSearchCost(int searchCost) {
		this.searchCost = searchCost;
	}
	
	public int getSearchCost() {
		return searchCost;
	}
	
	public void randomPlacement() {
		Random rand = new Random();
		
		for(int i = 0; i < N; i++) 
			board[i] = rand.nextInt(N);
		
	}
	
	public String getPlacement() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < N; i++) 
				sb.append(board[i] + " ");
		
		return sb.toString();
	}
	
	// If cost is 0 then goal is met.
	public int getStateCost() {
		int cost = 0;
		
		// Check adjacent, vertical, and diagonals for other queen. If encountered add to cost.
		for(int i = 0; i < N; i++) 
			for(int j = i + 1; j < N; j++) 
				if(board[i] == board[j] || Math.abs(board[i] - board[j]) == j - i)
					cost++;
		
		return cost;
	}

}
