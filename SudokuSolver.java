import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *	SudokuSolver - Solves an incomplete Sudoku puzzle using recursion and backtracking
 *
 *	@author	Qi Huang
 *	@since	1/25/2024
 *
 */
public class SudokuSolver {

	private int[][] puzzle;		// the Sudoku puzzle
	
	private String PUZZLE_FILE = "puzzle1.txt";	// default puzzle file
	
	private final int DIGITS = 9; // number of digits in the puzzle
	
	/* Constructor */
	public SudokuSolver() {
		puzzle = new int[9][9];
		// fill puzzle with zeros
		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = 0;
	}
	
	public static void main(String[] args) {
		SudokuSolver sm = new SudokuSolver();
		sm.run(args);
	}
	
	public void run(String[] args) {
		// get the name of the puzzle file
		String puzzleFile = PUZZLE_FILE;
		if (args.length > 0) puzzleFile = args[0];
		
		System.out.println("\nSudoku Puzzle Solver");
		// load the puzzle
		System.out.println("Loading puzzle file " + puzzleFile);
		loadPuzzle(puzzleFile);
		System.out.println("\nOriginal Sudoku Puzzle\n");
		printPuzzle();
		// solve the puzzle starting in (0,0) spot (upper left)
		solvePuzzle(0, 0);
		System.out.println("\nSolved Sudoku Puzzle\n");
		printPuzzle();
	}
	
	/**	Load the puzzle from a file
	 *	@param filename		name of puzzle file
	 */
	public void loadPuzzle(String filename) {
		Scanner infile = FileUtils.openToRead(filename);
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				puzzle[row][col] = infile.nextInt();
		infile.close();
	}
	
	/**	Solve the Sudoku puzzle using brute-force method. */
	public boolean solvePuzzle(int row, int col) {
		ArrayList<Integer> choices = new ArrayList<Integer>(DIGITS);
		int index = 0;
		int newRow = -1;
		int newCol = -1;
		boolean isValid = false;
		
		for (int i = 1; i <= DIGITS; i++) {
			choices.add(i);
		}
		
		do {
			index = (int)(Math.random() * choices.size());
			puzzle[row][col] = choices.remove(index);
			
			isValid = !isRepeated(row, col);
			
			if (isValid) {
				newRow = row;
				newCol = col;
				do {
					if (newCol < puzzle[0].length - 1) {
						newCol++;
					} else if (newRow < puzzle.length - 1) {
						newCol = 0;
						newRow++;
					}
				} while ( (newCol != puzzle[0].length - 1 || newRow != 
				puzzle.length - 1) && puzzle[newRow][newCol] != 0);
				
				if (puzzle[newRow][newCol] == 0) {
					isValid = solvePuzzle(newRow, newCol);
				}
			}
		} while (!isValid && choices.size() > 0);
		
		if (!isValid) {
			puzzle[row][col] = 0;
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isRepeated(int row, int col) {
		for (int i = 0; i < puzzle[0].length; i++) {
			if (i != col && puzzle[row][i] == puzzle[row][col]) {
				return true;
			}
		}
		
		for (int i = 0; i < puzzle.length; i++) {
			if (i != row && puzzle[i][col] == puzzle[row][col]) {
				return true;
			}
		}
		
		int cellRow = row / 3;
		int cellCol = col / 3;
		
		for (int i = 3 * cellRow; i < 3 * cellRow + 2; i++) {
			for (int j = 3 * cellCol; j < 3 * cellCol + 2; j++) {
				if ( (i != row && j != col) 
					&& puzzle[i][j] == puzzle[row][col]) {
					return true;
				}
			}
		}
		
		return false;
	}
		
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
}
