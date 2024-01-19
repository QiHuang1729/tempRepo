/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Qi Huang
 *	@version 1.0
 *
 */
public class SudokuMaker {

	private int[][] puzzle;
		
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
	
	/**
	 * createPuzzle - creates the Sudoku puzzle recursively
	 * Precondition: puzzle is a rectangular grid
	 */
	public void createPuzzle() {
		createRecursively(0, 0);
	}
	
	public void createRecursively(int row, int col) {
		int candidate = (int)(Math.random() * 9 + 1);
		// if candidate doesn't work, backtrack
		// do we have to use recursion in place of iterative for everything?
		if (col < puzzle[0].length - 1, candidate) {
			// store candidate for next square in this method. The position
			// of the next square is uniquely determined by the position 
			// of the this square (there is a deterministic rule for 
			// finding that)
			createRecursively(col, row + 1); // if candidate doesn't work
			// backtrack by exiting out of the method. 
			// recursively call again, then if it still doesn't work, try
			// a different candidate. Store the used candidates in an array.
			// The random generation idea I have right now should work
			// If the candidate does work, then the next method should not exit out
			// and should be considering the next number
			
		
	}
	
	public boolean isValid(int entry) {
		// check the rows for equals. If equal, return false
		// check the columns for equals. If equal, return false
		// check the rest of the square for equals. If equal, return false
		// return true if all checks turn false and the program flow does
		// not return
	}
}
