import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTest {
	Solver sudoku;
	int[][] matrix;

	@BeforeEach
	void setUp() throws Exception {
		sudoku = new Solver();
		matrix = new int[9][9];
		sudoku.setMatrix(matrix);
	}

	@AfterEach
	void tearDown() throws Exception {
		sudoku.clear();
	}

	
	//tried to solve an empty matrix
	@Test
	void testfall1() {
		assertTrue(sudoku.solve(), "Solve returns false when it should be true");
	}
	
	//tries to solve a sudoku with two fives on the same row, then same column and then same region
	@Test
	void testfall2() {
		sudoku.set(0, 0, 5);
		sudoku.set(0, 5, 5);
		assertFalse(sudoku.solve(), "Solve returns true when it should be false");		
		sudoku.clear();
		
		sudoku.set(0, 0, 5);
		sudoku.set(5, 0, 5);
		assertFalse(sudoku.solve(), "Solve returns true when it should be false");
		sudoku.clear();
		
		sudoku.set(0, 0, 5);
		sudoku.set(1, 1, 5);
		assertFalse(sudoku.solve(), "Solve returns true when it should be false");
	}
	
	//first tests an unsolvable case, then empties the box with 7 and tests that it's now solvable
	@Test
	void testfall3() {
		sudoku.set(0, 0, 1);
		sudoku.set(0, 1, 2);
		sudoku.set(0, 2, 3);
		sudoku.set(1, 0, 4);
		sudoku.set(1, 1, 5);
		sudoku.set(1, 2, 6);
		sudoku.set(2, 3, 7);
		assertFalse(sudoku.solve(), "Solve returns true when it should be false");
		sudoku.remove(2, 3);
		assertTrue(sudoku.solve(), "Solve returns false when it should be true");
	}
	
	//first tests an unsolvable case, then the board is cleared and tests that it's now solvable
	@Test
	void testfall4() {
		sudoku.set(0, 0, 5);
		sudoku.set(0, 6, 5);
		assertFalse(sudoku.solve(), "Solve returns true when it should be false");
		sudoku.clear();
		assertTrue(sudoku.solve(), "Solve returns false when it should be true");
	}
	
	//tries a solvable case
	
	@Test
	void testfall5() {
		sudoku.set(0, 2, 8);
		sudoku.set(0, 5, 9);
		sudoku.set(0, 7, 6);
		sudoku.set(0, 8, 2);
		sudoku.set(1, 8, 5);
		sudoku.set(2, 0, 1);
		sudoku.set(2, 2, 2);
		sudoku.set(2, 3, 5);
		sudoku.set(3, 3, 2);
		sudoku.set(3, 4, 1);
		sudoku.set(3, 7, 9);
		sudoku.set(4, 1, 5);
		sudoku.set(4, 6, 6);
		sudoku.set(5, 0, 6);
		sudoku.set(5, 7, 2);
		sudoku.set(5, 8, 8);
		sudoku.set(6, 0, 4);
		sudoku.set(6, 1, 1);
		sudoku.set(6, 3, 6);
		sudoku.set(6, 5, 8);
		sudoku.set(7, 0, 8);
		sudoku.set(7, 1, 6);
		sudoku.set(7, 4, 3);
		sudoku.set(7, 6, 1);
		sudoku.set(8, 6, 4);
		assertTrue(sudoku.solve(), "Solve returns false when it should be true");
	}
	
	@Test
	void testfall6() {
		sudoku.set(4, 4, -1);
		assertEquals(0, sudoku.getMatrix()[4][4], "Illegal argument was placed in the grid");
		assertTrue(sudoku.solve(), "The number was inserted into the matrix when it should not have been");
		sudoku.clear();
		sudoku.set(4, 4, 0);
		assertEquals(0, sudoku.getMatrix()[4][4], "Illegal argument was placed in the grid");
		assertTrue(sudoku.solve(), "The number was inserted into the matrix when it should not have been");
		sudoku.clear();
		sudoku.set(4, 4, 10);
		assertEquals(0, sudoku.getMatrix()[4][4], "Illegal argument was placed in the grid");
		assertTrue(sudoku.solve(), "The number was inserted into the matrix when it should not have been");
		sudoku.clear();
		sudoku.set(4, 4, 'a');
		assertEquals(0, sudoku.getMatrix()[4][4], "Illegal argument was placed in the grid");
		assertTrue(sudoku.solve(), "The character was inserted into the matrix when it should not have been");
	}

}
