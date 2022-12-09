import javax.swing.JOptionPane;

public class Solver implements SudokuSolver {
	private int[][] matrix;
	
	public Solver() {
	}
	
	/**
	 * Tries to recursively solve the sudoku based on the inputs of the grid.
	 * @return boolean value of true if solvable, false if it is not.
	 */
	public boolean solve() {
		//in case of user input, checks if the inputs are legal before attempting to solve
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				int temp = matrix[i][j];
				if (matrix[i][j] != 0) {
					remove(i, j);
					if (!legal(temp, i, j)) {
						return false;
					} else {
						set(i, j, temp);
					}
				}
			}
		}
		return solve(0, 0);
	}
	
	private boolean solve(int r, int c) {
		//base case, we have reached a solution
		if (r == 8 && c == 9) {
			return true;
		}
		
		//moves to the next row if the last column has been reached
		if (c == 9) {
			r++;
			c = 0;
		}
		
		if (matrix[r][c] != 0) {
			return solve(r, c + 1);
		}
		for (int i = 1; i < 10; i++) {
			if (legal(i, r, c)) {
				set(r, c, i);
				if (solve(r, c + 1)) {
					return true;
				}
				remove(r, c);
			}
		}
		return false;
	}

	/**
	 * Checks whether putting the value of digit into bow row, col is a legal move or not according to the rules of Sudoku.
	 * @param digit, the digit to be inserted.
	 * @param row, the current row.
	 * @param col, the current column.
	 * @return boolean value of true if the move is legal, false if it is not.
	 */
	public boolean legal(int digit, int row, int col) {
		//checks if the row already contains digit
		for (int i = 0; i <= 8; i++) {
			if (matrix[i][col] == digit) {
				return false;
			}
		}
		
		//checks if the column already contains digit
		for (int i = 0; i <= 8; i++) {
			if (matrix[row][i] == digit) {
				return false;
			}
		}
		
		//checks if the region already contains digit
		int regRow = row - (row % 3);
		int regCol = col - (col % 3);
		for (int r = regRow; r < regRow + 3; r++) {
			for (int c = regCol; c < regCol + 3; c++) {
				if (r == row && c == col) {
					c++;
				} else if (matrix[r][c] == digit) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Puts the value of digit into the box row, col.
	 * @param row, the current row.
	 * @param col, the current column.
	 * @param digit, the digit to be inserted.
	 */
	public void set(int row, int col, int digit) {
		if (row < 0 || row > 8 || col < 0 || col > 8 || digit < 1 || digit > 9) {
			JOptionPane.showMessageDialog(null, "You inserted " + digit + ". Insert a number between 1 and 9.");
		} else {
			matrix[row][col] = digit;
		}
	}

	/**
	 * Removes the digit in the box row, col.
	 * @param row, the current row.
	 * @param col, the current column.
	 * @throws IllegalArgumentException if row or col is outside the accepted range.
	 */
	public void remove(int row, int col) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException();
		}
		matrix[row][col] = 0;
	}

	/**
	 * Empties this matrix.
	 */
	public void clear() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] > 0) {
					matrix[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * Fills this matrix with the digits in m.
	 * @param m, the matrix with the digits to insert.
	 * @throws IllegalArgumentException if m is not a 9 x 9 matrix.
	 */
	public void setMatrix(int[][] m) {
		if (m.length != 9 && m[m.length].length != 9) {
			throw new IllegalArgumentException();
		}
		this.matrix = m;
	}

	/**
	 * Returns a matrix with the current value.
	 * @return integer matrix with the current values.
	 */
	public int[][] getMatrix() {
		return matrix;
	}

}
